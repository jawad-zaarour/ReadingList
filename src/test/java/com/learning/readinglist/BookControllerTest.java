package com.learning.readinglist;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.readinglist.controller.BookController;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.service.BookService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Contract test (unit test that test only the controller and mock the service): ensure what are the response format of the api
 */
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookController bookController;

    Book book = new Book();

    @BeforeEach
    public void setup() {
        book.setAuthor("Dirac");
        book.setDescription("for graduate and research students");
        book.setIsbn("1111");
        book.setTitle("Relativistic Quantum Mechanics");
        book.setId(20L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void shouldReturnBookById() throws Exception {
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
    public void testGetBookByIdEmptyResponse() throws Exception {
        given(bookService.getBookById(anyLong())).willReturn(null);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/books/{id}", 20L)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void ShouldSaveBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(book);

        //given(bookService.saveBook(book)).willReturn(book);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/books/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputJson))
                .andExpect(status().is2xxSuccessful());

        verify(bookService, times(1)).saveBook(any(Book.class));
    }
}
