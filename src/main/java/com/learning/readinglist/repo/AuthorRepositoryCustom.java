package com.learning.readinglist.repo;

import com.learning.readinglist.dto.AuthorStatDTO;

import java.util.List;

public interface AuthorRepositoryCustom {

    List<AuthorStatDTO> getStatByAuthorBook();
}
