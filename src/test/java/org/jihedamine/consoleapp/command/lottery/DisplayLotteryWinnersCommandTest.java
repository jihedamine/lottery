package org.jihedamine.consoleapp.command.lottery;

import org.jihedamine.consoleapp.CommandFactory;
import org.jihedamine.consoleapp.command.Command;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawDidNotHappenException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DisplayLotteryWinnersCommandTest {

    @Mock
    LotteryDraw lotteryDrawMock;

    @Test
    public void testDisplayLotteryWinnersCommand() {
        String drawnWinnersAsString = "abcd";
        DisplayLotteryWinnersCommand displayLotteryWinnersCommand = new DisplayLotteryWinnersCommand(lotteryDrawMock);
        when(lotteryDrawMock.getDrawWinnersAsString()).thenReturn(drawnWinnersAsString);
        CommandFactory factory = new CommandFactory(displayLotteryWinnersCommand);
        Command cmd = factory.getCommand(displayLotteryWinnersCommand.getId());
        assertEquals(drawnWinnersAsString, cmd.execute());
    }

    @Test
    public void testDrawDidNotHappen() {
        DisplayLotteryWinnersCommand displayLotteryWinnersCommand = new DisplayLotteryWinnersCommand(lotteryDrawMock);
        doThrow(new LotteryDrawDidNotHappenException()).when(lotteryDrawMock).getDrawWinnersAsString();
        CommandFactory factory = new CommandFactory(displayLotteryWinnersCommand);
        Command cmd = factory.getCommand(displayLotteryWinnersCommand.getId());
        String expected = "Lottery draw didn't happen yet";
        assertEquals(expected, cmd.execute());
    }


}
