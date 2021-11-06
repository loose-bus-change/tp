package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.FullPersonCard;
import seedu.address.ui.UiManager;

public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays additional details about a "
            + "particular contact in the address book. Contact is chosen based on index.\n"
            + "Parameter: " + " INDEX";

    public static final String MESSAGE_DISPLAY_SUCCESS = "Displayed selected person!";

    private final Index displayIndex;

    /**
     * Creates a DisplayCommand to display the specified {@code Person}
     */
    public DisplayCommand(Index index) {
        requireNonNull(index);
        displayIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> lastShownList = model.getFilteredPersonList();

        assert displayIndex.getOneBased() > 0;

        if (displayIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDisplay = lastShownList.get(displayIndex.getZeroBased());
        assert personToDisplay != null : "person to display cannot be null";

        int intDisplayIndex = displayIndex.getOneBased();
        FullPersonCard.setDisplayedIndex(intDisplayIndex);
        UiManager.displayFunction();

        return new CommandResult(String.format(MESSAGE_DISPLAY_SUCCESS, personToDisplay));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DisplayCommand)) {
            return false;
        }

        DisplayCommand e = (DisplayCommand) other;
        return displayIndex.equals(e.displayIndex);
    }
}
