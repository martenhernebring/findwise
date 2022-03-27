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

  public SearchService(DocumentRepository docRep, QueryRepository queryRep) {
    this.documentRepository = docRep;
    this.queryRepository = queryRep;
  }

  public void index(String[] args) {
    documentRepository.index(args);
  }

  public List<String> searchOrShutdown() {
    String query = queryRepository
      .prompt("Type word and hit enter to search (empty to quit)");
    if(query.isEmpty())
      throw new ShutdownRequestedException("Empty search: Quit requested");
    return new ArrayList<>(documentRepository.search(query).values());
  }
}
