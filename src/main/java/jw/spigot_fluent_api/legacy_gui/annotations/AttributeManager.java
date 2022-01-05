package jw.spigot_fluent_api.legacy_gui.annotations;

public class AttributeManager
{

  /*  private List<MethodAttribute> MethodAttribute = new ArrayList<>();

    public AttributeManager(Class class_)
    {
        scanClass(class_);
    }

    public  List<MethodAttribute> getMethodAttribute()
    {
        return MethodAttribute;
    }

    public void scanClass(Class class_)
    {
        for(Method method :class_.getMethods())
        {

            Annotation[] annotations = method.getAnnotations();
            if(annotations.length == 0)
                continue;
            boolean foundAnyAttributes = false;
            MethodAttribute MethodAttribute = new MethodAttribute(null);
            MethodAttribute.setCustomName(method.getName());
            for(Annotation annotation : annotations)
            {
               if(annotation instanceof Display)
               {
                   MethodAttribute.setCustomName(((Display) annotation).value());
                   foundAnyAttributes=true;
               }

               if(annotation instanceof PermissionGUI)
               {
                   PermissionGUI permissionGUI = (PermissionGUI) annotation;
                   MethodAttribute.addPermission(permissionGUI.action(),permissionGUI.value());
                   foundAnyAttributes=true;
               }
            }

            if(foundAnyAttributes)
              getMethodAttribute().add(MethodAttribute);
        }
    }

    public class MethodAttribute
    {
        private HashMap<ButtonActionsEnum,List<String>> permissions = new HashMap<>();
        private String customName = "";
        private List<String> description = new ArrayList<>();
        private BindedField bindedField = null;

        public MethodAttribute(BindedField bindedField)
        {
            this.bindedField = bindedField;
        }

        public String getCustomName()
        {
            return customName;
        }

        public boolean hasPermissions()
        {
            return permissions.size() != 0;
        }
        public boolean hasCustomName()
        {
            return customName.length() != 0;
        }
        public boolean hasDescription()
        {
            return description.size() != 0;
        }

        public Object getValue()
        {
            return bindedField.getValue();
        }

        public void setCustomName(String customName) {
            this.customName = customName;
        }

        public boolean isPlayerMeetPermisssions(Player player,ButtonActionsEnum ... actionsEnum)
        {
            for(ButtonActionsEnum buttonActionsEnum: actionsEnum)
            {
                if(permissions.containsKey(buttonActionsEnum))
                {
                    List<String> perms = permissions.get(actionsEnum);
                    for(String per : perms)
                    {
                        if(!player.hasPermission(per))
                        {
                          return false;
                        }
                    }
                }
            }
            return true;
        }

        public void addPermission(ButtonActionsEnum actionsEnum,String permission)
        {
           if(permissions.containsKey(actionsEnum))
           {
               List<String> perms = permissions.get(actionsEnum);
               perms.add(permission);
           }
           else
           {
               List<String> perms = new ArrayList<>();
               perms.add(permission);
               permissions.put(actionsEnum,perms);
           }
        }
    }*/

}
