package com.learning.readinglist.service;

import com.learning.readinglist.ServiceException;
import com.learning.readinglist.dto.AuthorDTO;
import com.learning.readinglist.dto.AuthorStatDTO;
import com.learning.readinglist.entity.Author;
import com.learning.readinglist.mapper.AuthorMapper;
import com.learning.readinglist.repo.AuthorRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAuthors() {
        List<Author> lstAuthors = authorRepository.findAll();
        if (CollectionUtils.isNotEmpty(lstAuthors)) {
            return authorMapper.toAuthorDTOs(lstAuthors);
        } else return Collections.emptyList();
    }


    public AuthorDTO saveAuthor(Author author) {
        try {
            return authorMapper.getAuthorDTO(authorRepository.save(author));
        } catch (ServiceException serviceException) {
            throw new ServiceException("Error in converting btw DTO and Entity" + serviceException);
        }
    }

    public List<AuthorStatDTO> getStatByAuthorBook() {
        return authorRepository.getStatByAuthorBook();
    }
}
