package jw.fluent_api.web_socket.utility;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ByteUtility
{
    public static String getGuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        UUID uuid = new UUID(high, low);
        return uuid.toString();
        //        return UUID.nameUUIDFromBytes(b);
    }

    public byte[] getUUIDAsByte(UUID uuid)
    {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
