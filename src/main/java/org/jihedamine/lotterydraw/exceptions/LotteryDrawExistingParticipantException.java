package org.jihedamine.lotterydraw.exceptions;

/**
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class LotteryDrawExistingParticipantException extends RuntimeException {

    public LotteryDrawExistingParticipantException(String firstName) {
        super("Lottery draw already sold a ticket to a participant with the name " + firstName);
    }

}
