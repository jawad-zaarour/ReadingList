package com.learning.readinglist;


import com.learning.readinglist.controller.BookController;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookController bookController;


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void shouldReturnBookById() throws Exception {
        Book book = new Book();
        book.setAuthor("Dirac");
        book.setDescription("for graduate and research students");
        book.setIsbn("1111");
        book.setTitle("Relativistic Quantum Mechanics");
        book.setId(20L);

        given(bookService.getBookById(anyLong())).willReturn(book);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/books/{id}", 20L)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1111"));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void ShouldSaveBook() throws Exception {
        Book book = new Book();
        book.setAuthor("Dirac");
        book.setDescription("for graduate and research students");
        book.setIsbn("1111");
        book.setTitle("Relativistic Quantum Mechanics");
        book.setId(20L);

        given(bookService.saveBook(book)).willReturn(book);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/books/")
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"isbn\": \"8888\", \"title\":\"DA7\", \"author\":\"jawad\", " +
                                        "\"description\":\"aaaaaa\"}"))
                .andExpect(status().isCreated());
        verify(bookService).saveBook(any(Book.class));
    }
}
