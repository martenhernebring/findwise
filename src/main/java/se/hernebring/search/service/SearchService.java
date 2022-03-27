package se.hernebring.search.service;

import org.springframework.stereotype.Service;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.repository.DocumentRepository;
import se.hernebring.search.repository.QueryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

  final DocumentRepository documentRepository;
  final QueryRepository queryRepository;

  public SearchService(DocumentRepository documentRepository, QueryRepository queryRepository) {
    this.documentRepository = documentRepository;
    this.queryRepository = queryRepository;
  }

  public void index(String[] args) {
    documentRepository.index(args);
  }

  public List<String> searchUntilShutdown() {
    String query = queryRepository.prompt("Type word and hit enter to search (empty to quit)");
    if(query.isEmpty())
      throw new ShutdownRequestedException("User request quit by empty search");
    return new ArrayList<>(documentRepository.search(query).values());
  }
}
