package se.hernebring.search.repository;

import java.util.List;

public interface DocumentRepository {
  void index(String[] args);
  List<String> search(String query);
}
