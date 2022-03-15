package seedu.address.logic.commands.delete;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class DeletePersonCommand extends DeleteCommand {
    
    private Target target;

    public DeletePersonCommand(Name name) {
        this.target = Target.of(name, null);
    }

    public DeletePersonCommand(Index index) {
        this.target = Target.of(index, null);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        target.setTargetList(lastShownList);
        Person personToDelete = target.targetPerson();

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && target.equals(((DeletePersonCommand) other).target)); // state check
    }


}
