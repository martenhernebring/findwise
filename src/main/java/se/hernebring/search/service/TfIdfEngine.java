package se.hernebring.search.service;

import org.springframework.stereotype.Service;
import se.hernebring.search.model.Document;
import se.hernebring.search.repository.DocumentRepository;

import java.util.*;

@Service
public class TfIdfEngine {

  private final DocumentRepository repository;

  public TfIdfEngine(DocumentRepository repository) {
    this.repository = repository;
  }

  public TreeMap<Double, String> search(String query) {
    TreeMap<Double, String> result = new TreeMap<>(Collections.reverseOrder());
    List<Document> documents = repository.findAll();
    for(Document doc : documents)
      addExistingAsUnique(query, doc, result);
    return result;
  }

  private void addExistingAsUnique(String query, Document doc, TreeMap<Double, String> result) {
    Map<String, Double> current = doc.getTfIdf();
    if(current.containsKey(query)) {
      double existing = current.get(query);
      while(result.containsKey(existing))
        existing -= 1.0E-18;
      result.put(existing, doc.getText());
    }
  }
}
