package maids.library.management.resource.control;

import lombok.RequiredArgsConstructor;
import maids.library.management.exceptions.InsertionFailedException;
import maids.library.management.exceptions.NotFoundException;
import maids.library.management.repos.book.entity.Book;
import maids.library.management.repos.book.repo.BookRepo;
import maids.library.management.repos.patron.entity.Patron;
import maids.library.management.repos.patron.repo.PatronRepo;
import maids.library.management.repos.record.entity.BorrowingRecord;
import maids.library.management.repos.record.repo.RecordRepo;
import maids.library.management.resource.model.BorrowingRecordDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordController {

    private final RecordRepo borrowingRecordRepo;
    private final BookRepo bookRepo;
    private final PatronRepo patronRepo;

    @Transactional
    public BorrowingRecordDTO borrowBook(Long bookId, Long patronId) {
        Optional<Book> book = bookRepo.findById(bookId);
        Optional<Patron> patron = patronRepo.findById(patronId);

        if (book.isEmpty()) {
            throw new NotFoundException("Book with ID " + bookId + " not found.");
        }
        if (patron.isEmpty()) {
            throw new NotFoundException("Patron with ID " + patronId + " not found.");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book.get());
        record.setPatron(patron.get());
        record.setBorrowingDate(new Timestamp(System.currentTimeMillis()));

        try {
            borrowingRecordRepo.save(record);
            return convertToSummaryModel(record);
        } catch (Exception ex) {
            throw new InsertionFailedException();
        }
    }

    @Transactional
    public BorrowingRecordDTO returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> recordOpt = borrowingRecordRepo.findByBookIdAndPatronId(bookId, patronId);

        if (recordOpt.isEmpty()) {
            throw new NotFoundException("Borrowing record for book ID " + bookId + " and patron ID " + patronId + " not found.");
        }

        BorrowingRecord record = recordOpt.get();
        record.setReturnDate(new Timestamp(System.currentTimeMillis()));

        try {
            borrowingRecordRepo.save(record);
            return convertToSummaryModel(record);
        } catch (Exception ex) {
            throw new InsertionFailedException();
        }
    }

    private BorrowingRecordDTO convertToSummaryModel(BorrowingRecord record) {
        return new BorrowingRecordDTO(
                record.getId(),
                record.getBook().getId(),
                record.getPatron().getId(),
                record.getBorrowingDate(),
                record.getReturnDate()
        );
    }
}
