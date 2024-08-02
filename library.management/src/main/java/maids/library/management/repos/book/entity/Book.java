package maids.library.management.repos.book.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOK")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column
    private String publisher;

    @Column
    private String genre;

    @Column
    private Integer numberOfPages;

    @Column
    private String language;

    @Column(length = 1000)
    private String summary;
}
