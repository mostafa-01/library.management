package maids.library.management.resource;


import com.fasterxml.jackson.databind.ObjectMapper;
import maids.library.management.exceptions.InsertionFailedException;
import maids.library.management.exceptions.NotFoundException;
import maids.library.management.repos.book.entity.Book;
import maids.library.management.repos.book.repo.BookRepo;
import maids.library.management.resource.control.BookController;
import maids.library.management.resource.model.BookAddDTO;
import maids.library.management.resource.model.BookSummaryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        // Setup
        Book book = new Book();
        List<Book> books = List.of(book);
        when(bookRepo.findAll()).thenReturn(books);
        BookSummaryModel bookSummaryModel = new BookSummaryModel();
        when(objectMapper.convertValue(book, BookSummaryModel.class)).thenReturn(bookSummaryModel);

        // Execute
        List<BookSummaryModel> result = bookController.getAllBooks();

        // Verify
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    public void testGetAllBooks_NoBooksFound() {
        // Setup
        when(bookRepo.findAll()).thenReturn(List.of());

        // Execute & Verify
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> bookController.getAllBooks());
        assertEquals("No Books found! add sum... :)", thrown.getMessage());
    }

    @Test
    public void testGetBookSummaryById() {
        // Setup
        Long bookId = 1L;
        Book book = new Book();
        BookSummaryModel bookSummaryModel = new BookSummaryModel();
        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));
        when(objectMapper.convertValue(book, BookSummaryModel.class)).thenReturn(bookSummaryModel);

        // Execute
        BookSummaryModel result = bookController.getBookSummaryById(bookId);

        // Verify
        assertNotNull(result);
        verify(bookRepo, times(1)).findById(bookId);
    }

    @Test
    public void testGetBookSummaryById_BookNotFound() {
        // Setup
        Long bookId = 1L;
        when(bookRepo.findById(bookId)).thenReturn(Optional.empty());

        // Execute & Verify
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> bookController.getBookSummaryById(bookId));
        assertEquals("Book with Id:" + bookId + " couldn't be found!", thrown.getMessage());
    }

    @Test
    public void testInsertNewBook() {
        // Setup
        BookAddDTO bookDTO = new BookAddDTO();
        Book book = new Book();
        when(objectMapper.convertValue(bookDTO, Book.class)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);

        // Execute
        BookSummaryModel result = bookController.insertNewBook(bookDTO);

        // Verify
        assertNotNull(result);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    public void testInsertNewBook_InsertionFailed() {
        // Setup
        BookAddDTO bookDTO = new BookAddDTO();
        Book book = new Book();
        when(objectMapper.convertValue(bookDTO, Book.class)).thenReturn(book);
        when(bookRepo.save(book)).thenThrow(new RuntimeException("Insertion failed"));

        // Execute & Verify
        InsertionFailedException thrown = assertThrows(InsertionFailedException.class, () -> bookController.insertNewBook(bookDTO));
        assertNotNull(thrown);
    }

    @Test
    public void testUpdateBook_Success() {
        // Setup
        Long bookId = 1L;
        BookAddDTO bookDTO = new BookAddDTO();
        Book book = new Book();
        when(objectMapper.convertValue(bookDTO, Book.class)).thenReturn(book);
        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepo.save(book)).thenReturn(book);

        // Expectation: BookSummaryModel should be returned as-is from the DTO
        BookSummaryModel expectedResponse = bookDTO;

        // Execute
        BookSummaryModel result = bookController.updateBook(bookDTO);

        // Verify
        assertEquals(expectedResponse, result);
        verify(bookRepo, times(1)).save(book);
    }


//    @Test
//    public void testUpdateBook_Failure() {
//        // Setup
//        BookAddDTO bookDTO = new BookAddDTO();
//        when(objectMapper.convertValue(bookDTO, Book.class)).thenReturn(new Book());
//        when(bookRepo.findById(anyLong())).thenReturn(Optional.empty());
//
//        // Execute & Verify
//        InsertionFailedException thrown = assertThrows(InsertionFailedException.class, () -> bookController.updateBook(bookDTO));
//        assertNotNull(thrown);
//    }



    @Test
    public void testDeleteBook() {
        // Setup
        Long bookId = 1L;

        // Execute
        bookController.deleteBook(bookId);

        // Verify
        verify(bookRepo, times(1)).deleteById(bookId);
    }
}

