package com.learning.readinglist.repo;

import com.learning.readinglist.dto.AuthorStatDTO;
import com.learning.readinglist.entity.QAuthor;
import com.learning.readinglist.entity.QBook;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepositoryCustom {


    @PersistenceContext
    EntityManager em;

    public static QBook qBook = QBook.book;
    public static QAuthor qAuthor = QAuthor.author;

    @Override
    public List<AuthorStatDTO> getStatByAuthorBook() {
        JPAQuery<AuthorStatDTO> jpaQuery = new JPAQuery<>(em);

        QBean<AuthorStatDTO> dslDTOQBean = Projections.bean(AuthorStatDTO.class,
                qBook.isbn,
                qBook.title,
                qAuthor.name
        );

        return jpaQuery
                .select(dslDTOQBean)
                .from(qBook)
                .innerJoin(qAuthor)
                .orderBy(qAuthor.name.asc())
                .on(qAuthor.id.eq(qBook.author.id))
                .fetch();


    }
}
