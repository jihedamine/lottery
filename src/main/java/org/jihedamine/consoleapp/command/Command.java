package org.jihedamine.consoleapp.command;

/**
 * A command is an operation that has an identifier and zero to many arguments.
 * <p>
 * Each class implementing the Command interface defines its own execution instructions.
 * The command execution is triggered with the {@link #execute()} method, which returns a String message informing about the command execution result.
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public interface Command {

    /**
     * Triggers the execution of the command.
     * <p>
     * Each class implementing the Command interface defines its own execution instructions.
     * @return A String message informing about the command execution result
     */
    String execute();

    /**
     * Returns the identifier of the command.
     * Each class implementing the Command interface has its unique identifier.
     * @return The identifier of the command.
     */
    String getId();

    /**
     * Returns the description of the command.
     * Each class implementing the Command interface has its own description.
     * @return The description of the command.
     */
    String getDescription();

    /**
     * Sets arguments for the command.
     * The arguments are represented as an array of Strings.
     * They can provide context for the {@link #execute()} method.
     * <p>
     * Example: A <code>delete</code> command takes the name of the file to delete as argument
     * @param args The arguments to pass to the command
     */
    void setArguments(String[] args);
}
