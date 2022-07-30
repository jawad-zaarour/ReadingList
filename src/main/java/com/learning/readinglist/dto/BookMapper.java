package com.learning.readinglist.dto;

import com.learning.readinglist.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BookMapper {

    public BookDTO getBookDTO(Book book);

}
