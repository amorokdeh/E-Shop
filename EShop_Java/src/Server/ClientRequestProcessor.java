package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

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

/**
 * Klasse zur Verarbeitung der Kommunikation zwischen EINEM Client und dem
 * Server. Die Kommunikation folgt dabei dem "Protokoll" der Anwendung. Das
 * ClientRequestProcessor-Objekt führt folgende Schritte aus: 
 * 0. Begrüßungszeile an den Client senden
 * Danach in einer Schleife:
 * 1. Empfang einer Zeile vom Client (z.B. Aktionsauswahl, hier eingeschränkt); 
 *    wenn der Client die Abbruchaktion sendet ('q'), wird die Schleife verlassen
 * 2. abhängig von ausgewählter Aktion Empfang weiterer Zeilen (Parameter für ausgewählte Aktion)
 * 3. Senden der Antwort an den Client; die Antwort besteht je nach Aktion aus einer oder mehr Zeilen
 * 
 */
class ClientRequestProcessor implements Runnable {

	// Eshopverwaltungsobjekt, das die eigentliche Arbeit machen soll
	private EShopInterface eShop; 

	// Datenstrukturen für die Kommunikation
	private Socket clientSocket;
	private BufferedReader in;
	private PrintStream out;

	
	/**
	 * @param socket
	 * @param eShop
	 */
	public ClientRequestProcessor(Socket socket, EShopInterface eShop) {

		this.eShop = eShop;
		clientSocket = socket;

		// I/O-Streams initialisieren und ClientRequestProcessor-Objekt als Thread starten:
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			try {
				clientSocket.close();
			} catch (IOException e2) {
			}
			System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
			return;
		}

		System.out.println("Verbunden mit " + clientSocket.getInetAddress()
				+ ":" + clientSocket.getPort());
	}

	/**
	 * Methode zur Abwicklung der Kommunikation mit dem Client gemäß dem
	 * vorgebenen Kommunikationsprotokoll.
	 */
	public void run() {

		String input = "";

		// Begrüßungsnachricht an den Client senden
		out.println("Server an Client: Bin bereit für Deine Anfragen!");

		// Hauptschleife zur wiederholten Abwicklung der Kommunikation
		do {
			// Beginn der Benutzerinteraktion:
			// Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
			try {
				input = in.readLine();
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				break;
			}

			// Eingabe bearbeiten:
			if (input == null) {
				// input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
				// Einfach behandeln wie ein "quit"
				input = "q";
			}
			else if (input.equals("l")) {
				// Aktion "einloggen _l" gewählt
				einloggen();
			}
			else if (input.equals("r")) {
				// Aktion "registrieren _r" gewählt
				registrieren();
			}
			else if (input.equals("a")) {
				// Aktion "Artikel_Ausgeben _a" gewählt
				ausgeben();
			} else if (input.equals("d")) {
				// Aktion "Artikel_löschen _d" gewählt
				loeschen();
			} else if (input.equals("e")) {
				// Aktion "Artikel_Einfügen _e" gewählt
				einfuegen();
			} else if (input.equals("f")) {
				// Aktion "Artiekl_suchen _f gewählt
				suchen();
			}
			else if (input.equals("w")) {
				// Aktion "Artikel_in_Warenkorb_liegen _w" gewählt
				artikelInWarenkorb();
			}
			else if (input.equals("u")) {
				// Aktion "Artikel_aus_Warenkorb_Entfernen _u" gewählt
				artikelEntfernen();
			}
			else if (input.equals("c")) {
				// Aktion "Alle_Artikel_aus_Warenkorb_Entfernen _c" gewählt
				alleArtikelEntfernen();
			}
			else if (input.equals("m")) {
				// Aktion "Warenkorb_anzeigen _m" gewählt
				warenkorbAnzeigen();
			}
			else if (input.equals("t")) {
				// Aktion "Mitarbeiter_registrieren _t" gewählt
				mitarbeiterRegistrieren();
			}
			else if (input.equals("s")) {
				// Aktion "speichern_s" gewählt
				speichern();
			}
			else if (input.equals("z")) {
				// Aktion "bezahlen_z" gewählt
				bezahlen();
			}
			else if (input.equals("x")) {
				// Aktion "ausloggen_x" gewählt
				ausloggen();
			}
			else if (input.equals("o")) {
				// Aktion "ereignis_ausgeben_o" gewählt
				ereignisAusgeben();
			}
			else if (input.equals("b")) {
				// Aktion "Alle_Mitarbeiter_ausgeben_b" gewählt
				MitarbeiterAusgeben();
			}
			else if (input.equals("v")) {
				// Aktion "Artikel_Bestand_in_Warenkorn_ändern_v" gewählt
				bestandImWarenkorbAendern();
			}

		} while (!(input.equals("q")));
		// Verbindung wurde vom Client abgebrochen:
		disconnect();		
	}

	private void einloggen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// hier ist nur der Benutzername
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (BenutzerName): ");
			System.out.println(e.getMessage());
		}
		String benuterName = new String(input);
		
		// hier ist nur der Passwort
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Passwort): ");
			System.out.println(e.getMessage());
		}
		String passwort = new String(input);
		
		
		try {
			eShop.einloggen(benuterName, passwort);
			out.println("Erfolg");
			out.println(eShop.eingeloggtePerson().getVorname());
			out.println(eShop.eingeloggtePerson().getNachname());
			out.println(eShop.eingeloggtePerson().getNummer());
			out.println(eShop.eingeloggtePerson().getBenutzerName());
			out.println(eShop.eingeloggtePerson().getPassword());
			if(eShop.eingeloggtePerson() instanceof Kunde) {
				Kunde k = (Kunde) eShop.eingeloggtePerson();
				out.println("K"); //Kunde
				out.println(k.getStrasse());
				out.println(k.getHausNr());
				out.println(k.getPlz());
				out.println(k.getOrt());
			} else {
				out.println("M"); //Mitarbeiter
			}
			System.out.println("Benutzer " + benuterName + " eingeloggt");
		} catch (LoginFehlgeschlagenException e) {
			System.out.println("--->Fehler beim Einloggen: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		}
		
	}
	
	public void registrieren() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// hier ist nur der Vorname
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Vorname): ");
			System.out.println(e.getMessage());
		}
		String vorname = new String(input);
		
		// hier ist nur der Nachname
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Nachname): ");
			System.out.println(e.getMessage());
		}
		String nachname = new String(input);
		
		// hier ist nur der benutzername
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (benutzername): ");
			System.out.println(e.getMessage());
		}
		String benutzername = new String(input);
		
		// hier ist nur der Passwort
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Passwort): ");
			System.out.println(e.getMessage());
		}
		String passwort = new String(input);
		
		// hier ist nur der Strasse
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Strasse): ");
			System.out.println(e.getMessage());
		}
		String strasse = new String(input);
		
		// hier ist nur der Hausnummer
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (hausnummer): ");
			System.out.println(e.getMessage());
		}
		String hausnummer = new String(input);
		
		// hier ist nur der PLZ
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (PLZ): ");
			System.out.println(e.getMessage());
		}
		String CityCode = new String(input);
		int plz = Integer.parseInt(CityCode);
		
		// hier ist nur der Ort
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Ort): ");
			System.out.println(e.getMessage());
		}
		String ort = new String(input);
		
		
		try {
			boolean registriert = eShop.registrieren(new Kunde(vorname, nachname, 0, benutzername, passwort, strasse, hausnummer, plz, ort));
			if(registriert) {
				System.out.println("Nutzer wurde registriert");
				out.println("Erfolg");
            } else {
            	System.out.println("Nutzer wurde nicht registriert. Benutzername existiert bereits");
            	out.println("Fehler");
            }
			
		} catch (BenutzerExistiertBereitsException e) {
			System.out.println("--->Fehler beim Registrieren: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		} catch(IOException e1) {
			System.out.println("--->Fehler");
			System.out.println(e1.getMessage());
		}
	}
	
	public void mitarbeiterRegistrieren() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// hier ist nur der Vorname
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Vorname): ");
			System.out.println(e.getMessage());
		}
		String vorname = new String(input);
		
		// hier ist nur der Nachname
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Nachname): ");
			System.out.println(e.getMessage());
		}
		String nachname = new String(input);
		
		// hier ist nur der benutzername
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (benutzername): ");
			System.out.println(e.getMessage());
		}
		String benutzername = new String(input);
		
		// hier ist nur der Passwort
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Passwort): ");
			System.out.println(e.getMessage());
		}
		String passwort = new String(input);
		
		try {
			boolean registriert = eShop.registrieren(new Mitarbeiter(vorname, nachname, 0, benutzername, passwort));
			if(registriert) {
				System.out.println("Mitarbeiter wurde registriert");
				out.println("Erfolg");
            } else {
            	System.out.println("Mitarbeiter wurde nicht registriert. Benutzername existiert bereits");
            	out.println("Fehler");
            }
		} catch (BenutzerExistiertBereitsException e) {
			System.out.println("--->Fehler beim Registrieren: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		} catch(IOException e1) {
			System.out.println("--->Fehler");
			System.out.println(e1.getMessage());
		}
	}
	private void speichern() {
		// Parameter sind in diesem Fall nicht einzulesen
		
		// die Arbeit macht wie immer Eshopverwaltungsobjekt:
		try {
			eShop.schreibeArtikel();
			eShop.schreibePerson();
			eShop.schreibeEreignis();
			out.println("Erfolg");
		} catch (IOException e) {
			System.out.println("--->Fehler beim Sichern: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		}
	}

	private void suchen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// hier ist nur der Titel der gesuchten Artikel erforderlich:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelname): ");
			System.out.println(e.getMessage());
		}
		// Achtung: Objekte sind Referenzdatentypen:
		// Artikelname in neues String-Objekt kopieren, 
		// damit Artikelname nicht bei nächster Eingabe in input überschrieben wird
		String name = new String(input);

		// die eigentliche Arbeit soll das Eshopverwaltungsobjekt machen:
		List<Artikel> artikel = null;
		if (name.equals(""))
			artikel = eShop.gibAlleArtikel();
		else
			artikel = eShop.sucheNachArtikelName(name);

		sendealleartikelAnClient(artikel);
	}

	private void ausgeben() {
		// Die Arbeit soll wieder das Artikelverwaltungsobjekt machen:
		List<Artikel> artikel = null;
		artikel = eShop.gibAlleArtikel();

		sendealleartikelAnClient(artikel);
	}

	private void sendealleartikelAnClient(List<Artikel> artikel) {
		// Anzahl der gefundenen Artikel senden
		out.println(artikel.size());
		for (Artikel a: artikel) {
			sendArtikelAnClient(a);
		}
	}

	private void sendArtikelAnClient(Artikel artikel) {
		// Name des Artikel senden
		out.println(artikel.getName());
		// Nummer des Artikel senden
		out.println(artikel.getNummer());
		// Bestand des Artikel senden
		out.println(artikel.getBestand());
		// Preis des Artikel senden
		out.println(artikel.getPreis());
	}
	
	private void MitarbeiterAusgeben() {
		// Die Arbeit soll wieder das Artikelverwaltungsobjekt machen:
		List<Mitarbeiter> mitarbeiter = null;
		mitarbeiter = eShop.gibAlleMitarbeiter();

		sendealleMitarbeiterAnClient(mitarbeiter);
	}
	
	private void sendealleMitarbeiterAnClient(List<Mitarbeiter> mitarbeiter) {
		// Anzahl der gefundenen Mitarbeiter senden
		out.println(mitarbeiter.size());
		for (Mitarbeiter m: mitarbeiter) {
			sendArtikelAnClient(m);
		}
	}

	private void sendArtikelAnClient(Mitarbeiter mitarbeiter) {
		// vorname senden
		out.println(mitarbeiter.getVorname());
		// Nachname senden
		out.println(mitarbeiter.getNachname());
		// Nummer senden
		out.println(mitarbeiter.getNummer());
		// Benutzername senden
		out.println(mitarbeiter.getBenutzerName());
		// Passwort senden
		out.println(mitarbeiter.getPassword());
	}
	
	private void ereignisAusgeben() {
		// Die Arbeit soll wieder das Ereignisverwaltungsobjekt machen:
		List<Ereignis> ereignis = null;
		ereignis = eShop.gibEreignisListe();

		sendealleEreignisAnClient(ereignis);
		
	}
	
	private void sendealleEreignisAnClient(List<Ereignis> ereignis) {
		// Anzahl der gefundenen Ereignis senden
		out.println(ereignis.size());
		for (Ereignis e: ereignis) {
			sendEreignisAnClient(e);
		}
	}

	private void sendEreignisAnClient(Ereignis ereignis) {
		// Typ des Ereignis senden
		out.println(ereignis.getTyp());
		// Nummer des Ereignis senden
		out.println(ereignis.getArtikelNr());
		//Bestand-Änderung senden
		out.println(ereignis.getBestandsAenderung());
		// Benutzernummer senden
		out.println(ereignis.getBenutzerNr());
		// Benutzername senden
		out.println(ereignis.getBenutzerName());
		// Datum senden
		out.println(ereignis.getDatum());
	}
	
	private void artikelInWarenkorb() {
		String input = null;
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelnummer): ");
			System.out.println(e.getMessage());
		}
		int artikelNummer = Integer.parseInt(input);
		
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelmenge): ");
			System.out.println(e.getMessage());
		}
		int menge = Integer.parseInt(input);
		
		try {
			eShop.artikelInWarenkorb(artikelNummer, menge);
			out.println("Erfolg");
		} catch (ArtikelExistiertNichtException e) {
			out.println("Fehler");
			e.printStackTrace();
		} catch (ArtikelBestandZuWenigException e) {
			out.println("Fehler");
			e.printStackTrace();
		}
	}
	private void warenkorbAnzeigen() {
		// Die Arbeit soll wieder das Warenkorbverwaltungsobjekt machen:
		List<Artikel> artikel = null;
		artikel = eShop.warenkorbAnzeigen();

		sendealleartikelAnClient(artikel);
	}

	private void einfuegen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		
		// zuerst die Name des einzufügenden Artikel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelname): ");
			System.out.println(e.getMessage());
		}
		// Achtung: Objekte sind Referenzdatentypen:
		// Artikelname in neues String-Objekt kopieren, 
		// damit Artikelname nicht bei nächste Eingabe in input überschrieben wird
		String artikelName = new String(input);
		
		//dann Nummer des einzufügenden Artikel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (ArtikelNummer): ");
			System.out.println(e.getMessage());
		}
		int artikelNummer = Integer.parseInt(input);
		
		//dann Bestand des einzufügenden Artikel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (ArtikelBestand): ");
			System.out.println(e.getMessage());
		}
		int artikelBestand = Integer.parseInt(input);
		
		//dann Preis des einzufügenden Artikel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (ArtikelPreis): ");
			System.out.println(e.getMessage());
		}
		float artikelPreis = Float.parseFloat(input);


		// die eigentliche Arbeit soll das Eshopverwaltungsobjekt machen:
		try {
			Artikel artikel = eShop.fuegeArtikelEin(artikelName, artikelNummer, artikelBestand, artikelPreis);
			// Rückmeldung an den Client: Aktion erfolgreich!
			out.println("Erfolg");
			sendArtikelAnClient(artikel);
		} catch (ArtikelExistiertBereitsException e) {
			// Rückmeldung an den Client: Fehler!
			out.println("Fehler");
			out.println(e.getMessage());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void loeschen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// zuerst die Nummer des einzufügenden Artikel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelnummer): ");
			System.out.println(e.getMessage());
		}
		int artikelNr = Integer.parseInt(input);

		// die eigentliche Arbeit soll das Eshopverwaltungsobjekt machen:
		try {
			eShop.loescheArtikel(artikelNr);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		// Rückmeldung an den Client: Aktion erfolgreich!
		out.println("Erfolg");
	}
	
	private void artikelEntfernen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// zuerst die Nummer des einzufügenden Artikel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (ArtikelNr): ");
			System.out.println(e.getMessage());
		}
		int artikelNr = Integer.parseInt(input);
		eShop.artikelAusWarenkorbNehmen(artikelNr);
		// Rückmeldung an den Client: Aktion erfolgreich!
		out.println("Erfolg");
	}
	
	//Alle Artikel in Warenkorb entfernen
	private void alleArtikelEntfernen() {
		eShop.warenKorbLoeschen();
		out.println("Erfolg");
	}
	
	private void bestandImWarenkorbAendern() {
		String artikelNum = null;
		String artikelMenge = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// zuerst die Nummer des Artikel:
		try {
			artikelNum = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelmenge): ");
			System.out.println(e.getMessage());
		}
		int nummer = Integer.parseInt(artikelNum);
		
		//dann die Menge des Artikel:
		try {
			artikelMenge = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Artikelmenge): ");
			System.out.println(e.getMessage());
		}
		int menge = Integer.parseInt(artikelMenge);
		
		try {
			boolean bestandAenderung = eShop.bestandImWarenkorbAendern(nummer, menge);
			if(bestandAenderung) {
				System.out.println("Artikelmenge ist: " +  menge);
				out.println("Erfolg");
			} else {
				out.println("Fehler");
			}
		} catch (ArtikelExistiertNichtException e) {
			out.println("Fehler");
			System.out.println("Artikel nicht gefunden");
			//e.printStackTrace();
		} catch (ArtikelBestandZuWenigException e) {
			out.println("Fehler");
			System.out.println("Artikel Bestand zu wenig");
		}
		
		
	}
	
	//Alle Artikel Warenkorb kaufen
	private void bezahlen() {
		try {
			List<Artikel> artikel = null;
			artikel = eShop.warenkorbAnzeigen();
			int artikelAnzahl = artikel.size();
			out.println(artikelAnzahl);
			String rechnung = eShop.warenkorbKaufen();
			out.println(rechnung);
			speichern();
		} catch (ArtikelExistiertNichtException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Person ausloggen
	private void ausloggen() {
		System.out.println("Benutzer " + eShop.eingeloggtePerson().getBenutzerName() + " ausgeloggt");
		eShop.ausloggen();
		out.println("Erfolg");
	}
	
	//Verbindung mit dem Server/Client abbrechen
	private void disconnect() {
		try {
			out.println("Tschuess!");
			clientSocket.close();

			System.out.println("Verbindung zu " + clientSocket.getInetAddress()
					+ ":" + clientSocket.getPort() + " durch Client abgebrochen");
		} catch (Exception e) {
			System.out.println("--->Fehler beim Beenden der Verbindung: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		}
	}
}