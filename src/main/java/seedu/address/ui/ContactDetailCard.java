package seedu.address.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.ClipboardManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * An UI component that displays {@code ContactDetail} of a {@code Person}.
 */

public class ContactDetailCard extends UiPart<Region> {
    private static final String FXML = "ContactDetailCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;

    @FXML
    private Label nameLabel;
    @FXML
    private Label nameView;

    @FXML
    private Label phoneLabel;
    @FXML
    private Label phoneView;

    @FXML
    private Label emailLabel;
    @FXML
    private Label emailView;

    @FXML
    private Label socialMediaLabel;
    @FXML
    private FlowPane socialMedias;

    @FXML
    private Label tagsLabel;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code ContactDetailCard} of the given {@code Person} to be displayed on the RHS Window.
     */
    public ContactDetailCard(Person person) {
        super(FXML);
        this.person = person;
        ClipboardManager clipboard = new ClipboardManager();

        nameView.setText(person.getName().fullName);
        nameView.setOnMouseClicked(event ->
                clipboard.copy(person.getName().fullName)
        );
        phoneView.setText(person.getPhone().value);
        phoneView.setOnMouseClicked(event ->
                clipboard.copy(person.getPhone().value)
        );
        emailView.setText(person.getEmail().value);
        emailView.setOnMouseClicked(event ->
                clipboard.copy(person.getEmail().value)
        );

        phoneLabel.setText("Phone:");
        phoneLabel.setOnMouseClicked(event ->
                clipboard.copy("Phone")
        );

        emailLabel.setText("Email:");
        emailLabel.setOnMouseClicked(event ->
                clipboard.copy("Email")
        );

        tagsLabel.setText("Tags");
        tagsLabel.setOnMouseClicked(event ->
                clipboard.copy("Tags")
        );

        socialMediaLabel.setText("Social Media");
        socialMediaLabel.setOnMouseClicked(event ->
                clipboard.copy("Social Media")
        );

        AtomicInteger index = new AtomicInteger(1);


        if (person.getTags().size() == 0) {
            tags.getChildren().add(new Label("-"));
        } else {
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label taglabel = new Label(index + ". " + tag.tagName);
                        taglabel.setOnMouseClicked(event ->
                                clipboard.copy(tag.tagName)
                        );
                        tags.getChildren().add(taglabel);
                        index.addAndGet(1);
                    });
            index.set(1);
        }

        if (person.getSocialMedias().size() == 0) {
            socialMedias.getChildren().add(new Label("-"));
        } else {
            person.getSocialMedias().stream()
                    .sorted(Comparator.comparing(sm -> sm.platformName.getValue()))
                    .forEach(sm -> {
                        
                        Label label = new Label(sm.getPlatformName()
                                + ": " + sm.getPlatformDescription());
                        label.setOnMouseClicked(event ->
                                clipboard.copy(sm.getPlatformDescription().getValue())
                        );
                        socialMedias.getChildren().add(label);

                       
                    });
            index.set(1);
        }


    };



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactDetailCard)) {
            return false;
        }

        // state check
        ContactDetailCard card = (ContactDetailCard) other;
        return person.equals(card.person);
    }

}
