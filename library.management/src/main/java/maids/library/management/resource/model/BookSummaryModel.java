package maids.library.management.resource.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSummaryModel {


    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Author is required")
    private String author;

    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @NotEmpty(message = "ISBN is required")
    @Size(min = 13, max = 13, message = "ISBN must be a 13-digit number")
//    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    private String isbn;
}
