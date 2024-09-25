package Persistence;
import java.io.IOException;

import Datenstrukturen.Artikel;
import Datenstrukturen.Ereignis;
import Datenstrukturen.Person;

/**
 * @author Mohamad Alkasem und Amr Okdeh
 *
 * Schnittstelle für den Zugriff auf Dateien
 * zum Ablegen von Artikel, Ereignis und Person
 * 
 */

public interface PersistenceManager {
	
	public void openForReading(String datenquelle) throws IOException;
    public void openForWriting(String datenquelle) throws IOException;
    public boolean close();

    /**
	 * Methode zum Einlesen und schreiben der Artikel-Daten aus einer externen Datenquelle.
	 *
	 * 
	 * @param a Artikel-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
    public Artikel ladeArtikel() throws IOException;
    public boolean speichereArtikel(Artikel a) throws IOException;
    
    //Methode zum Einlesen und schreiben der Person-Daten aus einer externen Datenquelle.
    public Person ladePerson() throws IOException;
    public boolean speicherePerson(Person p) throws IOException;

    public Ereignis ladeEreignis() throws IOException;
    public boolean speichereEreignis(Ereignis e) throws IOException;
    
}
