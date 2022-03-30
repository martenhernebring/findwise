package se.hernebring.search.service;

import org.springframework.stereotype.Service;
import se.hernebring.search.model.Document;
import se.hernebring.search.model.Term;
import se.hernebring.search.repository.DocumentRepository;
import se.hernebring.search.repository.TermRepository;

import java.util.*;

@Service
public class TfIdfCalculator {

  private final DocumentRepository documentRepository;
  private final TermRepository termRepository;
  private final HashMap<String, Integer> termDocsCount = new HashMap<>();

  public TfIdfCalculator(DocumentRepository docRep, TermRepository termRep) {
    this.documentRepository = docRep;
    this.termRepository = termRep;
  }

  public void index(String[] args) {
    calculateTermFrequency(args);
    weighInverseDocumentFrequency(args.length);
    createInvertedIndex();
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
    for(var entry : rawCount.entrySet())
      termFrequency.put(entry.getKey(), (double) entry.getValue() / words.length);
    Document document = new Document();
    document.setTfIdf(termFrequency);
    document.setText(text);
    documentRepository.save(document);
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

  private void weighInverseDocumentFrequency(int length) {
    List<Document> documents = documentRepository.findAll();
    for(Document d : documents) {
      d.getTfIdf().replaceAll((k, v) ->
        v * Math.log10((double) length / termDocsCount.get(k)));
      documentRepository.save(d);
    }
  }

  private void createInvertedIndex() {
    for(Document document : documentRepository.findAll()) {
      Map<String, Double> tfIdf = document.getTfIdf();
      for(var entry : tfIdf.entrySet())
        saveTerm(document.getId(), entry);
    }
  }

  private void saveTerm(Long id, Map.Entry<String, Double> entry) {
    String word = entry.getKey();
    Term term;
    if(termRepository.existsByWord(word))
      term = termRepository.findByWord(word);
    else
      term = new Term(word);
    term.addDocumentTfIdf(id, entry.getValue());
    termRepository.save(term);
  }
}
