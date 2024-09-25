package Domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Datenstrukturen.Artikel;
import Exceptions.ArtikelExistiertBereitsException;
import Exceptions.ArtikelExistiertNichtException;
import Exceptions.ArtikelNichtVerfuegbarException;
import Persistence.FilePersistenceManager;
import Persistence.PersistenceManager;

public class LagerManager {

    private List<Artikel> artikelBestand = new Vector<Artikel>();
    private PersistenceManager pm = new FilePersistenceManager();
    
    
    
    /**
     * Liest Daten aus Datei ueber PersistenzManager.
     * Fuegt Artikel im artikelBestand ein.
     * @param datei
     * @throws IOException
     */
    public void liesDaten(String datei) throws IOException {
        pm.openForReading(datei);
        Artikel einArtikel;
        do {
            einArtikel = pm.ladeArtikel();
            if (einArtikel != null) {
                try {
                    einfuegen(einArtikel);
                } catch (ArtikelExistiertBereitsException e1) {}
            }
        } while (einArtikel != null);
        pm.close();
    }
    

    /**
     * Artikel in artikelBestand einfuegen.
     * @param einArtikel
     * @throws ArtikelExistiertBereitsException
     */
    public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
        if (artikelBestand.contains(einArtikel)) {
            throw new ArtikelExistiertBereitsException(einArtikel, "");
        }

        artikelBestand.add(einArtikel);
    }
    
    
    /**
     * Schreibt Daten in Datei ueber PersistenzManager.
     * @param datei
     * @throws IOException
     */
    public void schreibeDaten(String datei) throws IOException {
        pm.openForWriting(datei);

        if (!artikelBestand.isEmpty()) {
            Iterator iter = artikelBestand.iterator();
            while (iter.hasNext()) {
                Artikel a = (Artikel) iter.next();
                pm.speichereArtikel(a);
            }
        }
        pm.close();
    }
    
    /**
     * Gibt eine Liste mit Artikeln zurueck, die des ArtieklName entsprechen.
     * @param bezeichnung
     * @return List<Artikel>
     */
    public List<Artikel> sucheArtikel(String artikelName) {
        List<Artikel> suchErg = new Vector();
        Iterator<Artikel> it = artikelBestand.iterator();

        while (it.hasNext()) {
            Artikel artikel = it.next();
            if (artikel.getName().equals(artikelName)) {
                suchErg.add(artikel);
            }
        }
        return suchErg;
    }
    

    /**
     * Gibt Artikel anhand Artikelnummer zurueck.
     * @param nummer
     * @return artikel
     */
    public Artikel sucheArtikelNummer(int nummer) {
        for (Artikel artikel : artikelBestand) {
            if (artikel.getNummer() == nummer) {
                return artikel;
            }
        }
        return null;
    }

    /**
     * Veraendert Menge eines Artikels im artikelBestand.
     * @param artikel
     * @param menge
     * @throws ArtikelNichtVerfuegbarException
     */
    public void veraendereBestand(Artikel artikel, int bestandsAenderung) throws ArtikelExistiertNichtException {
        if (!artikelBestand.contains(artikel)) {
            throw new ArtikelExistiertNichtException(artikel);
        }
        artikel.setBestand(bestandsAenderung);
    }
    
    
    /**
     * Verringert Menge eines Artikels im artikelBestand
     * @param artikel
     * @param bestandsAenderung
     * @throws ArtikelNichtVerfuegbarException
     */
    public void verringereBestand(Artikel artikel, int bestandsAenderung) throws ArtikelNichtVerfuegbarException {
        if (!artikelBestand.contains(artikel)) {
            throw new ArtikelNichtVerfuegbarException(artikel, "");
        }
        artikel.setBestand(artikel.getBestand() - bestandsAenderung);
    }
    
    
    
    /**
     * Erhoeht Menge eines Artikels im artikelBestand.
     * @param artikel
     * @param bestandsAenderung
     * @throws ArtikelNichtVerfuegbarException
     */
    public void bestandErhoehen(Artikel artikel, int bestandsAenderung) throws ArtikelNichtVerfuegbarException {
        if (!artikelBestand.contains(artikel)) {
            throw new ArtikelNichtVerfuegbarException(artikel, "");
        }
        artikel.setBestand(artikel.getBestand() + bestandsAenderung);
    }
    
    
    
    /**
     * Artikel aus artikelBestand entfernen.
     * @param einArtikel
     */
    public void loeschen(Artikel einArtikel) {
        artikelBestand.remove(einArtikel);
    }

    /**
     * Gibt eine neue Liste des artikelBestand zurueck.
     * @return Vector(artikelBestand)
     */
    public List<Artikel> getArtikelBestand() {
        return new Vector<Artikel>(artikelBestand);
    }

}
