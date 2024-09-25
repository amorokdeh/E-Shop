package Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Datenstrukturen.Artikel;
import Datenstrukturen.Kunde;
import Datenstrukturen.Rechnung;
import Datenstrukturen.Warenkorb;
import Exceptions.ArtikelExistiertNichtException;

public class WarenkorbManager {
	
    private LagerManager meinArtikel;

    public WarenkorbManager(LagerManager meinArtikel) {
        this.meinArtikel = meinArtikel;
    }

	
	 /**
     * Artikel aus Warenkorb entfernen.
     * @param warenkorb
     * @param artikel
     * @return true / false
     */
    public boolean artikelAusWarenkorb(Warenkorb warenkorb, Artikel artikel) {
        return warenkorb.deleteArtikel(artikel);
    }
    
    /**
     * Artikel in Warenkorb legen.
     * @param warenkorb
     * @param artikel
     * @param anzahl
     */
    public void artikelInWarenkorb(Warenkorb warenkorb, Artikel artikel, int anzahl) {
        warenkorb.addArtikel(artikel, anzahl);
    }
    
    /**
     * Bestand im Warenkorb aendern.
     * @param warenkorb
     * @param artikel
     * @param anzahl
     */
    public void bestandImWarenkorb(Warenkorb warenkorb, Artikel artikel, int anzahl) {
        warenkorb.aendernAnzahlArtikel(artikel, anzahl);
    }
    
    /**
     * Warenkorb kaufen
     * @param warenkorb
     * @param kunde
     * @throws IOException
     */
    public String warenkorbKaufen(Warenkorb warenkorb, Kunde kunde) throws IOException {
    	List<Artikel> wkList = warenkorb.getList();
    	if (wkList.isEmpty()) {
    		System.out.println("Es befinden sich keine Artikel im Warenkorb");
    		return "";
    	} else {
    		
            Rechnung r = new Rechnung(kunde, warenkorb);
            String rechnung = r.rechnungAusgeben();
            warenkorb.clearWarenkorb();
            System.out.println("Der Warenkorb wurde gekauft.");
            return rechnung;
    	}
    }
    
    /**
     * Loescht den Warenkorb
     * @param warenkorb
     */
    public void warenkorbLoeschen(Warenkorb warenkorb) {
        warenkorb.clearWarenkorb();
    }
    
    
    /**
     * Gibt eine Kopie des Warenkorb zurueck.
     * @param warenkorb
     * @return List<Artikel> warenkorbArtikelListe
     */
    
    public List<Artikel> zeigeWarenkorb(Warenkorb warenkorb) {
        List<Artikel> warenkorbArtikel = warenkorb.getList();
        List<Artikel> warenkorbArtikelListe = new ArrayList<>();

        for (Artikel a : warenkorbArtikel) {

            Artikel artikelcopy = new Artikel(a.getName(), a.getNummer(), a.getBestand(), a.getPreis() * a.getBestand());
            warenkorbArtikelListe.add(artikelcopy);
        }
        return warenkorbArtikelListe;
    }
   
    
}
