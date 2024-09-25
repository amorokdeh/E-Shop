package Datenstrukturen;

//import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Klasse zur Repraesentierung des Warenkorbs.
 */

public class Warenkorb {
	
	// Artikel im Warenkorp als List 
	
	   private List<Artikel> artikelListe = new Vector<Artikel>();

	   // HashMap< Integer, Integer> list = new HashMap<Integer, Integer>();
	    
	    /**
	     * Fuegt Artikel mit Anzahl im Warenkorb hinzu.
	     * Der Preis fuer mehrere Artikel wird errechnet.
	     * @param artikel
	     * @param anzahl
	     */
	    public void addArtikel(Artikel artikel, int anzahl) {
	        Artikel artikelWk = new Artikel (artikel.getName(), artikel.getNummer(), anzahl, artikel.getPreis());
	        artikelWk.setPreis(artikel.getPreis());
	        artikelListe.add(artikelWk);
	    }


	    /**
	     * Gibt Anzahl des Artikels im Warenkorb wieder.
	     * @param artikel
	     * @return int anzahl
	     */
	    public int countArtikel(Artikel artikel) {
	        for (Artikel aktArtikel : artikelListe) {
	            if (aktArtikel.equals(artikel)) {
	                return aktArtikel.getBestand();
	            }
	        }
	        return 0;
	    }
	    
	    /**
	     * Aendern Anzahl des Artikels im Warenkorb wieder.
	     * @param artikel
	     * @return int anzahl
	     */
	    public int aendernAnzahlArtikel(Artikel artikel, int anzahl) {
	        for (Artikel aktArtikel : artikelListe) {
	            if (aktArtikel.equals(artikel)) {
	            	aktArtikel.setBestand(anzahl);
	            }
	        }
	        return anzahl;
	    }
	    
	    /**
	     * Loescht Artikel  aus dem Warenkorb.
	     * @param artikel
	     * @return true / false
	     */
	    public boolean deleteArtikel(Artikel artikel) {
	        for ( Artikel art : artikelListe) {
	        	if (art.equals(artikel)) {
	        		artikelListe.remove(artikel);
	            return true;
	        	}
	        }
	        return false;
	    }
	    
	    

    /**
     * Gibt Menge eines Artikels wieder.
     * @param nummer
     * @return menge
     */
    	public int getMenge(Artikel artikel) {
            for(Artikel a: artikelListe) {
                if (a.equals(artikel)) {
                    return a.getBestand();
                }
            } return 0;
        }

    /**
     * Put Artikel in List.
     * @param nummer
     * @param menge
     */
    	public void setArtikel(Artikel artikel, int menge) {
            for(Artikel a: artikelListe) {
                if ( (a.equals(artikel)) && (menge >= 0) ) {
                    artikel.setBestand(menge);
                }
            }
        }
    
	    /**
	     * Gibt die List des Warenkorbs wieder
	     * @return List<Artikel> 
	     */
	    public List<Artikel> getList() { return artikelListe; }

	    /**
	     * Entfernt alle Objekte aus dem Warenkorb.
	     */
	    public void clearWarenkorb() {
	        artikelListe.clear();
	       // list.clear();
	    }
	    
	    

}
