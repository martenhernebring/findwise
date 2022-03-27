package se.hernebring.search.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("document")
public class TfIdfSorter implements DocumentRepository {

  String[] documents;
  final List<HashMap<String, Double>> invertedIndexList = new ArrayList<>();

  @Override
  public void index(String[] args) {
    documents = args;
    HashMap<String, Integer> containTerm = new HashMap<>();
    for (String arg : args) {
      String[] words = arg.split(" ");
      HashMap<String, Integer> rawCount = new HashMap<>();
      for (String word : words) {
        if (rawCount.containsKey(word)) {
          int count = rawCount.get(word) + 1;
          rawCount.put(word, count);
        }
        else {
          rawCount.put(word, 1);
          if(containTerm.containsKey(word))
            containTerm.put(word, containTerm.get(word) + 1);
          else
            containTerm.put(word, 1);
        }
      }
      HashMap<String, Double> termFrequency = new HashMap<>();
      for(var entry : rawCount.entrySet()) {
        termFrequency.put(entry.getKey(), (double) entry.getValue() / words.length);
      }
      invertedIndexList.add(termFrequency);
    }
    double N = documents.length;
    for(HashMap<String, Double> ii : invertedIndexList)
      ii.replaceAll((k, v) -> v * Math.log10(N / containTerm.get(k)));
  }

  @Override
  public TreeMap<Double, String> search(String query) {
    TreeMap<Double, String> result = new TreeMap<>(Collections.reverseOrder());
    for(int i = 0; i < documents.length; i++) {
      HashMap<String, Double> current = invertedIndexList.get(i);
      if(current.containsKey(query))
        result.put(current.get(query), documents[i]);
    }
    System.out.println(result);
    return result;
  }
}
