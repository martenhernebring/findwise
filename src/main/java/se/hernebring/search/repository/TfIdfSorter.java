package se.hernebring.search.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

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
      HashMap<String, Integer> termFrequency = new HashMap<>();
      for (String word : words) {
        if (termFrequency.containsKey(word)) {
          int count = termFrequency.get(word) + 1;
          termFrequency.put(word, count);
        }
        else {
          termFrequency.put(word, 1);
          if(containTerm.containsKey(word))
            containTerm.put(word, containTerm.get(word) + 1);
          else
            containTerm.put(word, 1);
        }
      }
      HashMap<String, Double> augmentedFrequency = new HashMap<>();
      for(var entry : termFrequency.entrySet()) {
        augmentedFrequency.put(entry.getKey(), 0.5 + 0.5 * entry.getValue() / words.length);
      }
      System.out.println(augmentedFrequency.keySet());
      System.out.println(augmentedFrequency.values());
      invertedIndexList.add(augmentedFrequency);
    }
    double N = documents.length;
    for(HashMap<String, Double> ii : invertedIndexList) {
      for(var entry : ii.entrySet()) {
        ii.put(entry.getKey(), entry.getValue() * Math.log10(N / containTerm.get(entry.getKey())));
      }
      System.out.println(ii.keySet());
      System.out.println(ii.values());
    }
  }

  @Override
  public TreeMap<Double, String> search(String query) {
    TreeMap<Double, String> result = new TreeMap<>();
    for(int i = 0; i < documents.length; i++) {
      HashMap<String, Double> current = invertedIndexList.get(i);
      if(current.containsKey(query)) {
        result.put(current.get(query), documents[i]);
      }
    }
    return result;
  }
}
