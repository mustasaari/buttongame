package fi.tampere.mustasaari.mikko.buttongame;

import javax.persistence.*;

@Entity
public class NumberModel {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int theCurrentNumber;

    public NumberModel() {
        
    }
  
    public NumberModel(int number) {
        this.theCurrentNumber = number;
    }

    public int getNumber() {
        return this.theCurrentNumber;
    }

    public void setNumber(int number) {
        this.theCurrentNumber = number;
    }

    public void increaseNumber() {
        this.theCurrentNumber++;
    }

    public Long getId() {
        return id;
    }
}