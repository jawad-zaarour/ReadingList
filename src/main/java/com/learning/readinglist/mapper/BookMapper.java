package com.learning.readinglist.mapper;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BookMapper {

    BookDTO getBookDTO(Book book);
    List<BookDTO> toBookDTOs(List<Book> books);

    Book getBook(BookDTO bookDTO);

}