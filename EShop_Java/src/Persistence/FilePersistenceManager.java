package Persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Datenstrukturen.Artikel;
import Datenstrukturen.Ereignis;
import Datenstrukturen.Kunde;
import Datenstrukturen.Mitarbeiter;
import Datenstrukturen.Person;

public class FilePersistenceManager implements PersistenceManager {
	
	BufferedReader reader = null;
    PrintWriter writer = null;

	//Realisierung einer "Mockup"-Schnittstelle zur persistenten Speicherung von Daten in Dateien.
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
		
	}

	//öffnet Datei zum Schreiben.
	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
		
	}

	
	public boolean close() {
		
		if (writer != null) {
            writer.close();
		}
		
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

	// Lies einen Artikel 
	public Artikel ladeArtikel() throws IOException {
		String artikeName = liesZeile();
        if (artikeName == null) { return null; }

        String artikelNummer = liesZeile();
        int nummer = Integer.parseInt(artikelNummer);

        String artikelBestand = liesZeile();
        int bestand = Integer.parseInt(artikelBestand);

        String artikelPreis = liesZeile();
        float preis = Float.parseFloat(artikelPreis);
        
        return new Artikel(artikeName, nummer, bestand, preis);
	}
	
	// Speicher einen Artikel 
	public boolean speichereArtikel(Artikel a) throws IOException {
		schreibeZeile(a.getName());
        schreibeZeile(a.getNummer() + "");
        schreibeZeile(a.getBestand() + "");
        schreibeZeile(Float.toString(a.getPreis()));
        return true;
	}

	//Person laden aus .txt Datei.
	public Person ladePerson() throws IOException {
		String typ = liesZeile();
        if (typ == null) { return null; }
        
		String vorname = liesZeile();
        if (vorname == null) { return null; }
        
        String nachname = liesZeile();
        if (nachname == null) { return null; }
        
        String nummer = liesZeile();
        if (nummer == null) { return null; }
        int num = Integer.parseInt(nummer);
        
        String benutzerName = liesZeile();
        if (benutzerName == null) { return null; }
        
        String password = liesZeile();
        if (password == null) { return null; }
        
        // Trennung zwischen Kunden und Mitarbeitern
        if (typ.equals("k")) {
        	String strasse = liesZeile();
            if (strasse == null) { return null; }
            
            String hausNrummer = liesZeile();
            if (hausNrummer == null) { return null; }
            
            String pLZ = liesZeile();
            if (pLZ == null) { return null; }
            int plz = Integer.parseInt(pLZ);
            
            String ort = liesZeile();
            if (ort == null) { return null; }
              
            Kunde k = new Kunde(vorname, nachname, num, benutzerName, password, strasse, hausNrummer,
            		plz, ort);
            return k;
            
        } else if (typ.equals("m")) {
            Mitarbeiter m = new Mitarbeiter(vorname, nachname, num, benutzerName, password);
            return m;
        }
         
        return null;
	}


	public boolean speicherePerson(Person p) throws IOException {
		
	    if (p instanceof Kunde) {
	    	schreibeZeile("k");
	    	schreibeZeile(p.getVorname());
		    schreibeZeile(p.getNachname());
		    schreibeZeile(p.getNummer() + ""); // "" alternative kann -> Interger.toString 
		    schreibeZeile(p.getBenutzerName());
		    schreibeZeile(p.getPassword());
			schreibeZeile(((Kunde) p).getStrasse());
			schreibeZeile(((Kunde) p).getHausNr());
			String plz = Integer.toString( ((Kunde) p).getPlz() );
			schreibeZeile(plz);
			schreibeZeile(((Kunde) p).getOrt());
		}
	    if (p instanceof Mitarbeiter) {
			schreibeZeile("m");
			schreibeZeile(p.getVorname());
		    schreibeZeile(p.getNachname());
		    schreibeZeile(p.getNummer() + ""); // "" alternative kann -> Interger.toString 
		    schreibeZeile(p.getBenutzerName());
		    schreibeZeile(p.getPassword());
	    }
	    return true;
	
	}

	
	public Ereignis ladeEreignis() throws IOException {
	    String typ = liesZeile();
        if (typ == null) {
            return null;
        }
        String artikelNrStr = liesZeile();
        int artikelNr = Integer.parseInt(artikelNrStr);
        String artikBestAenderungStr = liesZeile();
        int bestandsAenderung = Integer.parseInt(artikBestAenderungStr);
        String benutzerNrStr = liesZeile();
        int benutzerNr = Integer.parseInt(benutzerNrStr);
        String benutzerName = liesZeile();
        String datum = liesZeile();
        Ereignis e =  new Ereignis(typ, artikelNr, bestandsAenderung, benutzerNr, benutzerName, datum);
        return e;
	}

	// Speichere  Ereignis 
	public boolean speichereEreignis(Ereignis e) throws IOException {
		schreibeZeile(e.getTyp());
        schreibeZeile(e.getArtikelNr() + "");
        schreibeZeile(e.getBestandsAenderung() + "");
        schreibeZeile(e.getBenutzerNr() + "");
        schreibeZeile(e.getBenutzerName());
        schreibeZeile(e.getDatum() + "");
        
        return true;
	}
	
	//Methoden lies Zeile und schreibe
    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }
    
    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }

}
