package maids.library.management.resource.boundry;

import lombok.RequiredArgsConstructor;
import maids.library.management.aspects.LogExecutionA;
import maids.library.management.resource.control.RecordController;
import maids.library.management.resource.model.BorrowingRecordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordResource {

    private final RecordController borrowingRecordCtrl;

    @LogExecutionA
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        BorrowingRecordDTO record = borrowingRecordCtrl.borrowBook(bookId, patronId);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @LogExecutionA
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        BorrowingRecordDTO record = borrowingRecordCtrl.returnBook(bookId, patronId);
        return ResponseEntity.ok(record);
    }
}
