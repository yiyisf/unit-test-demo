package com.paic.unittest.demo.repository;

import com.paic.unittest.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoonReopository extends JpaRepository<Book, Long> {
}
