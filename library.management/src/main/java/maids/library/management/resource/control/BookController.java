package maids.library.management.resource.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import maids.library.management.exceptions.InsertionFailedException;
import maids.library.management.exceptions.NotFoundException;
import maids.library.management.repos.book.entity.Book;
import maids.library.management.repos.book.repo.BookRepo;
import maids.library.management.resource.model.BookAddDTO;
import maids.library.management.resource.model.BookSummaryModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookController {

    private final ObjectMapper objectMapper;
    private final BookRepo repo;

    public List<BookSummaryModel> getAllBooks() {
        List<Book> books = repo.findAll();
        if(books.isEmpty())
            throw new NotFoundException("No Books found! add sum... :)");
        return books.stream()
                .map(this::convertToSummaryModel)
                .toList();
    }

    public BookSummaryModel getBookSummaryById(Long id) {
        Optional<Book> book = repo.findById(id);
        if(book.isPresent())
            return convertToSummaryModel(book.get());
        else
            throw new NotFoundException("Book with Id:"+id+" couldn't be found!");
    }

    public Optional<Book> getBookById(Long id){
        Optional<Book> book = repo.findById(id);
        if(book.isPresent())
            return book;
        else
            throw new NotFoundException("Book with Id:"+id+" couldn't be found!");
    }



    public BookSummaryModel insertNewBook(BookAddDTO bookDTO) {
        try{
            repo.save(convertToEntity(bookDTO));
            BookSummaryModel book;
            book = bookDTO;
            return book;
        }catch (Exception ex){
            throw new InsertionFailedException();
        }
    }

    public BookSummaryModel updateBook(BookAddDTO book) {
        try{
            repo.save(convertToEntity(book));
            return book;
        }catch (Exception ex){
            throw new InsertionFailedException();
        }
    }

    public void deleteBook(Long id) {
        repo.deleteById(id);
    }

    private BookSummaryModel convertToSummaryModel(Book book) {
        return objectMapper.convertValue(book, BookSummaryModel.class);
    }

    private Book convertToEntity (BookAddDTO book) {
        return objectMapper.convertValue(book, Book.class);
    }
}
