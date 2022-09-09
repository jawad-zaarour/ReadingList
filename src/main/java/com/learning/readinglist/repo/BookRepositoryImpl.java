package com.learning.readinglist.repo;

import com.learning.readinglist.entity.Author;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.QBook;
import com.learning.readinglist.util.EnBookType;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    public static QBook qBook = QBook.book;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public List<Book> getBooksByType(EnBookType type) {
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);

        return jpaQuery
                .from(qBook)
                .where(qBook.type.eq(type))
                .fetch();
    }

    @Override
    public Map<EnBookType, Long> getBooksStat() {
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);

        return jpaQuery
                .select(qBook.type)
                .from(qBook)
                .groupBy(qBook.type)
                .transform(GroupBy.groupBy(qBook.type).as(qBook.count()));
    }

    @Override
    public List<Book> findBooksByAuthorName(String authorName) {
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);
        Optional<Author> author = authorRepository.findByName(authorName);
        if(author.isPresent()){
            return jpaQuery
                    .from(qBook)
                    .where(qBook.author.id.eq(author.get().getId()))
                    .fetch();
        }
        return null;
    }


}
