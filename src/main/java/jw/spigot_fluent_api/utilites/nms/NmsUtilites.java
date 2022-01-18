package jw.spigot_fluent_api.utilites.nms;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class NmsUtilites
{
    private static HashMap<Class<? extends Entity>, Method> handles = new HashMap<Class<? extends Entity>, Method>();
    private static Field player_connection = null;
    private static Method player_sendPacket = null;
    public static String getVersion(){
        String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
        if (array.length == 4)
            return array[3] + ".";
        return "";
    }
    public static Class<?> getNmsClass(String name){
        String version = getVersion();
        String className = "net.minecraft.server." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return clazz;
    }

    public static Field getFirstFieldByType(Class<?> clazz, Class<?> type){
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType() == type) {
                return field;
            }
        }
        return null;
    }
    public static Object getHandle(Entity entity){
        try {
            if (handles.get(entity.getClass()) != null)
                return handles.get(entity.getClass()).invoke(entity);
            else {
                Method entity_getHandle = entity.getClass().getMethod("getHandle");
                handles.put(entity.getClass(), entity_getHandle);
                return entity_getHandle.invoke(entity);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static void sendPacket(Player p, Object packet) throws IllegalArgumentException {
        try {
            if (player_connection == null){
                player_connection = NmsUtilites.getHandle(p).getClass().getField("b");
                for (Method m : player_connection.get(NmsUtilites.getHandle(p)).getClass().getMethods()){
                    if (m.getName().equalsIgnoreCase("sendPacket")){
                        player_sendPacket = m;
                    }
                }
            }
            player_sendPacket.invoke(player_connection.get(NmsUtilites.getHandle(p)), packet);
        }
        catch (IllegalAccessException ex){
            FluentPlugin.logError("Nms Exception");
            ex.printStackTrace();
        }
        catch (InvocationTargetException ex){
            FluentPlugin.logError("Nms Exception");
            ex.printStackTrace();
        }
        catch (NoSuchFieldException ex){
            FluentPlugin.logError("Nms Exception");
            ex.printStackTrace();
        }
    }
    public static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();

    public static Class<?> getPrimitiveType(Class<?> Class) { return CORRESPONDING_TYPES.containsKey(Class) ? CORRESPONDING_TYPES.get(Class) : Class; }

    public static Class<?>[] toPrimitiveTypeArray(Class<?>[] Classes) {
        int L = Classes != null ? Classes.length : 0;
        Class<?>[] T = new Class<?>[L];
        for(int i = 0; i < L; i++) T[i] = getPrimitiveType(Classes[i]);
        return T;
    }

    public static boolean equalsTypeArray(Class<?>[] Value1, Class<?>[] Value2) {
        if(Value1.length != Value2.length) return false;
        for(int i = 0; i < Value1.length; i++) if(!Value1[i].equals(Value2[i]) && !Value1[i].isAssignableFrom(Value2[i])) return false;
        return true;
    }

    public static boolean classListEqual(Class<?>[] Value1, Class<?>[] Value2) {
        if(Value1.length != Value2.length) return false;
        for(int i = 0; i < Value1.length; i++) if(Value1[i] != Value2[i]) return false;
        return true;
    }



    public static boolean useNewVersion() {
        try {
            Class.forName("net.minecraft.server." + getVersion() + "ContainerAccess");
            return true;
        } catch (Exception e) { return false; }
    }

    public static Field getField(Class<?> Class, String Field) {
        try {
            Field F = Class.getDeclaredField(Field);
            F.setAccessible(true);
            return F;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String ClassName)
    {
        Class<?> C = null;
        try { return Class.forName("net.minecraft.server." + getVersion() + ClassName); } catch (Exception e) { e.printStackTrace(); }
        return C;
    }
    public static Class<?> getBukkitClass(String ClassName)
    {
        Class<?> C = null;
        try { return Class.forName("org.bukkit.craftbukkit." + getVersion() + ClassName); } catch (Exception e) { e.printStackTrace(); }
        return C;
    }
    public static Method getMethod(Class<?> Class, String ClassName, Class<?>... Parameters) {
        for(Method M : Class.getMethods()) if(M.getName().equals(ClassName) && (Parameters.length == 0 || classListEqual(Parameters, M.getParameterTypes()))) {
            M.setAccessible(true);
            return M;
        }
        return null;
    }

    public static Method getMethod(String MethodName, Class<?> Class, Class<?>... Parameters) {
        Class<?>[] T = toPrimitiveTypeArray(Parameters);
        for(Method M : Class.getMethods()) if(M.getName().equals(MethodName) && equalsTypeArray(toPrimitiveTypeArray(M.getParameterTypes()), T)) return M;
        return null;
    }

    public static Object getHandle(Object Object) {
        try { return getMethod("getHandle", Object.getClass()).invoke(Object); }
        catch (Exception e) {
            FluentPlugin.logError("Nms Exception");
            e.printStackTrace();
            return null;
        }
    }

    public static Object getPlayerField(Player Player, String Field) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Object P = Player.getClass().getMethod("getHandle").invoke(Player);
        return P.getClass().getField(Field).get(P);
    }

    public static Object invokeMethod(String MethodName, Object Parameter) {
        try { return getMethod(MethodName, Parameter.getClass()).invoke(Parameter); }
        catch (Exception e) {
            FluentPlugin.logError("Nms Exception");
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethodWithArgs(String MethodName, Object Object, Object... Parameters) {
        try { return getMethod(MethodName, Object.getClass()).invoke(Object, Parameters); }
        catch (Exception e) {
            FluentPlugin.logError("Nms Exception");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean set(Object Object, String Field, Object Value) {
        Class<?> C = Object.getClass();
        while(C != null) {
            try {
                Field F = C.getDeclaredField(Field);
                F.setAccessible(true);
                F.set(Object, Value);
                return true;
            } catch (NoSuchFieldException e) { C = C.getSuperclass(); } catch (Exception e) { throw new IllegalStateException(e); }
        }
        return false;
    }
}
