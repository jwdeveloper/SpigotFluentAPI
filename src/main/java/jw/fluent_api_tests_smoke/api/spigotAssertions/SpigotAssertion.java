package jw.fluent_api_tests_smoke.api.spigotAssertions;

import java.util.Collection;
import java.util.List;

public class SpigotAssertion
{
    public static void shouldBeNotNull(Object object) throws Exception {
        if(object == null)
        {
            throw new Exception("null object");
        }
    }

    public static void shouldBeTrue(boolean object) throws Exception {
        if(object == false)
        {
           throw  new Exception("should be true");
        }
    }

    public static void shouldBeEqual(Object o1, Object o2) throws Exception {


        if(!o1.equals(o2))
        {
            throw  new Exception("should be equal");
        }
    }

    public  static <T> void shouldContains(List<T> list, T object) throws Exception {
        if(!list.contains(object))
        {
            throw  new Exception("list not contains object");
        }
    }

    public  static <T> void shouldNotContains(List<T> list, T object) throws Exception {
        if(list.contains(object))
        {
            throw  new Exception("list should not contains object");
        }
    }

    public static void shouldBeFalse(boolean object) throws Exception {
        if(object == true)
        {
            throw new Exception("should be false");
        }
    }

    public static <T> void shouldBeNotEmpty(Collection<T> object) throws Exception {
        shouldBeNotNull(object);
        if(object.size() == 0)
        {
            throw new Exception("should not be empty");
        }
    }
}
