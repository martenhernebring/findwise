package se.hernebring.search.repository;

import java.util.TreeMap;

public interface DocumentRepository {
  void index(String[] args);
  TreeMap<Double, String> search(String query);
}
