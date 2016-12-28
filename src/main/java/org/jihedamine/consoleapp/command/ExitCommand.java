package org.jihedamine.consoleapp.command;

/**
 * The ExitCommand does not do any processing
 * but should be used by a program receiving commands as a mean for the end user to quit the program.
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class ExitCommand extends AbstractCommand {

    /**
     * The unique identifier of an ExitCommand
     */
    public static final String COMMAND_ID = "exit";

    /**
     * The ExitCommand's execute method does not do any processing
     * @return An empty string
     */
    @Override
    public String execute() {
        return "";
    }

    /**
     * {@inheritDoc}
     * @return {@value #COMMAND_ID} {@inheritDoc}
     */
    @Override
    public String getId() {
        return COMMAND_ID;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Quits the application";
    }
}
