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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PurchaseLotteryTicketCommandTest {

    @Mock
    LotteryDraw lotteryDrawMock;

    @Test
    public void testPurchaseLotteryTicket() {
        PurchaseLotteryTicketCommand purchaseCommand = new PurchaseLotteryTicketCommand(lotteryDrawMock);
        when(lotteryDrawMock.purchaseTicket(anyString())).thenReturn(1);
        CommandFactory factory = new CommandFactory(purchaseCommand);
        Command cmd = factory.getCommand(purchaseCommand.getId() + " arg1");
        assertTrue(cmd.execute().startsWith("Purchased number for arg1"));
    }

    @Test
    public void testPurchaseLotteryTicketTwoArguments() {
        PurchaseLotteryTicketCommand purchaseCommand = new PurchaseLotteryTicketCommand(lotteryDrawMock);
        when(lotteryDrawMock.purchaseTicket(anyString())).thenReturn(1);
        CommandFactory factory = new CommandFactory(purchaseCommand);
        Command cmd = factory.getCommand(purchaseCommand.getId() + " arg1" + " arg2");
        assertTrue(cmd.execute().startsWith("Purchased number for arg1"));
    }

    @Test
    public void testPurchaseLotteryTicketMissingArgument() {
        PurchaseLotteryTicketCommand purchaseCommand = new PurchaseLotteryTicketCommand(lotteryDrawMock);
        CommandFactory factory = new CommandFactory(purchaseCommand);
        Command cmd = factory.getCommand(purchaseCommand.getId());
        String expected = "Missing participant first name argument (usage: purchase <first name of participant>)";
        assertEquals(expected, cmd.execute());
    }

    @Test
    public void testLotteryDrawPurchaseTicketThrowsException() {
        when(lotteryDrawMock.purchaseTicket(anyString())).thenThrow(new LotteryDrawAlreadyHappenedException());
        PurchaseLotteryTicketCommand purchaseCommand = new PurchaseLotteryTicketCommand(lotteryDrawMock);

        CommandFactory factory = new CommandFactory(purchaseCommand);
        Command cmd = factory.getCommand(purchaseCommand.getId() + " arg1");
        String expected = "Lottery Draw already happened";
        assertEquals(expected, cmd.execute());
    }

}
