package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DisplayCommand;

public class DisplayCommandParserTest {
    private DisplayCommandParser parser = new DisplayCommandParser();

    @Test
    public void parse_validArgs_returnsDisplayCommand() {
        assertParseSuccess(parser, "2", new DisplayCommand(INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "*", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "c", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooBigInt_throwsParseException() {
        long l = 23423498233L;
        String input = String.valueOf(l);
        assertParseFailure(parser, input, String.format(DisplayCommandParser.MESSAGE_INDEX_NOT_PARSED));
    }
}
