package se.hernebring.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.hernebring.search.model.Document;

@Repository("document")
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
