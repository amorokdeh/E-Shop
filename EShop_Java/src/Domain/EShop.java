package Domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import Datenstrukturen.Artikel;
import Datenstrukturen.Ereignis;
import Datenstrukturen.Kunde;
import Datenstrukturen.Mitarbeiter;
import Datenstrukturen.Person;
import Exceptions.ArtikelBestandZuWenigException;
import Exceptions.ArtikelExistiertBereitsException;
import Exceptions.ArtikelExistiertNichtException;
import Exceptions.BenutzerExistiertBereitsException;
import Exceptions.LoginFehlgeschlagenException;
import Server.EShopInterface;

public class EShop implements EShopInterface {
	
	  private String datei = "";
	  private PersonManager meinePerson;
	  private LagerManager meinArtikel;
	  private EreignisManager meinErg; 
	  private WarenkorbManager meinWarenkorb;
	  private Person eingeloggtePerson;
	  
	  
  /**
     * Delegiert Aufgaben an Managers --> Lager, Warenkorb und Person
     * @param datei
     * @throws IOException
 */
	  
  public EShop(String datei) throws IOException {
        this.datei = datei;

        meinArtikel= new LagerManager();
        meinArtikel.liesDaten(datei+"_ARTIKEL.txt");

        meinWarenkorb = new WarenkorbManager(meinArtikel);

        meinePerson= new PersonManager();
        meinePerson.liesDaten(datei+"_PERSON.txt");
        
        meinErg = new EreignisManager();
        meinErg.liesDaten(datei+"_EREIGNIS.txt");

    }
  

  /**
   * Funktionen fuer Person
   * Person ueber PersonManager registrieren.
   * @param p
   * @throws BenutzerExistiertBereitsException
   */
  public boolean registrieren(Person p) throws BenutzerExistiertBereitsException, IOException {
     boolean r = meinePerson.registrieren(p);
     if(p instanceof Kunde) {
    	String typ = "Kunde registriert";
 	    meinErg.erstelleEreignis(p,  typ);
 	    schreibeEreignis();
     }
     if(p instanceof Mitarbeiter) {
     	String typ = "Mitarbeiter registriert von " + eingeloggtePerson.getBenutzerName();
  	    meinErg.erstelleEreignis(p,  typ);
  	    schreibeEreignis();
      }
     schreibePerson();
     return r;
  }


	/**
	 * Gibt Anzahl der Person zurueck.
	 * @return int Personanzahl
	 */
	public int personAnzahl() {
	    return meinePerson.nutzer.size();
	}
	
	public List<Mitarbeiter> gibAlleMitarbeiter() {
		List<Mitarbeiter> m = new Vector();
		for (int i = 0; i < personAnzahl(); i++){
        	if (meinePerson.nutzer.get(i) instanceof Mitarbeiter) {
        		m.add((Mitarbeiter) meinePerson.nutzer.get(i));
        	}
		}
	return m;
	}
	
	public void schreibePerson() throws IOException {
	    meinePerson.schreibePerson(datei + "_PERSON.txt");
	}
	
	
	 /**
     * Gibt Person als eingeloggtenePerson wieder.
     * @param benutzerName
     * @param passwort
     * @return
     * @throws LoginFehlgeschlagenException
     */
    public Person einloggen(String benutzerName, String passwort) throws LoginFehlgeschlagenException{
        return (eingeloggtePerson = meinePerson.einloggen(benutzerName, passwort));
    }
    
    /**
     * Benutzer wird ausgeloggt.
     * eingeloggtePerson = null;
     */
    public void ausloggen(){
    	eingeloggtePerson = null;
    }

    /**
     * Gibt eingeloggten Benutzer wieder.
     * @return eingeloggtePerson
     */
    public Person eingeloggtePerson(){
        return eingeloggtePerson;
    }
    
    
    
    // ####################### Ereignisse #############################

    public void schreibeEreignis() throws IOException {
    	meinErg.schreibeEreignisse(datei + "_EREIGNIS.txt");
    }

    public List<Ereignis> gibEreignisListe(){
        return meinErg.gibEreignisListe();
    }
    
    //################### Funktionen fuer Artikel #######################

    /**
     * Gibt alle Artikel wieder.
     * @return meineArtikel.getArtikelBestand()
     */
    public List<Artikel> gibAlleArtikel() {
        return meinArtikel.getArtikelBestand();
    }
    
    
    /**
     * Gibt Menge der Artikel im Shop wieder.
     * @return meinArtikel.getArtikelBestand().size()
     */
    public int artikelMenge() {
        return meinArtikel.getArtikelBestand().size();
    }
    
    
    /**
     * Sucht nach Artikelname und gibt entsprechende Artikel in einer Liste zurueck.
     * @param artikelName
     * @return List<Artikel>
     */
    public List<Artikel> sucheNachArtikelName(String artikelName) {
        return meinArtikel.sucheArtikel(artikelName);
    }
    
    /**
     * Gibt Artikel mit bestimmter Artikelnummer zurueck.
     * @param nrmmer
     * @return Artikel
     */
    public Artikel sucheNachNummer(int nrmmer) {
        return meinArtikel.sucheArtikelNummer(nrmmer);
    }
    
    
    // aendert den Bestand eines Artikels
    
    public void aendereBestand(int nummer, int menge) {
        meinArtikel.sucheArtikelNummer(nummer).setBestand(menge);
    }
    

    /**
     * Fuegt Artikel im Shop hinzu.
     * @param artikelName
     * @param artikelNummer
     * @param artikelPreis
     * @param artikelBestand
     * @return Artikel
     * @throws ArtikelExistiertBereitsException
     */
    public Artikel fuegeArtikelEin(String artikelName, int artikelNummer, int artikelBestand, float artikelPreis) throws ArtikelExistiertBereitsException, IOException {
        // boolean verfuegbar;
        artikelNummer = artikelMenge() + 1;
        Artikel a = new Artikel(artikelName, artikelNummer,  artikelBestand, artikelPreis);
        if(artikelBestand >= 1) { a.setVerfeugbar(true); }
        meinArtikel.einfuegen(a);
        String typ = "Artikel einfuegen";
        meinErg.erstelleEreignis(eingeloggtePerson, a, artikelBestand, typ);
        schreibeEreignis();
        schreibeArtikel();
        return a;
    }
    

    /**
     * Loescht Artikel aus meinArtikel.
     * @param nummer
     */
    public void loescheArtikel(int nummer) throws IOException {
        Artikel a = meinArtikel.sucheArtikelNummer(nummer);
        if (a != null) {
            meinArtikel.loeschen(a);
            int menge = 0;
            String typ = "Artikel loeschen";
            meinErg.erstelleEreignis(eingeloggtePerson, a, menge, typ);
            schreibeEreignis();
            schreibeArtikel();
        }
    }
    
    /**
     * Speichert Artikel in .txt Datein
     * @throws IOException
     */
    public void schreibeArtikel() throws IOException {
        meinArtikel.schreibeDaten(datei + "_ARTIKEL.txt");
    }
    
    
    
    //################# Funktionen fuer Warenkorb ##########################

    /**
     * Gibt Warenkorb von Kunde wieder.
     * @return Liste<Artikel> mit Artikeln im Warenkorb.
     */
    public List<Artikel> warenkorbAnzeigen() {
        if (eingeloggtePerson instanceof Kunde) {
            return meinWarenkorb.zeigeWarenkorb(((Kunde) eingeloggtePerson).getWarenkorb());
        } else {
            System.out.println("Kein Warenkorb vorhanden!");
            return null;
        }
    }
    
    
    /**
     *Fuegt Artikel mit Nummer und Menge im Warenkorb hinzu.
     * @param nummer
     * @param menge
     * @throws ArtikelExistiertNichtException
     * @throws ArtikelBestandZuWenigException
     */
    public void artikelInWarenkorb(int nummer, int menge) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException {
        Artikel artikel = meinArtikel.sucheArtikelNummer(nummer);
        List<Artikel> wk = warenkorbAnzeigen();
        if (artikel == null) {
            throw new ArtikelExistiertNichtException(artikel);
        } else if (artikel.getBestand() < menge) {
            throw new ArtikelBestandZuWenigException();
        } else {
        	boolean added = false;
        	for(Artikel a : wk) {
        		if (artikel.getNummer() == a.getNummer()) {
        			meinWarenkorb.bestandImWarenkorb(((Kunde) eingeloggtePerson).getWarenkorb(), artikel, menge);
                    added = true;
                    if (((Kunde) eingeloggtePerson).getWarenkorb().getMenge(artikel) <= 0) {
                        artikelAusWarenkorbNehmen(artikel.getNummer());
                    }
        		}
        	}
        	if(!added) {
        		meinWarenkorb.artikelInWarenkorb(((Kunde) eingeloggtePerson).getWarenkorb(), artikel, menge);
        	}
        }
            
    }

    public boolean bestandImWarenkorbAendern(int nummer, int menge) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException {
        
    	Artikel artikel = meinArtikel.sucheArtikelNummer(nummer);
    	List<Artikel> wk = warenkorbAnzeigen();
    	
    	if (artikel == null) {
            return false;
        } else {
        	for(Artikel a : wk) {
            	if (artikel.getNummer() == a.getNummer()) {
            		if (artikel.getBestand() < menge) {
                        throw new ArtikelBestandZuWenigException();
                    } else {
                        meinWarenkorb.bestandImWarenkorb(((Kunde) eingeloggtePerson).getWarenkorb(), artikel, menge);
                        
                        if (((Kunde) eingeloggtePerson).getWarenkorb().getMenge(artikel) <= 0) {
                            artikelAusWarenkorbNehmen(artikel.getNummer());
                        }
                        return true;
                    }
            	}
            }
        	return false;
        }
    }
    
    
    /**
     * Artikel aus Warenkorb loeschen
     * @param nummer
     */
    public void artikelAusWarenkorbNehmen(int artikelNummer) {
        Artikel artikel = meinArtikel.sucheArtikelNummer(artikelNummer);
        meinWarenkorb.artikelAusWarenkorb(((Kunde) eingeloggtePerson).getWarenkorb(), artikel);
    }

    public String warenkorbKaufen() throws IOException, ArtikelExistiertNichtException {
    	List<Artikel> artikelInWK = meinWarenkorb.zeigeWarenkorb(((Kunde) eingeloggtePerson).getWarenkorb());
    	for(Artikel artikel : artikelInWK) {
    		Artikel a = meinArtikel.sucheArtikelNummer(artikel.getNummer());
    		meinArtikel.veraendereBestand(a, a.getBestand() - artikel.getBestand());
    		String typ = "Artikel Kaufen";
    	    meinErg.erstelleEreignis(eingeloggtePerson, a, artikel.getBestand(), typ);
    	    schreibeEreignis();
    		
    	}
        return meinWarenkorb.warenkorbKaufen(((Kunde) eingeloggtePerson).getWarenkorb(), ((Kunde) eingeloggtePerson));
    }

    
    /**
     * Loescht den Warenkorb
     */
    public void warenKorbLoeschen() {
        meinWarenkorb.warenkorbLoeschen(((Kunde) eingeloggtePerson).getWarenkorb());
    }
    
    @Override
	public void disconnect() throws IOException {
		// Hier gibt's nichts zu tun, da Anforderungen eines Verbindungsabbruchs 
		// durch einen Client bereits vom ClientRequestProcessor verarbeitet werden.
	}


	public void speichern() {
		try {
			schreibeArtikel();
			schreibePerson();
			schreibeEreignis();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
