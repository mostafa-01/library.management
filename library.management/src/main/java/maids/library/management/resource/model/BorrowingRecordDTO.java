package maids.library.management.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecordDTO {
    private Long id;
    private Long bookId;
    private Long patronId;
    private Timestamp borrowDate;
    private Timestamp returnDate;
}