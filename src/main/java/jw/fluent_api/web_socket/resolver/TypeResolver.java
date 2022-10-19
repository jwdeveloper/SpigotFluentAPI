package jw.fluent_api.web_socket.resolver;
import java.nio.ByteBuffer;

public interface TypeResolver
{
     Object resolve(int currentIndex, ByteBuffer buffer);

     int typeSize();
}
