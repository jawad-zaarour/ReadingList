package com.learning.readinglist.repo;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.QBook;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BookRepositoryImpl implements BookRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    public static QBook qBook = QBook.book;

    @Override
    public List<Book> getAllBooksByQuerDsl(String author) {
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);

        QBean<Book> bookQBean = Projections.bean(Book.class,
                qBook.title,
                qBook.isbn
        );

        List<Book> books =  jpaQuery
                .from(qBook)
                .where(qBook.author.eq(author))
                .select(bookQBean)
                .fetch();
        return books;
    }
}
