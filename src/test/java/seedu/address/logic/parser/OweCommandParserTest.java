// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_HEAD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.OweCommand;

public class OweCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            OweCommand.MESSAGE_USAGE);

    private OweCommandParser parser = new OweCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ROLE_HEAD, MESSAGE_INVALID_FORMAT);

        // no role specified
        assertParseFailure(parser, "1", OweCommand.MESSAGE_NOT_OWE);

        // no index and no role specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // invalid index
        assertParseFailure(parser, "a" + VALID_ROLE_HEAD, MESSAGE_INVALID_FORMAT);
    }
}
