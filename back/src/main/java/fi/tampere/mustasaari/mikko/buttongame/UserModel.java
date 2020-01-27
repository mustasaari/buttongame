package fi.tampere.mustasaari.mikko.buttongame;

import javax.persistence.*;

@Entity
public class UserModel {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String name;
    private int credits;
    private int clicksToPrize;
    private String message;
 
    public UserModel() {
    }
 
    public UserModel(String name, int credits) {
        this.name = name;
        this.credits = credits;
        this.message = "";
        this.clicksToPrize = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addCredits(int plusCredits) {
        this.credits += plusCredits;
    }

    public Long getId() {
        return id;
    }

    public void setClicksToPrize(int clicks) {
        this.clicksToPrize = clicks;
    }

    public int getClicksToPrize() {
        return this.clicksToPrize;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}