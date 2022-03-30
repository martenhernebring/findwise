package se.hernebring.search.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "term")
public class Term {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(updatable = false, unique = true)
  private String word;
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "documentIdRef", referencedColumnName = "id")
  @ElementCollection(fetch = EAGER)
  private Map<Double, Long> documentTfIdf;

  //JPA requirement
  public Term() {}

  public Term(String word) {
    this.word = word;
    documentTfIdf = new HashMap<>();
  }

  public void addDocumentTfIdf(Long documentId, Double tfIdf) {
    while(this.documentTfIdf.containsKey(tfIdf))
      tfIdf -= 1.0E-18;
    this.documentTfIdf.put(tfIdf, documentId);
  }

  public Map<Double, Long> getDocumentTfIdf() {
    return documentTfIdf;
  }
}
