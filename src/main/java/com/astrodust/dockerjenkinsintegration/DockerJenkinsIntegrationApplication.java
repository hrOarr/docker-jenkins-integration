package com.astrodust.dockerjenkinsintegration;

import com.astrodust.dockerjenkinsintegration.entity.Book;
import com.astrodust.dockerjenkinsintegration.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/book")
public class DockerJenkinsIntegrationApplication {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Book saveBook(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(DockerJenkinsIntegrationApplication.class, args);
    }

}
