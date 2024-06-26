package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.amount.Amount;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Sessions;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Metadata;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.roles.Role;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String metadata;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final List<JsonAdaptedCca> ccas = new ArrayList<>();
    private final JsonAdaptedAmount amount;
    private final String attendance;
    private final String sessions;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("roles") List<JsonAdaptedRole> roles,
                             @JsonProperty("CCAs") List<JsonAdaptedCca> ccas,
                             @JsonProperty("amount") JsonAdaptedAmount amount,
                             @JsonProperty("attendance") String attendance,
                             @JsonProperty("sessions") String sessions,
                             @JsonProperty("metadata") String metadata) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        if (ccas != null) {
            this.ccas.addAll(ccas);
        }
        this.amount = amount;
        this.attendance = attendance;
        this.sessions = sessions;
        this.metadata = metadata;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        metadata = source.getMetadata().map(m -> m.metadata).orElse(Metadata.NO_METADATA_STRING);
        amount = new JsonAdaptedAmount(source.getAmount());
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
        ccas.addAll(source.getCcas().stream()
                .map(JsonAdaptedCca::new)
                .collect(Collectors.toList()));
        attendance = source.getAtt().value;
        sessions = source.getSess().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Role> personRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            personRoles.add(role.toModelType());
        }

        final List<Cca> personCcas = new ArrayList<>();
        for (JsonAdaptedCca cca : ccas) {
            personCcas.add(cca.toModelType());
        }

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

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount.getValue())) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        boolean isNullMetadata = metadata.equals(Metadata.NO_METADATA_STRING);
        if (metadata == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Metadata.class.getSimpleName()));
        }
        if (!isNullMetadata && !Metadata.isValidMetadata(metadata)) {
            throw new IllegalValueException(Metadata.MESSAGE_CONSTRAINTS);
        }
        final Optional<Metadata> modelMetadata = isNullMetadata
            ? Optional.empty()
            : Optional.of(new Metadata(metadata));

        final Amount modelAmount = amount.toModelType();

        if (attendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Attendance.class.getSimpleName()));
        }
        if (!Attendance.isValidAttendance(attendance)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        final Attendance modelAtt = new Attendance(attendance);

        if (sessions == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Sessions.class.getSimpleName()));
        }
        if (!Sessions.isValidSessions(sessions)) {
            throw new IllegalValueException(Sessions.MESSAGE_CONSTRAINTS);
        }
        final Sessions modelSess = new Sessions(sessions);

        if (isMoreThanSess(modelAtt, modelSess)) {
            throw new IllegalValueException(Attendance.MESSAGE_LESS_THAN_CONSTRAINT);
        }
        final Address modelAddress = new Address(address);
        final Set<Role> modelRoles = new HashSet<>(personRoles);
        final Set<Cca> modelCcas = new HashSet<>(personCcas);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelRoles, modelCcas, modelAmount,
                modelAtt, modelSess, modelMetadata);
    }

    public boolean isMoreThanSess(Attendance attendance, Sessions sessions) {
        return attendance.getValue() > sessions.getValue();
    }
}
