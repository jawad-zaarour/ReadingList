package com.learning.readinglist;

import com.learning.readinglist.entity.Author;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.AuthorRepository;
import com.learning.readinglist.repo.BookRepository;
import com.learning.readinglist.util.EnBookType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    //private BookDTO bookDTO = new BookDTO();
    private Book book = new Book();
    private Author author = new Author();

    @BeforeEach
    void setUp() {

        author.setId(1L);
        author.setName("Einstein");

        book.setDescription("Advanced");
        book.setId(1L);
        book.setIsbn("1111");
        book.setTitle("Atoms");
        book.setType(EnBookType.Science);
        book.setAuthor(author);

    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void findByAuthorTest() {

        //Book book_1 = new Book(20L, null,"1111", "Atoms", author,
        //     EnBookType.Science,"Advanced");
        authorRepository.save(author);
        bookRepository.save(book);
        //when
        List<Book> expectedBook = bookRepository.getBooksByAuthorName("Einstein");
        //then
        assertThat(expectedBook.size()).isEqualTo(1);
        assertThat(expectedBook.get(0).getDescription()).isEqualTo("Advanced");
        assertThat(expectedBook.get(0).getType()).isEqualTo(EnBookType.Science);


    }

    @Test
    void findByTitleTest() {

        authorRepository.save(author);
        bookRepository.save(book);
        //when
        Optional<Book> expectedBook = bookRepository.findByTitle("Atoms");
        //then
        assertThat(expectedBook.get().getTitle()).isEqualTo("Atoms");
        assertThat(expectedBook.get().getTitle()).isNotEqualTo("MoleculeS");
    }
}