package Domain;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Datenstrukturen.Artikel;
import Datenstrukturen.Ereignis;
import Datenstrukturen.Person;
import Exceptions.ArtikelExistiertBereitsException;
import Persistence.FilePersistenceManager;
import Persistence.PersistenceManager;

public class EreignisManager {
	
	private List<Ereignis> ereignisBestand = new Vector<Ereignis>();
    private PersistenceManager pm = new FilePersistenceManager();
    
    public void liesDaten(String datei) throws IOException {
        pm.openForReading(datei);
        Ereignis einEreignis;
        do {
            // Ereignis-Objekt einlesen
            einEreignis = pm.ladeEreignis();
            if (einEreignis != null) {
                try {
                    erstelle(einEreignis);
                } catch (ArtikelExistiertBereitsException e) {
                    
                    e.printStackTrace();
                }
            }
        } while (einEreignis != null);

        pm.close();
    }
    
    public void erstelle(Ereignis einEreignis) throws ArtikelExistiertBereitsException, IOException {
        ereignisBestand.add(einEreignis);
    }
    
    public void schreibeEreignisse(String datei) throws IOException  {
        pm.openForWriting(datei);

        if (!ereignisBestand.isEmpty()) {
            Iterator<Ereignis> iter = ereignisBestand.iterator();
            while (iter.hasNext()) {
                Ereignis e = iter.next();
                pm.speichereEreignis(e);
            }
        }
        pm.close();
    }
    
    public void erstelleEreignis(Person p, Artikel artikel, int bestandsAenderung, String typ ) {
        String benutzerName = p.getBenutzerName();
        int benutzerNr = p.getNummer();
        int artikelNr = artikel.getNummer();
        String datum =  new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(new Date());
        Ereignis ereignis = new Ereignis(typ, artikelNr, bestandsAenderung, benutzerNr, benutzerName, datum);
        ereignisBestand.add(ereignis);
        System.out.println("Ereignis wurde erstellt.");
    }
    
    public void erstelleEreignis(Person p, String typ ) {
        String benutzerName = p.getBenutzerName();
        int benutzerNr = p.getNummer();
        String datum =  new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(new Date());
        Ereignis ereignis = new Ereignis(typ, 0, 0, benutzerNr, benutzerName, datum);
        ereignisBestand.add(ereignis);
        System.out.println("Ereignis wurde erstellt.");
    }

    public List<Ereignis> gibEreignisListe(){
        return new Vector <Ereignis> (ereignisBestand);
    }
}