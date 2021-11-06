package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.EMPTY_PREFIX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input argument and creates a new DisplayCommand object
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {

    public static final String MESSAGE_INDEX_NOT_PARSED = "The index entered is not within the allowed range. "
            + "The index cannot be bigger than 2147483647 (i.e., MAX_VALUE).";

    /**
     * Parses the given {@code String} of argument and returns a DisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        List<String> argList = argMultimap.getAllValues(EMPTY_PREFIX);

        Index index;
        try {
            tryParseIndex(argList);
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE), ive);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INDEX_NOT_PARSED), e);
        }
        return new DisplayCommand(index);
    }

    /**
     * Extracts the last {@code argument} from {@code List<String> argList}.
     */
    private String extractLastArgument(List<String> argList) {
        String last = argList.get(argList.size() - 1); // category is the first argument
        return last;
    }

    /**
     * Checks whether the parameter that has been input is a whole number (can also be a whole number greater
     * than Integer.MAX_VALUE
     *
     * @param arg the parameter that has been entered by the user
     * @return
     */
    public boolean isWholeNumber(String arg) {
        try {
            for (char c : arg.toCharArray()) {
                Integer.parseInt(String.valueOf(c));
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if there are any parameters input by the user
     * @param list The list that stores the parameters
     * @return a boolean indicating whether there is a parameter input or not
     */
    public boolean hasParameterValue(List<String> list) {
        for (String s: list) {
            if (!s.equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tries to parse the input of the user to get the integer index
     * @param argList List that stores the user input
     */
    public void tryParseIndex(List<String> argList) throws NumberFormatException {
        if (!hasParameterValue(argList)) {
            return;
        }
        String lastArgument = extractLastArgument(argList);
        if (isWholeNumber(lastArgument)) {
            Integer.parseInt(lastArgument);
        }
    }
}
