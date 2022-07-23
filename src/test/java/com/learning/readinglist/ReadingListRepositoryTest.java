package com.learning.readinglist;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ReadingListRepositoryTest {

    @Autowired
    private BookRepository bookRepositoryUnderTest;


    @AfterEach
    void tearDown() {
        bookRepositoryUnderTest.deleteAll();
    }

    @Test
    void findByReaderTest() {
        //given
        Book book_1 = new Book(20L, "1111", "Atoms", "Einstein", "Advanced");
        Book book_2 = new Book(30L, "2222", "Molecules", "Dirac", "Beginner");
        Book book_3 = new Book(40L, "3333", "JAVA", "Einstein", "Medium");
        bookRepositoryUnderTest.save(book_1);
        bookRepositoryUnderTest.save(book_2);
        bookRepositoryUnderTest.save(book_3);
        //when
        List<Book> expectedBook = bookRepositoryUnderTest.findByAuthor("Einstein");
        //then
        assertThat(expectedBook.size()).isEqualTo(2);


    }

    @Test
    void findByTitleTest() {
        //given
        Book book_1 = new Book(20L, "1111", "Atoms", "Einstein", "Advanced");
        Book book_2 = new Book(30L, "2222", "Molecules", "Dirac", "Beginner");
        Book book_3 = new Book(40L, "3333", "JAVA", "Einstein", "Medium");
        bookRepositoryUnderTest.save(book_1);
        bookRepositoryUnderTest.save(book_2);
        bookRepositoryUnderTest.save(book_3);
        //when
        Book expectedBook = bookRepositoryUnderTest.findByTitle("Molecules");
        //then
        assertThat(expectedBook.getTitle()).isEqualTo("Molecules");
        assertThat(expectedBook.getTitle()).isNotEqualTo("MoleculeS");
    }
}