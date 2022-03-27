package se.hernebring.search.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("document")
public class TfIdfSorter implements DocumentRepository {

  String[] documents;
  final List<HashMap<String, Double>> invertedIndexList = new ArrayList<>();

  @Override
  public TreeMap<Double, String> search(String query) {
    TreeMap<Double, String> result = new TreeMap<>(Collections.reverseOrder());
    for(int i = 0; i < documents.length; i++) {
      HashMap<String, Double> current = invertedIndexList.get(i);
      if(current.containsKey(query)) {
        if(result.containsKey(current.get(query))) {
          result.put(current.get(query) - 0.000000000000000001, documents[i]);
        } else {
          result.put(current.get(query), documents[i]);
        }
      }
    }
    return result;
  }

  @Override
  public void index(String[] args) {
    documents = args;
    HashMap<String, Integer> termDocsCount = calculateAllTermFrequencies();
    weighInverseDocumentFrequency(termDocsCount);
  }

  private void weighInverseDocumentFrequency(HashMap<String, Integer> docsNo) {
    double N = documents.length;
    for(HashMap<String, Double> ii : invertedIndexList)
      ii.replaceAll((k, v) -> v * Math.log10(N / docsNo.get(k)));
  }

  private HashMap<String, Integer> calculateAllTermFrequencies() {
    HashMap<String, Integer> termDocsCount = new HashMap<>();
    for (String document : documents)
      calculateTermFrequency(document, termDocsCount);
    return termDocsCount;
  }

  private void calculateTermFrequency(String document,
                                      HashMap<String, Integer> docsWith) {
    String[] words = document.split(" ");
    HashMap<String, Integer> rawCount = new HashMap<>();
    for (String word : words)
      countOccurrences(word, docsWith, rawCount);
    HashMap<String, Double> termFrequency = new HashMap<>();
    for(var es : rawCount.entrySet())
      termFrequency.put(es.getKey(), (double) es.getValue() / words.length);
    invertedIndexList.add(termFrequency);
  }

  private void countOccurrences(String word, HashMap<String, Integer> docsWith,
                                HashMap<String, Integer> rawCount) {
    if (rawCount.containsKey(word))
      rawCount.put(word, rawCount.get(word) + 1);
    else {
      rawCount.put(word, 1);
      if(docsWith.containsKey(word))
        docsWith.put(word, docsWith.get(word) + 1);
      else
        docsWith.put(word, 1);
    }
  }
}
