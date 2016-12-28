package org.jihedamine.consoleapp.command;

/**
 * This class provides a skeletal implementation of the {@link Command} interface to minimize the efforts to implement this interface.
 * <p>
 * This class defines a field to store the command arguments and provides implementations
 * for the {@link #setArguments(String[])}, {@link #equals(Object)} and {@link #hashCode()} methods.
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public abstract class AbstractCommand implements Command {
    /**
     * Array of strings containing the arguments of the command
     */
    protected String[] args;

    /**
     * {@inheritDoc}
     * @param args {@inheritDoc}
     */
    @Override
    public void setArguments(String[] args) {
        this.args = args;
    }

    /**
     * Two commands are equal if they have the same identifier returned by {@link #getId()}.
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     *         argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AbstractCommand that = (AbstractCommand) obj;

        return this.getId().equals(that.getId());
    }

    /**
     * The hashCode of a command is computed from its identifier returned by {@link #getId()}.
     * @see Object#hashCode
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = 21;
        result = 37 * result + this.getId().hashCode();
        return result;
    }
}
