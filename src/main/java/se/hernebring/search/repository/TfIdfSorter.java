package se.hernebring.search.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository("document")
public class TfIdfSorter implements DocumentRepository {

  String[] documents;
  List<HashMap<String, Integer>> invertedIndexList = new ArrayList<>();

  @Override
  public void index(String[] args) {
    documents = args;
    for(int i = 0; i < args.length; i++) {
      String[] words = args[i].split(" ");
      HashMap<String, Integer> invertedIndex = new HashMap<>();
      for(String word: words) {
        if(invertedIndex.containsKey(word))
          invertedIndex.put(word, invertedIndex.get(word) + 1);
        else
          invertedIndex.put(word, 1);
      }
      invertedIndexList.add(invertedIndex);
    }
  }

  @Override
  public List<String> search(String query) {
    List<String> result = new ArrayList<>();
    for(int i = 0; i < documents.length; i++) {
      HashMap<String, Integer> current = invertedIndexList.get(i);
      if(current.containsKey(query))
        result.add(documents[i]);
    }
    return result;
  }
}
