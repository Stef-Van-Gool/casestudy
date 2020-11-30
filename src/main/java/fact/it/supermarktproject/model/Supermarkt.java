package fact.it.supermarktproject.model;
//Stef Van Gool
//r0740461

import java.util.ArrayList;

public class Supermarkt {
    private ArrayList<Afdeling> afdelingen = new ArrayList<>();
    private String naam;
    private int aantalKlanten;
    private Klant klant;

    public Supermarkt(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ArrayList<Afdeling> getAfdelingen() {
        return afdelingen;
    }

    public int getAantalAfdelingen() {
        return afdelingen.size();
    }

    public void voegAfdelingToe(Afdeling afdeling) {
        afdelingen.add(afdeling);
    }

    public Afdeling zoekAfdelingOpNaam(String naam) {
        Afdeling output = null;
        for (Afdeling afdeling: afdelingen) {
            if (afdeling.getNaam().toLowerCase().equals(naam.toLowerCase())) {
                output = afdeling;
            }
        }
        return output;
    }

    public void registreerKlant(Klant klant) {
        aantalKlanten += 1;
        klant.setKlantenkaartnr(aantalKlanten);
    }
}
