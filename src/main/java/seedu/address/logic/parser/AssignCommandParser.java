package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class AssignCommandParser implements Parser<AssignCommand> {
    @Override
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), pe);
        }

        AssignCommand.AssignPersonDescriptor assignPersonDescriptor = new AssignCommand.AssignPersonDescriptor();

        parseRolesForAssign(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(assignPersonDescriptor::setRole);

        System.out.println(assignPersonDescriptor.toString());

        if (!assignPersonDescriptor.isAnyFieldNotEdited()) {
            throw new ParseException(AssignCommand.MESSAGE_NOT_ASSIGNED);
        }

        return new AssignCommand(index, assignPersonDescriptor);

    }

    private Optional<Set<Tag>> parseRolesForAssign(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
