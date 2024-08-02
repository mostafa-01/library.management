package maids.library.management.resource.boundry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.library.management.repos.book.entity.Book;
import maids.library.management.resource.control.BookController;
import maids.library.management.resource.model.BookAddDTO;
import maids.library.management.resource.model.BookSummaryModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookResource {

    private final BookController bookCtrl;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponse(responseCode = "200", description = "List of books retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No books found")
    @GetMapping
    public List<BookSummaryModel> getAllBooks() {
        return bookCtrl.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book with the specified ID not found")
    })
    public ResponseEntity<BookSummaryModel> getBookById(@PathVariable Long id) {
        BookSummaryModel book = bookCtrl.getBookSummaryById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Add a new book to the library")
    @ApiResponse(responseCode = "201", description = "Book created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<BookSummaryModel> createBook(@Valid @RequestBody BookAddDTO bookDTO) {

        return new ResponseEntity<>(bookCtrl.insertNewBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update details of an existing book")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book with the specified ID not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<BookSummaryModel> updateBook(@PathVariable Long id, @Valid @RequestBody BookAddDTO bookDTO) {
        Optional<Book> existingBook = bookCtrl.getBookById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();

            return ResponseEntity.ok(bookCtrl.updateBook(bookDTO));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Remove a book from the library by its ID")
    @ApiResponse(responseCode = "204", description = "Book deleted successfully")
    @ApiResponse(responseCode = "404", description = "Book with the specified ID not found")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookCtrl.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
