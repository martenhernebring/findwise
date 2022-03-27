package se.hernebring.search.service;

import org.springframework.stereotype.Service;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.repository.DocumentRepository;
import se.hernebring.search.repository.QueryRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

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

  public List<String> searchForQuery() {
    String query = queryRepository.prompt("Type word and hit enter to search (empty to quit)");
    if(query.isEmpty())
      throw new ShutdownRequestedException("User request quit by empty search");
    return (List<String>) documentRepository.search(query).values();
  }
}
