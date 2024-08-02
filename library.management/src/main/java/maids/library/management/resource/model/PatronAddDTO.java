package maids.library.management.resource.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.sql.Timestamp;

public class PatronAddDTO extends  PatronSummaryModel{

    private String address;

    @NotEmpty(message = "email is required")
    @Email(message = "ex : email@eg.com")
    private String email;

    @NotEmpty(message = "date is required is required")
    private Timestamp membershipDate;
}
