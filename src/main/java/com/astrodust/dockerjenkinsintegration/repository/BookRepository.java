package com.astrodust.dockerjenkinsintegration.repository;

import com.astrodust.dockerjenkinsintegration.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, Integer> {
}
