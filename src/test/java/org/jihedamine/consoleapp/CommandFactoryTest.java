package org.jihedamine.consoleapp;

import org.jihedamine.consoleapp.command.Command;
import org.jihedamine.consoleapp.command.ExitCommand;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryTest {

    @Mock
    LotteryDraw lotteryDrawMock;

    @Test
    public void testGetCommandNotFound() {
        CommandFactory factory = new CommandFactory();
        Command cmd = factory.getCommand("not found");
        assertTrue(cmd instanceof CommandFactory.NotFoundCommand);
        assertEquals("Command not found", cmd.execute());
        assertEquals("", cmd.getId());
    }

    @Test
    public void testNullStringCommand() {
        ExitCommand exitCommand = new ExitCommand();
        CommandFactory factory = new CommandFactory(exitCommand);
        Command cmd = factory.getCommand(null);
        assertTrue(cmd instanceof CommandFactory.NotFoundCommand);
        assertEquals("Command not found", cmd.execute());
    }

    @Test
    public void testExitCommand() {
        ExitCommand exitCommand = new ExitCommand();
        CommandFactory factory = new CommandFactory(exitCommand);
        Command cmd = factory.getCommand(exitCommand.getId());
        assertTrue(cmd instanceof ExitCommand);
        assertEquals("", cmd.execute());
    }

}
