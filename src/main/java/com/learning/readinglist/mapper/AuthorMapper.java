package com.learning.readinglist.mapper;

import com.learning.readinglist.dto.AuthorDTO;
import com.learning.readinglist.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO getAuthorDTO(Author author);

    List<AuthorDTO> toAuthorDTOs(List<Author> authors);

    Author getAuthor(AuthorDTO authorDTO);
}
