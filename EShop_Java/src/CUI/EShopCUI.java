package CUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;

import javax.swing.JOptionPane;

import Datenstrukturen.Artikel;
import Datenstrukturen.Kunde;
import Datenstrukturen.Mitarbeiter;
import Datenstrukturen.Person;
import Domain.EShop;
import Exceptions.ArtikelExistiertBereitsException;
import Exceptions.ArtikelExistiertNichtException;
import Exceptions.BenutzerExistiertBereitsException;
import Exceptions.EShopException;
import Exceptions.LoginFehlgeschlagenException;
import Server.EShopFassade;
import Server.EShopInterface;

/**
 * Klasse für sehr einfache Benutzungsschnittstelle für das EShop.
 * Die Benutzungsschnittstelle basiert auf Ein- und Ausgaben auf der Kommandozeile.
 *
 * @author Mohamad und Amr 
 * 
 */

public class EShopCUI {
	
	public static final int DEFAULT_PORT = 6789;

    private EShopInterface eShop;
    private BufferedReader in;
    
    public EShopCUI(String host, int port) throws IOException {
        eShop = new EShopFassade(host, port);
        // Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
        in = new BufferedReader(new InputStreamReader(System.in));
    }
    
    
    // prueft ob Benutzer eingeloggt ist 
    
    private Person eingeloggtePerson(){
        return eShop.eingeloggtePerson();
    }
    
    private void gibMenueAus() {
        if (eingeloggtePerson() == null) {
            gibStartMenueAus();
        }else{
        	gibBenutzerMenueAus(eingeloggtePerson());
        }
    }
    

    private void gibStartMenueAus() {
        System.out.print("\n Befehle: \n   Login: 'l'");
        System.out.print("         \n   Registrieren: 'r'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.flush(); // ohne NL ausgeben
    }
   
    // Gibt die Art des Benutzer ( Mitarbeiter / Kunde ) wieder 
    private void gibBenutzerMenueAus(Person person){
        if (person instanceof Mitarbeiter){
        	gibBenutzerMenueAus((Mitarbeiter)person);
        }else if (person instanceof Kunde){
        	gibBenutzerMenueAus((Kunde)person);
        }else{
            throw new RuntimeException("Die Art des Benutzers ist unbekannt");
        }
    }
    
    
    private void gibBenutzerMenueAus(Mitarbeiter person){
        System.out.print("\n Befehle: ");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Artikel anzeigen:  'a'");
        System.out.print("         \n  Artikel löschen:  'd'");
        System.out.print("         \n  Artikel einfügen: 'e'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Mitarbeiter erstellen:  't'");
        System.out.print("         \n  Daten sichern:  's'");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Ausloggen:  'x'");
        System.out.println("         \n  Beenden:        'q'");
        System.out.flush(); // ohne NL ausgeben
    }

    private void gibBenutzerMenueAus(Kunde person) {
        System.out.print("\n Befehle: ");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Artikel anzeigen:  'a'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Artikel in Warenkorb:  'w'");
        System.out.print("         \n  Warenkorb anzeigen: 'm'");
        System.out.print("         \n  Artikel aus Warenkorb entfernen: 'u'");
        System.out.print("         \n  Warenkorb leeren: 'c'");
        System.out.print("         \n  Bezahlen:  'z'");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Ausloggen:  'x'");
        System.out.println("         \n  Beenden:        'q'");
        System.out.flush(); // ohne NL ausgeben
    }
    
    /* (non-Javadoc)
     * 
     * Interne (private) Methode zum Einlesen von Benutzereingaben.
     */
    private String liesEingabe(String promptText) throws IOException {
        // einlesen von Konsole
        String input = "";
        while (input.length() == 0){
            System.out.print(promptText+" > ");
            input = in.readLine().trim();
        }
        return input;
    }
    
    private int liesEingabeGanzzahl(String promptText) throws IOException {
        // einlesen von Konsole
        int nummer = -1;
        while (true){
            try{
                nummer = Integer.parseInt(liesEingabe(promptText));
                if (nummer >= 0){
                    break;
                }
            } catch (NumberFormatException e){
                //nothing to do here
            }
            System.out.println("Bitte eine positive Ganzzahl eingeben...");
        }
        return nummer;
    }
    /* (non-Javadoc)
     * 
     * Interne (private) Methode zur Verarbeitung von Eingaben
     * und Ausgabe von Ergebnissen.
     */

    protected void verarbeiteEingabe(String line) throws EShopException, IOException {
        if (eingeloggtePerson() == null) {
            verarbeiteStartMenueEingabe(line);
        } else {
        	verarbeiteBenutzerEingabe(line, eingeloggtePerson());
        }
    }
    
    private void verarbeiteBenutzerEingabe(String line, Person person) throws EShopException, IOException {
    	if (person instanceof Mitarbeiter){
        	verarbeiteBenutzerEingabe(line,(Mitarbeiter)person);
        }else if (person instanceof Kunde){
        	verarbeiteBenutzerEingabe(line,(Kunde)person);
        }else{
            throw new RuntimeException("Die Art des Benutzers ist unbekannt");
        }
    }
    //Mitarbeiter Eingabe
    private void verarbeiteBenutzerEingabe(String line, Mitarbeiter person) throws EShopException, IOException {
    	
    	String artikelName;
    	int artikelNummer;
    	int artikelBestand;
    	float artikelpreis;
        List<Artikel> liste;
        String benutzerName;
        String passwort;
 
        // Eingabe bearbeiten:
        switch (line) {
            case "a":
                // Artikel ausgeben
                liste = eShop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
            case "d":
                // Artikel loeschen Eingabe
                artikelNummer = liesEingabeGanzzahl("Artikelnummer");
                eShop.loescheArtikel(artikelNummer);
                break;
            case "e":
                // Artikel einfuegen Eingabe
                artikelName = liesEingabe("Artikelsname");
                artikelNummer = liesEingabeGanzzahl("Artikelnummer");
                artikelBestand = liesEingabeGanzzahl("Bestand");
                artikelpreis = liesEingabeGanzzahl("Preis");
                eShop.fuegeArtikelEin(artikelName, artikelNummer, artikelBestand, artikelpreis);
                System.out.println("Einfügen ok");
                break;
            case "f":
                // Artikel suchen und ausgeben
            	artikelName = liesEingabe("Artikelsname");
                liste = eShop.sucheNachArtikelName(artikelName);
                gibArtikellisteAus(liste);
                break;
            case "s":
            	eShop.speichern();
                System.out.println("Die Artikel und Nutzer wurden gespeichert.");
                break;
            case "t":
                String vorname = liesEingabe("Vorname");
                String nachname = liesEingabe("Nachname");
                //String mitarbeiterID = liesEingabe("MitarbiterID");
                //int mitID = Integer.parseInt(mitarbeiterID);
                benutzerName = liesEingabe("Benutzername");
                passwort = liesEingabe("Passwort");
                //Mitarbeiter registrieren
                boolean registriert = eShop.registrieren(new Mitarbeiter(vorname, nachname, 0, benutzerName, passwort));
                if(registriert) {
                	System.out.println("Mitarbeiter wurde registriert");
                } else {
                	System.out.println("Mitarbeiter wurde nicht registriert. Benutzername existiert bereits");
                }
                break;
            case "x":
            	eShop.ausloggen();
                break;
            case "q":
            	eShop.schreibePerson();
            	eShop.schreibeArtikel();
            	eShop.schreibeEreignis();
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }
    
  //Kunden Eingabe
    private void verarbeiteBenutzerEingabe(String line, Kunde person) throws EShopException, IOException {
        List<Artikel> liste;
        String artikelName;
        int artikelNummer;
        int menge;

        switch (line){
            case "a":
                // Artikel ausgeben
                liste = eShop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
            case "f":
                // Artikel suchen und ausgeben
            	artikelName = liesEingabe("ArtikelName");
                liste = eShop.sucheNachArtikelName(artikelName);
                gibArtikellisteAus(liste);
                break;
            case "w":
                //Artikel in den Warenkorb
                System.out.println("Artikel in den Warenkorb");
                artikelNummer = liesEingabeGanzzahl("Artikelnummer");
                menge = liesEingabeGanzzahl("Menge");
                try {
                	eShop.artikelInWarenkorb(artikelNummer, menge);
                } catch(ArtikelExistiertNichtException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "m":
                //Warenkorb ausgeben
                System.out.println("Im Warenkorb: ");
                liste = eShop.warenkorbAnzeigen();
                gibArtikellisteAus(liste);
                break;
            case "u":
                //Artikel aus Warenkorb nehmen
            	artikelNummer = liesEingabeGanzzahl("Artikelnummer: ");
                eShop.artikelAusWarenkorbNehmen(artikelNummer);
                break;
            case "c":
                //Warenkorb loeschen
            	eShop.warenKorbLoeschen();
                break;
            case "z":
                //Warenkorb kaufen
            	eShop.warenkorbKaufen();
                break;
            case "x":
            	eShop.ausloggen();
                break;
            case "q":
            	eShop.schreibePerson();
            	eShop.schreibeArtikel();
            	eShop.schreibeEreignis();
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }
    
    private void verarbeiteStartMenueEingabe(String line) throws EShopException, IOException {
       String benutzerName;
       String passwort;

        switch (line) {
            //Login verarbeiten
            case "l":
            	benutzerName = liesEingabe("Benutzername");
                passwort = liesEingabe("Passwort");
                Person person = eShop.einloggen(benutzerName, passwort);
                System.out.print(person.getBenutzerName() + " wurde eingeloggt.");
                break;
            //Registrierung verarbeiten
            case "r":
                String vorname = liesEingabe("Vorname");
                String nachname = liesEingabe("Nachname");
                //String kundenID = liesEingabe("KundenID");
                //int kundeID = Integer.parseInt(kundenID);
                benutzerName = liesEingabe("Benutzername");
                passwort = liesEingabe("Passwort");
                String strasse = liesEingabe("Strasse");
                String hausNummer = liesEingabe("Hausnummer");
                String CityCode = liesEingabe("PLZ");
                int plz = Integer.parseInt(CityCode);
                String ort = liesEingabe("Ort");
                
                //Kunde registrieren
                boolean registriert = eShop.registrieren(new Kunde(vorname, nachname, 0 , benutzerName, passwort, strasse, hausNummer, plz, ort));
                eShop.schreibePerson();
                if(registriert) {
                	System.out.println("Nutzer wurde registriert");
                } else {
                	System.out.println("Nutzer wurde nicht registriert. Benutzername existiert bereits");
                }
                break;
            case "q":
                eShop.schreibePerson();
                eShop.schreibeArtikel();
                eShop.schreibeEreignis();
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }
    
    /**
    * (non-Javadoc)
    * 
    * Interne (private) Methode zum Ausgeben von Artikelslisten.
    *
    */
    
    private void gibArtikellisteAus(List liste) {
        if (liste.isEmpty()) {
            System.out.println("Liste ist leer.");
        } else {
            for (Object artikel : liste) {
                System.out.println(artikel);
            }
        }
    }
    
    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    
    public void run() {
        // Variable für Eingaben von der Konsole
        String input = "";

        // Hauptschleife der Benutzungsschnittstelle
        do {

            gibMenueAus();

            try {

                input = liesEingabe("");
                verarbeiteEingabe(input);

            } catch (EShopException eshopException){

                System.out.println("\n[!] " + eshopException.getMessage() + "\n");

            } catch (IOException ioException) {

                ioException.printStackTrace();

            }

        } while (! (input.equals("q")) );
    }
    
    
    
// ########## MAIN ##############
    
    public static void main(String[] args) {
    	int port = 0;
		String host = null;
		InetAddress ia = null;

    	// Host- und Port-Argumente einlesen, wenn angegeben
		if (args.length > 2) {
			System.out.println("Aufruf: java ClientGUI [<hostname> [<port>]]");
			System.exit(0);
		}
		switch (args.length) {
		case 0:
			try {
				ia = InetAddress.getLocalHost();
			} catch (Exception e) {
				System.out.println("XXX InetAdress-Fehler: " + e);
				System.exit(0);
			}
			host = ia.getHostName(); // host ist lokale Maschine
			port = DEFAULT_PORT;
			break;
		case 1:
			port = DEFAULT_PORT;
			host = args[0];
			break;
		case 2:
			host = args[0];
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out.println("Aufruf: java ClientGUI [<hostname> [<port>]]");
				System.exit(0);
			}				
		}
		
		// CUI auf Starten und mit Server auf Host und Port verbinden
		EShopCUI cui;
		try {
			cui = new EShopCUI(host, port);
			cui.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
