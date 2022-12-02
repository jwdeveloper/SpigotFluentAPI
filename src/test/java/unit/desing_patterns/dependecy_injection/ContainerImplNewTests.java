package unit.desing_patterns.dependecy_injection;

import integration.desing_patterns.dependecy_injection.example_classes.ExampleClass;
import jw.fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.EventHandler;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_api.desing_patterns.dependecy_injection.api.search.SearchAgent;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.DefaultContainer;
import jw.fluent_api.logger.api.SimpleLogger;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jw.fluent_api.utilites.java.Pair;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ContainerImplNewTests
{
    private final DefaultContainer sut;
    private final InstanceProvider instanceProviderMock = mock(InstanceProvider.class);
    private final EventHandler eventHandlerMock = mock(EventHandler.class);
    private final SimpleLogger simpleLoggerMock = mock(SimpleLogger.class);
    private final InjectionInfoFactory injectionInfoFactoryMock = mock(InjectionInfoFactory.class);
    private final SearchAgent searchAgentMock = mock(SearchAgent.class);
    private final List<RegistrationInfo> registrationInfos  = new ArrayList<>();

    public ContainerImplNewTests()
    {
        sut = new DefaultContainer(
                searchAgentMock,
                instanceProviderMock,
                eventHandlerMock,
                simpleLoggerMock,
                injectionInfoFactoryMock,
                registrationInfos);
    }


    @Test
    public void register_Should_Success() throws Exception {
        var registrationInfo = new RegistrationInfo(null,null,null, LifeTime.SINGLETON, RegistrationType.InterfaceAndIml);
        var type = ExampleClass.class;
        var pair = new Pair<Class<?>,InjectionInfo>(type,new InjectionInfo());

        when(injectionInfoFactoryMock.create(registrationInfo)).thenReturn(pair);
        when(eventHandlerMock.OnRegistration(new OnRegistrationEvent(pair.key(),pair.value(),registrationInfo))).thenReturn(true);
        //Act
        var result = sut.register(registrationInfo);

        //Assert
        Assert.assertTrue(result);
        Assert.assertEquals(1, registrationInfos.size());
    }


 /*   @Test
    public void find_Should_Fail_When_InstaneProvider_Throw_Exception() throws Exception {
        //Arrange
        var injectionInfo = new InjectionInfo();
        var type = ExampleInjection.class;
        var exception = new Exception();
        registrationInfos.clear();
        registrationInfos.put(type, injectionInfo);

        when(instanceProviderMock.getInstance(injectionInfo, registrationInfos, sut)).thenThrow(exception);
        //Act
        var result = sut.find(type);

        //Assert
        Assert.assertNull(result);
        verify(simpleLoggerMock,times(1)).error("Can not create instance injection of type type ExampleInjection",exception);
    }

    @Test
    public void find_Should_Fail_When_InjectionInfo_Is_Not_Registered() throws Exception {
        //Arrange
        var type = ExampleInjection.class;
        registrationInfos.clear();

        //Act
        var result = sut.find(type);

        //Assert
        Assert.assertNull(result);
        verify(simpleLoggerMock,times(1)).error("Class ExampleInjection has not been register to Dependency Injection container");
    }

    @Test
    public void find_Should_Success() throws Exception {
        //Arrange
        var injectionInfo = new InjectionInfo();
        var type = ExampleInjection.class;
        var instance = new ExampleInjectionImpl();
        registrationInfos.clear();
        registrationInfos.put(type, injectionInfo);

        when(instanceProviderMock.getInstance(injectionInfo, registrationInfos, sut)).thenReturn(instance);
        when(eventHandlerMock.OnInjection(new OnInjectionEvent(type,injectionInfo,instance, registrationInfos, sut))).thenReturn(instance);
        //Act
        var result = sut.find(type);

        //Assert
        Assert.assertEquals(instance, result);
    }

    public class ExampleInjectionImpl implements ExampleInjection
    {

    }

    public interface ExampleInjection
    {

    }*/
}