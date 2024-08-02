package maids.library.management.resource.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class PatronSummaryModel {

    private Long id;

    @NotEmpty(message = "name is required")
    @Pattern(regexp = "^[a-zA-Z.]+$", message = "Publisher must contain only English alphabet characters and periods")
    private String name;

    @NotEmpty(message = "phone number is required")
    @Pattern(regexp = "^0\\d{10}$", message = "phone number must be 11 digits long, start with 0, and contain only numbers")
    private String phoneNumber;
}
