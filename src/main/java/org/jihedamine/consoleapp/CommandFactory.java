package org.jihedamine.consoleapp;

import org.jihedamine.consoleapp.command.AbstractCommand;
import org.jihedamine.consoleapp.command.Command;

import java.util.*;

/**
 * A CommandFactory holds a reference to a set of commands.
 * A command is retrieved from the CommandFactory by calling the {@link #getCommand(String)} method
 * with a String parameter indicating the command id, and an eventual list of arguments separated by spaces.
 * <p>
 * The CommandFactory comes with a {@link NotFoundCommand} that cannot be instantiated from outside the CommandFactory.
 * {@link NotFoundCommand} is returned by {@link #getCommand(String)}
 * when the factory cannot find a command with the id passed in the String parameter of {@link #getCommand(String)}.
 * <p>
 * The CommandFactory also comes with a {@link HelpCommand} that cannot be instantiated from outside the CommandFactory.
 * {@link HelpCommand} displays the list of ids and descriptions of the commands available in the current CommandFactory.
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 * @see Command
 */
public class CommandFactory {

    // A static CommandFactory instance is made available
    // to all instances of CommandFactory when the CommandFactory class is loaded
    private static final Command notFoundCommand = new NotFoundCommand();

    // Set of commands of the CommandFactory instance
    private Set<Command> commandSet;

    /**
     * Constructs a CommandFactory
     *
     * @param commands List of commands that, in addition to the {@link NotFoundCommand} and {@link HelpCommand},
     *                 form the set of commands of the factory.
     */
    public CommandFactory(Command... commands) {
        this.commandSet = new HashSet<>(Arrays.asList(commands));
        this.commandSet.add(new HelpCommand(commands));
    }

    /**
     * Parses a String representing a command id and an eventual list of arguments
     * and returns the corresponding command or {@link NotFoundCommand} if the command is not found
     *
     * @param inputString String representing a command id and an eventual list of arguments.
     *                    The command id and the arguments are separated by spaces (e.g. {@code commandId arg1 arg2})
     * @return The corresponding command or {@link NotFoundCommand} if the command is not found
     */
    public Command getCommand(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return notFoundCommand;
        }

        String[] commandTokens = inputString.split("\\s+");
        String commandName = commandTokens[0];

        Command command = commandSet.stream()
                .filter(cmd -> commandName.equals(cmd.getId()))
                .findAny()
                .orElse(notFoundCommand);

        command.setArguments(Arrays.copyOfRange(commandTokens, 1, commandTokens.length));

        return command;
    }

    /**
     * This class is a Command returned by the CommandFactory when it cannot find a Command in its {@link #commandSet}
     * that has the identifier passed in the String parameter of {@link #getCommand(String)}
     * The NotFoundCommand does not do any processing.
     */
    static class NotFoundCommand extends AbstractCommand {

        private static final String COMMAND_ID = "";

        private NotFoundCommand() {
            // Only CommandFactory should be able to instantiate a NotFoundCommand
        }

        /**
         * The NotFoundCommand's execute method does not do any processing
         *
         * @return A String indicating that the command was not found
         */
        @Override
        public String execute() {
            return "Command not found";
        }

        /**
         * {@inheritDoc}
         *
         * @return {@value #COMMAND_ID} {@inheritDoc}
         */
        @Override
        public String getId() {
            return COMMAND_ID;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public String getDescription() {
            return "Command run when the issued command can't be found";
        }
    }

    /**
     * This class is a Command that displays the list of ids and descriptions
     * of the commands available in the current CommandFactory.
     * <p>
     * Sample usage:
     * <pre>
     * <code>> {@value COMMAND_ID}
     * Available commands:
     * - help: Displays the list of available commands
     * - exit: Quits the application
     * </code>
     */
    class HelpCommand extends AbstractCommand {
        public static final String COMMAND_ID = "help";

        // The help message displayed by HelpCommand
        private final String helpMessage;

        // Only the enclosing Factory should be able to instantiate HelpCommand
        private HelpCommand(Command... commands) {
            List<Command> commandList = new ArrayList<>(Arrays.asList(commands));
            // add the help command to the list of commands displayed in the help message
            commandList.add(this);
            // The help message is build once when the HelpCommand is constructed
            this.helpMessage = buildHelpMessage(commandList);
        }

        /**
         * Builds a String containing the list of ids and descriptions of the commands
         * available in the current CommandFactory.
         *
         * @return A String containing the list of ids and descriptions of the commands
         * available in the current CommandFactory.
         */
        @Override
        public String execute() {
            return helpMessage;
        }

        /**
         * Builds a help message
         *
         * @param commands Commands the help message will list
         * @return A String containing the list of ids and descriptions of the commands
         * available in the current CommandFactory.
         */
        private String buildHelpMessage(List<Command> commands) {
            StringBuilder sb = new StringBuilder();
            sb.append("Available commands:");
            commands.forEach(cmd -> {
                sb.append(System.lineSeparator());
                sb.append(" - ");
                sb.append(cmd.getId());
                sb.append(": ");
                sb.append(cmd.getDescription());
            });

            return sb.toString();
        }

        /**
         * {@inheritDoc}
         *
         * @return {@value #COMMAND_ID} {@inheritDoc}
         */
        @Override
        public String getId() {
            return COMMAND_ID;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public String getDescription() {
            return "Displays the list of available commands";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            HelpCommand that = (HelpCommand) obj;

            return super.equals(that) && this.helpMessage.equals(that.helpMessage);
        }

        @Override
        public int hashCode() {
            return 37 * super.hashCode() + this.helpMessage.hashCode();
        }
    }
}
