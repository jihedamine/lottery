package org.jihedamine.consoleapp;

import org.jihedamine.consoleapp.command.*;
import org.jihedamine.consoleapp.command.lottery.DisplayLotteryWinnersCommand;
import org.jihedamine.consoleapp.command.lottery.DrawLotteryCommand;
import org.jihedamine.consoleapp.command.lottery.PurchaseLotteryTicketCommand;
import org.jihedamine.lotterydraw.LotteryDraw;

import java.util.Scanner;

/**
 * LotteryApplication is a frontend application that lets the user interact
 * with a lottery draw by issuing commands via the System input stream.
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class LotteryApplication {

    // The lottery draw for which this LotteryApplication instance provides a frontend
    private LotteryDraw lotteryDraw;

    // The command factory used to get the commands to execute
    private CommandFactory commandFactory;

    /**
     * Constructs a LotteryApplication that serves as a frontend to interact with a lottery draw
     * @param lotteryDraw The lottery draw for which this LotteryApplication instance provides a frontend
     */
    public LotteryApplication(LotteryDraw lotteryDraw) {
        this.lotteryDraw = lotteryDraw;

        this.commandFactory = new CommandFactory(
                new PurchaseLotteryTicketCommand(lotteryDraw),
                new DrawLotteryCommand(lotteryDraw),
                new DisplayLotteryWinnersCommand(lotteryDraw),
                new ExitCommand()
        );
    }

    /**
     * Starts a console prompt that gets commands via the System input stream.
     * <p>
     * The console displays the list of its available commands when it receives the <code>{@value org.jihedamine.consoleapp.CommandFactory.HelpCommand#COMMAND_ID}</code> command
     * <p>
     * The console stops waiting for commands when it receives the <code>{@value org.jihedamine.consoleapp.command.ExitCommand#COMMAND_ID}</code> command
     *
     */
    public void run() {
        Scanner console = new Scanner(System.in);
        String input;
        System.out.println("Welcome to the lottery draw of the month!");
        System.out.printf("This month's lottery contains %d items, %d items will be picked during the draw.%n", lotteryDraw.getNbItems() ,lotteryDraw.getNbDraws());
        System.out.printf("Type %s to display the list of available commands%n", CommandFactory.HelpCommand.COMMAND_ID);
        Command cmd;
        do {
            System.out.print("Please input a command > ");
            input = console.nextLine().trim();
            cmd = commandFactory.getCommand(input);
            System.out.println();
            System.out.println(cmd.execute());
        } while (!(cmd instanceof ExitCommand));
    }

}
