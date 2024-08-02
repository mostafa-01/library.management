package maids.library.management.resource.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAddDTO extends BookSummaryModel{

    private String publisher;

    private String genre;

    private Integer numberOfPages;

    private String language;
}
