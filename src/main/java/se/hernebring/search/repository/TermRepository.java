package se.hernebring.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.hernebring.search.model.Term;

@Repository("termRepository")
public interface TermRepository extends JpaRepository<Term, Long> {
  boolean existsByWord(String word);
  Term findByWord(String word);
}
