package commands;

import jw.spigot_fluent_api.legacy_commands.FluentCommand;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FluentCommandTest
{
    private FluentCommand command;

    @Before
    public void setUp() {
        command = Mockito.mock(FluentCommand.class);
    }

    @Ignore
    @Test
    public void test()
    {
        when(command.setParent(any())).thenReturn(command);
        verify(command,times(1)).setParent(command);
    }
}
