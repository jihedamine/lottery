package org.jihedamine.consoleapp.command;

import org.jihedamine.consoleapp.command.lottery.DrawLotteryCommand;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommandsTest {

    @Mock
    LotteryDraw lotteryDrawMock;

    @Test
    public void testExitCommand() {
        Command cmd = new ExitCommand();
        assertEquals("", cmd.execute());
    }

    @Test
    public void testSameCommandsReferencesAreEqual() {
        Command cmd = new ExitCommand();
        Command cmd2 = cmd;
        assertEquals(cmd, cmd2);
    }

    @Test
    public void testCommandsAreEqual() {
        Command cmd = new ExitCommand();
        Command cmd2 = new ExitCommand();
        assertEquals(cmd, cmd2);
    }

    @Test
    public void testCommandsAreNotEqual() {
        Command cmd = new ExitCommand();
        Command cmd2 = new DrawLotteryCommand(lotteryDrawMock);
        assertNotEquals(cmd, cmd2);
    }
}
