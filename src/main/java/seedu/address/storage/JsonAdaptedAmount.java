// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.amount.Amount;

/**
 * Jackson-friendly version of {@link Amount}.
 */
public class JsonAdaptedAmount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Amount's %s field is missing!";

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedAmount} with the given amount details.
     */
    @JsonCreator
    public JsonAdaptedAmount(JsonNode jsonNode) {
        this.value = jsonNode.get("value").asText();
    }

    /**
     * Converts a given {@code Amount} into this class for Jackson use.
     */
    public JsonAdaptedAmount(Amount source) {
        value = source.value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * Converts this Jackson-friendly adapted amount object into the model's
     * {@code Amount} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted amount.
     */
    public Amount toModelType() throws IllegalValueException {
        if (!Amount.isValidAmount(value)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(value);
    }
}
