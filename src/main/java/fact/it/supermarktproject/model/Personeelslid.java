package fact.it.supermarktproject.model;
//Stef Van Gool
//r0740461

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personeelslid extends Persoon{
    private LocalDate inDienstSinds;
    private double wedde;

    public Personeelslid(String voornaam, String familienaam) {
        super(voornaam, familienaam);
        this.inDienstSinds = LocalDate.now();
        this.wedde = 1;
    }

    public Personeelslid(String voornaam, String familienaam, double wedde) {
        super(voornaam, familienaam);
        this.inDienstSinds = LocalDate.now();
        this.wedde = wedde;
    }

    public LocalDate getInDienstSinds() {
        return inDienstSinds;
    }

    public void setInDienstSinds(LocalDate inDienstSinds) {
        this.inDienstSinds = inDienstSinds;
    }

    public double getWedde() {
        return wedde;
    }

    public void setWedde(double wedde) {
        this.wedde = wedde;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String datum = getInDienstSinds().format(formatter);
        return "Personeelslid " + super.toString() + " is in dienst sinds " + datum;
    }
}
