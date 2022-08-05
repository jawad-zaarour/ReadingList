package com.learning.readinglist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.readinglist.controller.BookController;
import com.learning.readinglist.dto.BookDTO;
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

import static org.mockito.ArgumentMatchers.*;
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

    BookDTO bookDTO;

    @BeforeEach
    public void setup() {
        bookDTO = new BookDTO(1L, "1111",
                "Atoms", "Einstein", "Advanced");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void shouldReturnBookById() throws Exception {
        given(bookService.getBookById(anyLong())).willReturn(bookDTO);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/books/{id}", 20L)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1111"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void shouldReturnBookByTitle() throws Exception {
        given(bookService.getBookByTitle(anyString())).willReturn(bookDTO);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/books/book-by-title/Atoms")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Atoms"));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void ShouldSaveBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(bookDTO);

        given(bookService.saveBook(any(Book.class))).willReturn(bookDTO);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/books/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputJson))
                .andExpect(status().is2xxSuccessful());

        verify(bookService, times(1)).saveBook(any(Book.class));
    }
}
