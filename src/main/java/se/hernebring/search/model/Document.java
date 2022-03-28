package se.hernebring.search.model;

import javax.persistence.*;
import java.util.Map;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "document")
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(updatable = false)
  private String text;
  @ElementCollection(fetch = EAGER)
  private Map<String, Double> tfIdf;

  //for jpa
  public Document() {}

  public String getText() {
    return text;
  }

  public Map<String, Double> getTfIdf() {
    return tfIdf;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTfIdf(Map<String, Double> tfIdf) {
    this.tfIdf = tfIdf;
  }
}
