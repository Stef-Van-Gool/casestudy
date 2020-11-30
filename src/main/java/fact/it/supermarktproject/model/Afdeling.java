package fact.it.supermarktproject.model;
//Stef Van Gool
//r0740461

public class Afdeling {
    private String naam;
    private String foto;
    private boolean gekoeld;
    private Personeelslid personeelslid;

    public Afdeling() {
    }

    public Afdeling(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Personeelslid getVerantwoordelijke() {
        return personeelslid;
    }

    public void setVerantwoordelijke(Personeelslid verantwoordelijke) {
        this.personeelslid = verantwoordelijke;
    }

    public boolean isGekoeld() {
        return gekoeld;
    }

    public void setGekoeld(boolean gekoeld) {
        this.gekoeld = gekoeld;
    }
}
