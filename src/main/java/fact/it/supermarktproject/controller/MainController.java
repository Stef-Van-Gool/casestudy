package fact.it.supermarktproject.controller;
//Stef Van Gool
//r0740461

import fact.it.supermarktproject.model.Afdeling;
import fact.it.supermarktproject.model.Klant;
import fact.it.supermarktproject.model.Personeelslid;
import fact.it.supermarktproject.model.Supermarkt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class MainController {
    private ArrayList<Personeelslid> personeelsleden;
    private ArrayList<Klant> klanten;
    private ArrayList<Supermarkt> supermarkten;

    @PostConstruct
    public void opvullen() {
        vulPersoneelsledenLijst();
        vulKlantenLijst();
        vulSupermarktenLijst();
    }

    /* Codeer hieronder al je verschillende mappings */
    @RequestMapping("/0_Wedde")
    public String naarWedde(Model model) {
        model.addAttribute("personeellijst", personeelsleden);
        return "0_Wedde";
    }

    @RequestMapping("/1_NieuweKlant")
    public String naarRegistratie(Model model) {
        model.addAttribute("supermarktenlijst", supermarkten);
        return "1_NieuweKlant";
    }

    @RequestMapping("/2_Formulier")
    public String maakNieuweKlant(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String familienaam = request.getParameter("familienaam");
        int geboortejaar = Integer.parseInt(request.getParameter("geboortejaar"));
        int supermarktNaamIndex = Integer.parseInt(request.getParameter("supermarktKeuze"));
        Klant klant = new Klant(voornaam, familienaam);
        Supermarkt supermarkt = supermarkten.get(supermarktNaamIndex);
        klant.setGeboortejaar(geboortejaar);
        supermarkt.registreerKlant(klant);
        klanten.add(klant);
        model.addAttribute("klant", klant);
        return "2_Formulier";
    }

    @RequestMapping("/3_NieuwPersoneelslid")
    public String naarPersoneelslid() {
        return "3_NieuwPersoneelslid";
    }

    @RequestMapping("/4_Formulier")
    public String maakPersoneelslid(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String familienaam = request.getParameter("familienaam");
        LocalDate inDienstSinds = LocalDate.parse(request.getParameter("inDienstSinds"));
        double wedde = Double.parseDouble(request.getParameter("wedde"));
        Personeelslid personeelslid = new Personeelslid(voornaam, familienaam, wedde);
        personeelslid.setInDienstSinds(inDienstSinds);
        personeelsleden.add(personeelslid);
        model.addAttribute("personeelslid", personeelslid);
        return "4_Formulier";
    }

    @RequestMapping("/5_Personeel")
    public String naarPersoneel(Model model) {
        model.addAttribute("personeellijst", personeelsleden);
        return "5_Personeel";
    }

    @RequestMapping("/6_Klanten")
    public String naarKlanten(Model model) {
        model.addAttribute("klantenlijst", klanten);
        return "6_Klanten";
    }

    @RequestMapping("/7_NieuweSupermarkt")
    public String maakSupermarkt() {
        return "7_NieuweSupermarkt";
    }

    @RequestMapping("/8_Supermarkten")
    public String naarSupermarkten(Model model, HttpServletRequest request) {
        String supermarktNaam = request.getParameter("supermarktNaam");
        int index = supermarkten.indexOf(supermarktNaam);
        if (supermarktNaam != null) {
            Supermarkt supermarkt = new Supermarkt(supermarktNaam);
            supermarkten.add(supermarkt);
        }
        model.addAttribute("supermarktenLijst", supermarkten);
        return "8_Supermarkten";
    }

    @RequestMapping("/9_NieuweAfdeling")
    public String maakAfdeling(Model model) {
        model.addAttribute("personeellijst", personeelsleden);
        model.addAttribute("supermarktenlijst", supermarkten);
        return "9_NieuweAfdeling";
    }

    @RequestMapping("/10_Afdelingen")
    public String naarAfdelingen(Model model, HttpServletRequest request) {
        String afdelingNaam = request.getParameter("naam");
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarktIndex"));
        Supermarkt supermarkt = supermarkten.get(supermarktIndex);
        if (afdelingNaam != null) {
            String foto = request.getParameter("foto");
            int verantwoordelijkeIndex = Integer.parseInt(request.getParameter("verantwoordelijkeIndex"));
            boolean gekoeld = Boolean.parseBoolean(request.getParameter("gekoeld"));
            if (verantwoordelijkeIndex < 0 && supermarktIndex < 0) {
                model.addAttribute("fout", "Geen verantwoordelijke en geen supermarkt gekozen");
                return "99_Error";
            } else if (verantwoordelijkeIndex < 0) {
                model.addAttribute("fout", "Geen verantwoordelijke gekozen");
                return "99_Error";
            } else if (supermarktIndex < 0) {
                model.addAttribute("fout", "Geen supermarkt gekozen");
                return "99_Error";
            }

            Afdeling afdeling = new Afdeling(afdelingNaam);
            Personeelslid personeelslid = personeelsleden.get(verantwoordelijkeIndex);
            afdeling.setVerantwoordelijke(personeelslid);
            afdeling.setGekoeld(gekoeld);
            afdeling.setFoto(foto);
            supermarkt.voegAfdelingToe(afdeling);
        }

        model.addAttribute("supermarkt", supermarkt);
        return "10_Afdelingen";
    }

    @RequestMapping("/11_ZoekAfdeling")
    public String zoekAfdeling(HttpServletRequest request, Model model) {
        boolean voorwaarde = false;
        String afdelingNaam = request.getParameter("afdeling");
        for (Supermarkt supermarkt : supermarkten) {
            Afdeling afdeling = supermarkt.zoekAfdelingOpNaam(afdelingNaam);
            if (afdeling != null) {
                model.addAttribute("afdeling", afdeling);
                voorwaarde = true;
            }
        }
        if (voorwaarde == false) {
            model.addAttribute("fout", "Geen afdeling met naam " + afdelingNaam + " gevonden!");
            return "99_Error";
        }
        return "11_ZoekAfdeling";
    }

    private ArrayList<Personeelslid> vulPersoneelsledenLijst() {
        personeelsleden = new ArrayList<>();
        Personeelslid jitse = new Personeelslid("Jitse", "Verhaegen", 1000);
        Personeelslid bert = new Personeelslid("Bert", "De Meulenaere", 200);
        Personeelslid sanne = new Personeelslid("Sanne", "Beckers", 30);
        personeelsleden.add(jitse);
        personeelsleden.add(bert);
        personeelsleden.add(sanne);
        return personeelsleden;
    }

    private ArrayList<Klant> vulKlantenLijst() {
        klanten = new ArrayList<>();
        Klant daan = new Klant("Daan", "Mertens");
        daan.setGeboortejaar(2001);
        Klant wim = new Klant("Wim", "Wijns");
        wim.setGeboortejaar(1956);
        Klant gert = new Klant("Gert", "Pauwels");
        gert.setGeboortejaar(1978);
        Klant stef = new Klant("Stef", "Van Gool");
        stef.setGeboortejaar(2000);
        klanten.add(daan);
        klanten.add(wim);
        klanten.add(gert);
        klanten.add(stef);
        klanten.get(0).voegToeAanBoodschappenlijst("melk");
        klanten.get(0).voegToeAanBoodschappenlijst("kaas");
        klanten.get(1).voegToeAanBoodschappenlijst("eieren");
        klanten.get(1).voegToeAanBoodschappenlijst("water");
        klanten.get(1).voegToeAanBoodschappenlijst("bloemkool");
        klanten.get(1).voegToeAanBoodschappenlijst("sla");
        klanten.get(2).voegToeAanBoodschappenlijst("tomaten");
        klanten.get(3).voegToeAanBoodschappenlijst("chocolade");
        klanten.get(3).voegToeAanBoodschappenlijst("ice tea");
        return klanten;
    }

    private ArrayList<Supermarkt> vulSupermarktenLijst() {
        supermarkten = new ArrayList<>();
        Supermarkt supermarkt1 = new Supermarkt("Colruyt Geel");
        Supermarkt supermarkt2 = new Supermarkt("Okay Meerhout");
        Supermarkt supermarkt3 = new Supermarkt("Colruyt Herentals");
        Afdeling afdeling1 = new Afdeling("Brood");
        Afdeling afdeling2 = new Afdeling("Groenten");
        afdeling2.setGekoeld(true);
        Afdeling afdeling3 = new Afdeling("Fruit");
        afdeling3.setGekoeld(true);
        Afdeling afdeling4 = new Afdeling("Vlees");
        afdeling4.setGekoeld(true);
        Afdeling afdeling5 = new Afdeling("Dranken");
        Afdeling afdeling6 = new Afdeling("Diepvries");
        afdeling1.setFoto("/img/brood.jpg");
        afdeling2.setFoto("/img/groenten.jpg");
        afdeling3.setFoto("/img/fruit.jpg");
        afdeling1.setVerantwoordelijke(personeelsleden.get(0));
        afdeling2.setVerantwoordelijke(personeelsleden.get(1));
        afdeling3.setVerantwoordelijke(personeelsleden.get(2));
        afdeling4.setVerantwoordelijke(personeelsleden.get(0));
        afdeling5.setVerantwoordelijke(personeelsleden.get(1));
        afdeling6.setVerantwoordelijke(personeelsleden.get(2));

        supermarkt1.voegAfdelingToe(afdeling1);
        supermarkt1.voegAfdelingToe(afdeling2);
        supermarkt2.voegAfdelingToe(afdeling3);
        supermarkt2.voegAfdelingToe(afdeling4);
        supermarkt3.voegAfdelingToe(afdeling5);
        supermarkt3.voegAfdelingToe(afdeling6);
        supermarkten.add(supermarkt1);
        supermarkten.add(supermarkt2);
        supermarkten.add(supermarkt3);
        return supermarkten;
    }
}
