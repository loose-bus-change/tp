package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.IsCalled;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.interests.Interest;
import seedu.address.model.person.interests.InterestsList;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String isCalled;
    private final String gender;
    private final String age;
    private final List<JsonAdaptedInterest> interests = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("isCalled") String isCalled,
                             @JsonProperty("address") String address, @JsonProperty("gender") String gender,
                             @JsonProperty("age") String age,
                             @JsonProperty("interests") List<JsonAdaptedInterest> interests) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isCalled = isCalled;
        this.address = address;
        this.gender = gender;
        this.age = age;
        if (interests != null) {
            this.interests.addAll(interests);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        isCalled = source.getIsCalled().value ? "TRUE" : "FALSE";
        address = source.getAddress().value;
        gender = source.getGender().value;
        age = source.getAge().value;
        interests.addAll(source.getAllInterests()
                .stream()
                .map(JsonAdaptedInterest::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (isCalled == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IsCalled.class.getSimpleName()));
        }

        if (!(IsCalled.isValidIsCalled(isCalled))) {
            throw new IllegalValueException(IsCalled.MESSAGE_CONSTRAINTS);
        }

        final IsCalled modelIsCalled = new IsCalled(isCalled);

        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        final Address modelAddress = new Address(address);

        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }

        final Gender modelGender = new Gender(gender);

        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }

        final Age modelAge = new Age(age);

        final InterestsList modelInterests = new InterestsList();

        for (JsonAdaptedInterest interest : interests) {
            Interest i = interest.toModelType();
            if (modelInterests.checkDuplicate(i)) {
                throw new IllegalValueException(InterestsList.MESSAGE_CONSTRAINTS);
            } else {
                modelInterests.addInterest(i);
            }
        }

        return new Person(modelName, modelPhone, modelEmail, modelIsCalled, modelAddress,
                modelGender, modelAge, modelInterests);
    }

}
