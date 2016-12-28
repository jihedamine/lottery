package org.jihedamine.consoleapp.command.lottery;

import org.jihedamine.consoleapp.CommandFactory;
import org.jihedamine.consoleapp.command.Command;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawAlreadyHappenedException;
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
public class DrawLotteryCommandTest {

    @Mock
    LotteryDraw lotteryDrawMock;

    @Test
    public void testDrawLotteryCommand() {
        String drawnBallsString = "abcd";
        DrawLotteryCommand drawLotteryCommand = new DrawLotteryCommand(lotteryDrawMock);
        when(lotteryDrawMock.getDrawnBallsAsString()).thenReturn(drawnBallsString);
        CommandFactory factory = new CommandFactory(drawLotteryCommand);
        Command cmd = factory.getCommand(drawLotteryCommand.getId());
        assertEquals(drawnBallsString, cmd.execute());
    }

    @Test
    public void testDrawAlreadyHappened() {
        String drawnBallsString = "abcd";
        DrawLotteryCommand drawLotteryCommand = new DrawLotteryCommand(lotteryDrawMock);
        doThrow(new LotteryDrawAlreadyHappenedException()).when(lotteryDrawMock).draw();
        when(lotteryDrawMock.getDrawnBallsAsString()).thenReturn(drawnBallsString);
        CommandFactory factory = new CommandFactory(drawLotteryCommand);
        Command cmd = factory.getCommand(drawLotteryCommand.getId());
        String expected = "Lottery Draw already happened" + System.lineSeparator() + "abcd";
        assertEquals(expected, cmd.execute());
    }


}
