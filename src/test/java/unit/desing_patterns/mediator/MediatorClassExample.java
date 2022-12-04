package unit.desing_patterns.mediator;

import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;

public class MediatorClassExample implements MediatorHandler<Integer,String> {

    public static String TESTOUTPUT = "HELLO WORLD";

    @Override
    public String handle(Integer request) {
        return TESTOUTPUT;
    }
}
