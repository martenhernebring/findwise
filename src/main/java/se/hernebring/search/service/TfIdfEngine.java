package se.hernebring.search.service;

import org.springframework.stereotype.Service;
import se.hernebring.search.exception.IllegalQueryException;
import se.hernebring.search.model.Term;
import se.hernebring.search.repository.DocumentRepository;
import se.hernebring.search.repository.TermRepository;

import java.util.*;

@Service
public class TfIdfEngine {

  private final TermRepository termRepository;
  private final DocumentRepository documentRepository;

  public TfIdfEngine(TermRepository termRepo, DocumentRepository docRepo) {
    this.termRepository = termRepo;
    this.documentRepository = docRepo;
  }

  public List<String> search(String query) {
    Term term = termRepository.findByWord(query);
    if(term == null)
      throw new IllegalQueryException("Query did not exist! Try again.");
    List<String> result = new ArrayList<>();
    TreeMap<Double, Long> sortedTfIdfDocument =
      new TreeMap<>(Collections.reverseOrder());
    sortedTfIdfDocument.putAll(term.getDocumentTfIdf());
    for(var entry : sortedTfIdfDocument.entrySet()) {
      result.add(getTextFromId(entry));
    }
    return result;
  }

  private String getTextFromId(Map.Entry<Double, Long> entry) {
    //Query is legal so optional always exists
    return documentRepository.findById(entry.getValue()).get().getText();
  }

}
