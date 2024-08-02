package maids.library.management.repos.record.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maids.library.management.repos.book.entity.Book;
import maids.library.management.repos.patron.entity.Patron;

import java.sql.Timestamp;

@Entity
@Table(name = "BORROWING_RECORD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @Column(nullable = false)
    private Timestamp borrowingDate;

    @Column(nullable = true)
    private Timestamp returnDate;
}
