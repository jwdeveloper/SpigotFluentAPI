package jw.spigot_fluent_api.web_socket.utility;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;

public class ByteBufferBuilder
{
    private byte[] bytes;

    public ByteBufferBuilder()
    {
        bytes = new byte[0];
    }
    public ByteBufferBuilder writeInt(int value)
    {
        bytes = ArrayUtils.addAll(bytes, ByteBuffer.allocate(4).putInt(value & 0xFF).array());
        return  this;
    }
    public ByteBufferBuilder writeByte(byte value)
    {
        bytes = ArrayUtils.addAll(bytes, ByteBuffer.allocate(1).put(value).array());
        return  this;
    }
    public ByteBufferBuilder writeByte(boolean value)
    {
        bytes = ArrayUtils.addAll(bytes, ByteBuffer.allocate(1).put((byte)(value?1:0)).array());
        return  this;
    }
    public ByteBuffer build()
    {
        return ByteBuffer.wrap(bytes);
    }
}
