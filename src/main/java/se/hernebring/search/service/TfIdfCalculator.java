package se.hernebring.search.service;

import org.springframework.stereotype.Service;
import se.hernebring.search.model.Document;
import se.hernebring.search.repository.DocumentRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class TfIdfCalculator {

  private final DocumentRepository repository;
  private final HashMap<String, Integer> termDocsCount = new HashMap<>();

  public TfIdfCalculator(DocumentRepository repository) {
    this.repository = repository;
  }

  public void index(String[] args) {
    calculateTermFrequency(args);
    weighInverseDocumentFrequency(args.length);
  }

  private void weighInverseDocumentFrequency(int length) {
    List<Document> documents = repository.findAll();
    for(Document d : documents) {
      d.getTfIdf().replaceAll((k, v) ->
        v * Math.log10((double) length / termDocsCount.get(k)));
    }
    repository.saveAll(documents);
  }

  private void calculateTermFrequency(String[] args) {
    for (String text : args)
      calculateTermFrequency(text);
  }

  private void calculateTermFrequency(String text) {
    String[] words = text.split(" ");
    HashMap<String, Integer> rawCount = new HashMap<>();
    for (String word : words)
      countOccurrences(word, rawCount);
    HashMap<String, Double> termFrequency = new HashMap<>();
    for(var es : rawCount.entrySet())
      termFrequency.put(es.getKey(), (double) es.getValue() / words.length);
    Document document = new Document();
    document.setTfIdf(termFrequency);
    document.setText(text);
    repository.save(document);
  }

  private void countOccurrences(String word, HashMap<String, Integer> rawCount) {
    if (rawCount.containsKey(word))
      rawCount.put(word, rawCount.get(word) + 1);
    else {
      rawCount.put(word, 1);
      if(termDocsCount.containsKey(word))
        termDocsCount.put(word, termDocsCount.get(word) + 1);
      else
        termDocsCount.put(word, 1);
    }
  }
}
