package Server;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
	
public class EShopFassade implements EShopInterface{
	// Datenstrukturen für die Kommunikation
		private Socket socket = null;
		private BufferedReader sin; // server-input stream
		private PrintStream sout; // server-output stream
		Person eingeloggtePerson = null;
		
		
		/**
		 * Konstruktor, der die Verbindung zum Server aufbaut (Socket) und dieser
		 * Grundlage Eingabe- und Ausgabestreams für die Kommunikation mit dem
		 * Server erzeugt.
		 * 
		 * @param host Rechner, auf dem der Server läuft
		 * @param port Port, auf dem der Server auf Verbindungsanfragen warten
		 * @throws IOException
		 */
		public EShopFassade(String host, int port) throws IOException {
			try {
				// Socket-Objekt fuer die Kommunikation mit Host/Port erstellen
				socket = new Socket(host, port);

				// Stream-Objekt fuer Text-I/O ueber Socket erzeugen
				InputStream is = socket.getInputStream();
				sin = new BufferedReader(new InputStreamReader(is));
				sout = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				System.err.println("Fehler beim Socket-Stream öffnen: " + e);
				// Wenn im "try"-Block Fehler auftreten, dann Socket schließen:
				if (socket != null)
					socket.close();
				System.err.println("Socket geschlossen");
				System.exit(0);
			}
			
			// Verbindung erfolgreich hergestellt: IP-Adresse und Port ausgeben
			System.err.println("Verbunden: " + socket.getInetAddress() + ":"
					+ socket.getPort());	

			// Begrüßungsmeldung vom Server lesen
			String message = sin.readLine();
			System.out.println(message);
		}
		
		/**
		 * Methode, die eine Liste aller im Bestand befindlichen Artikel zurückgibt.
		 * 
		 * @return Liste aller Artikel im Bestand der Bibliothek
		 */
		public List<Artikel> gibAlleArtikel() {
			List<Artikel> liste = new ArrayList<Artikel>();

			// Kennzeichen für gewählte Aktion senden
			sout.println("a");

			// Antwort vom Server lesen und im info-Feld darstellen:
			String antwort = "?";
			try {
				// Anzahl gefundener Artikel einlesen
				antwort = sin.readLine();
				int anzahl = Integer.parseInt(antwort);
				for (int i = 0; i < anzahl; i++) {
					// Artikel vom Server lesen ...
					Artikel artikel = liesArtikelVonServer();
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return null;
			}
			return liste;
		}
		
		public List<Ereignis> gibEreignisListe() {
			List<Ereignis> liste = new ArrayList<Ereignis>();

			// Kennzeichen für gewählte Aktion senden
			sout.println("o");

			// Antwort vom Server lesen und im info-Feld darstellen:
			String antwort = "?";
			try {
				// Anzahl gefundener Artikel einlesen
				antwort = sin.readLine();
				int anzahl = Integer.parseInt(antwort);
				for (int i = 0; i < anzahl; i++) {
					// Artikel vom Server lesen ...
					Ereignis ereignis = liesEreignisVonServer();
					// ... und in Liste eintragen
					liste.add(ereignis);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return null;
			}
			return liste;
		}
		
		public List<Mitarbeiter> gibAlleMitarbeiter() {
			List<Mitarbeiter> liste = new ArrayList<Mitarbeiter>();

			// Kennzeichen für gewählte Aktion senden
			sout.println("b");

			// Antwort vom Server lesen und im info-Feld darstellen:
			String antwort = "?";
			try {
				// Anzahl gefundener Mitarbeiter einlesen
				antwort = sin.readLine();
				int anzahl = Integer.parseInt(antwort);
				for (int i = 0; i < anzahl; i++) {
					// Mitarbeiter vom Server lesen ...
					Mitarbeiter m = (Mitarbeiter) liesMitarbeiterVonServer();
					// ... und in Liste eintragen
					liste.add(m);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return null;
			}
			return liste;
		}
		
		public List<Artikel> warenkorbAnzeigen() {
			List<Artikel> liste = new ArrayList<Artikel>();

			// Kennzeichen für gewählte Aktion senden
			sout.println("m");

			// Antwort vom Server lesen und im info-Feld darstellen:
			String antwort = "?";
			try {
				// Anzahl gefundener Artikel einlesen
				antwort = sin.readLine();
				int anzahl = Integer.parseInt(antwort);
				for (int i = 0; i < anzahl; i++) {
					// Artikel vom Server lesen ...
					Artikel artikel = liesArtikelVonServer();
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return null;
			}
			return liste;
		}

		/**
		 * Methode zum Suchen von Artikeln anhand des Namen. Es wird eine Liste von Artikel
		 * zurückgegeben, die alle Artikel mit exakt übereinstimmendem Namen enthält.
		 * 
		 * @param titel name des gesuchten Artikel
		 * @return Liste der gefundenen Artikel (evtl. leer)
		 */
		public List<Artikel> sucheNachArtikelName(String artikelName) {
			List<Artikel> liste = new ArrayList<Artikel>();
			
			// Kennzeichen für gewählte Aktion senden
			sout.println("f");
			// Parameter für Aktion senden
			sout.println(artikelName);

			// Antwort vom Server lesen und im info-Feld darstellen:
			String antwort = "?";
			try {
				// Anzahl gefundener Artikel einlesen
				antwort = sin.readLine();
				int anzahl = Integer.parseInt(antwort);
				for (int i = 0; i < anzahl; i++) {
					// Artikel vom Server lesen ...
					Artikel artikel = liesArtikelVonServer();
					// ... und in Liste eintragen
					liste.add(artikel);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return null;
			}
			return liste;
		}

		private Artikel liesArtikelVonServer() throws IOException {
			String antwort;
			//Name einlesen
			String name = sin.readLine();
			//Nummer einlesen
			antwort = sin.readLine();
			int nummer = Integer.parseInt(antwort);
			//Bestand einlesen
			antwort = sin.readLine();
			int bestand = Integer.parseInt(antwort);
			//Preis einlesen
			antwort = sin.readLine();
			float preis = Float.parseFloat(antwort);
			
			//Neues Artikel erzeugen
			Artikel artikel = new Artikel(name, nummer, bestand, preis);
			return artikel;
		}
		
		private Ereignis liesEreignisVonServer() throws IOException {
			String antwort;
			//Type einlesen
			String type = sin.readLine();
			//ArtikelNr einlesen
			antwort = sin.readLine();
			int artikelNr = Integer.parseInt(antwort);
			//bestandsAenderung einlesen
			antwort = sin.readLine();
			int bestandsAenderung = Integer.parseInt(antwort);
			//BenutzerNr einlesen
			antwort = sin.readLine();
			int benutzerNr = Integer.parseInt(antwort);
			//BenutzerName einlesen
			String benutzerName = sin.readLine();
			//Datum einlesen
			String datum = sin.readLine();
			
			//Neues Ereignis erzeugen
			Ereignis ereignis = new Ereignis(type, artikelNr, bestandsAenderung, benutzerNr, benutzerName, datum);
			return ereignis;
		}
		
		public Person einloggen(String benuterName, String passwort) throws LoginFehlgeschlagenException{
			sout.println("l");
			// Parameter für Aktion senden
			sout.println(benuterName);
			sout.println(passwort);
			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
				if (antwort.equals("Erfolg")) {
					eingeloggtePerson = liesPersonVonServer();
					// ... und zurückgeben
					return eingeloggtePerson;
				} else {
					// Fehler: Exception (re-)konstruieren
					String message = "Benuter nicht gefunden";
					throw new LoginFehlgeschlagenException(message);
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
				return null;
			}
		}
		
		private Person liesPersonVonServer() throws IOException {
			Person p = null;
			String antwort;
			//Vorname einlesen
			String vorname = sin.readLine();
			//Nachname einlesen
			String nachname = sin.readLine();
			//Nummer einlesen
			antwort = sin.readLine();
			int nummer = Integer.parseInt(antwort);
			//Benutzername einlesen
			String benutzerName = sin.readLine();
			//Passwort einlesen
			String passwort = sin.readLine();
			//Type(Mitarbeiter / Kunde) einlesen
			String type = sin.readLine();
			
			if(type.equals("K")) { //Kunde
				//Adresse einlesen
				String strasse = sin.readLine();
				String hausNr = sin.readLine();
				antwort = sin.readLine();
				int plz = Integer.parseInt(antwort);
				String ort = sin.readLine();
				//Kunde erstellen
				Kunde k = new Kunde(vorname, nachname, nummer, benutzerName, passwort, strasse, hausNr, plz, ort);
				return k;
				
			} else if(type.equals("M")) { //Mitarbeiter
				//Mitarbeiter erstellen
				Mitarbeiter m = new Mitarbeiter(vorname, nachname, nummer, benutzerName, passwort);
				return m;
				
			} else
			return p;
		}
		
		private Mitarbeiter liesMitarbeiterVonServer() throws IOException {
			String antwort;
			//Vorname einlesen
			String vorname = sin.readLine();
			//Nachname einlesen
			String nachname = sin.readLine();
			//Nummer einlesen
			antwort = sin.readLine();
			int nummer = Integer.parseInt(antwort);
			//Benutzername einlesen
			String benutzerName = sin.readLine();
			//Passwort einlesen
			String passwort = sin.readLine();
			//Mitarbeiter erstellen
			Mitarbeiter m = new Mitarbeiter(vorname, nachname, nummer, benutzerName, passwort);
			return m;
		}
		
		public boolean registrieren(Person p) throws BenutzerExistiertBereitsException, IOException {
			if(p instanceof Kunde) {
				Kunde k = (Kunde) p;
				sout.println("r");
				// Parameter für Aktion senden
				sout.println(k.getVorname());
				sout.println(k.getNachname());
				sout.println(k.getBenutzerName());
				sout.println(k.getPassword());
				sout.println(k.getStrasse());
				sout.println(k.getHausNr());
				sout.println(k.getPlz());
				sout.println(k.getOrt());
				
			} else if(p instanceof Mitarbeiter) {
				Mitarbeiter m = (Mitarbeiter) p;
				sout.println("t");
				// Parameter für Aktion senden
				sout.println(m.getVorname());
				sout.println(m.getNachname());
				sout.println(m.getBenutzerName());
				sout.println(m.getPassword());
			}
			
			
			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
				if (antwort.equals("Erfolg")) {
					return true;
				} else {
					throw new BenutzerExistiertBereitsException();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			return false;
		}

		/**
		 * Methode zum Einfügen eines neuen Artikel in den Bestand. 
		 * Wenn das Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
		 * 
		 * @param name des Artikel
		 * @param nummer Nummer des Artikel
		 * @returns Artikel-Objekt, das im Erfolgsfall eingefügt wurde
		 * @throws ArtikelExistiertBereitsException wenn das Artikel bereits existiert
		 */
		public Artikel fuegeArtikelEin(String artikelName, int artikelNummer, int artikelBestand, float artikelPreis) throws ArtikelExistiertBereitsException, IOException {
			// Kennzeichen für gewählte Aktion senden
			sout.println("e");
			// Parameter für Aktion senden
			sout.println(artikelName);
			sout.println(artikelNummer);
			sout.println(artikelBestand);
			sout.println(artikelPreis);

			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
				if (antwort.equals("Erfolg")) {
					// Eingefügtes Artikel vom Server lesen ...
					Artikel artikel = liesArtikelVonServer();
					// ... und zurückgeben
					return artikel;
				} else {
					// Fehler: Exception (re-)konstruieren
					String message = sin.readLine();
					throw new ArtikelExistiertBereitsException(message);
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
				return null;
			}
		}
		public void artikelInWarenkorb(int artikelNummer, int menge) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException {
			// Kennzeichen für gewählte Aktion senden
			sout.println("w");
			sout.println(artikelNummer);
			sout.println(menge);
			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
				if (antwort.equals("Erfolg")) {
					System.out.println("Artike ist in Warenkorb");
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			
		}
		/**
		 * Methode zum Löschen eines Artikel aus dem Bestand. 
		 * Es wird nur das erste Vorkommen des Artikel gelöscht.
		 * 
		 * @param name des Artikel
		 * @param nummer des Artikel
		 */
		public void loescheArtikel(int nummer) throws IOException {
			// Kennzeichen für gewählte Aktion senden
			sout.println("d");
			// Parameter für Aktion senden
			sout.println(nummer);

			// Antwort vom Server lesen:
			String antwort;
			try {
				antwort = sin.readLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return;
			}
			System.out.println(antwort);
		}
		
		public void artikelAusWarenkorbNehmen(int artikelNummer) {
			// Kennzeichen für gewählte Aktion senden
			sout.println("u");
			// Parameter für Aktion senden
			sout.println(artikelNummer);

			// Antwort vom Server lesen:
			String antwort;
			try {
				antwort = sin.readLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return;
			}
			System.out.println(antwort);
		}
		
		public void warenKorbLoeschen() {
			// Kennzeichen für gewählte Aktion senden
			sout.println("c");
			String antwort;
			try {
				antwort = sin.readLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return;
			}
			System.out.println(antwort);
		}
		
		public String warenkorbKaufen() throws IOException, ArtikelExistiertNichtException {
			// Kennzeichen für gewählte Aktion senden
			sout.println("z");
			String antwort;
			String rechnung = "";
			try {
				antwort = sin.readLine();
				int artikelAnzahl = Integer.parseInt(antwort);
				for(int i = 0; i < artikelAnzahl + 5; i++) {
					antwort = sin.readLine();
					rechnung += "\n" + antwort;
					System.out.println(antwort);
				}
				return rechnung;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return "";
			}
		}
		
		//Bestand in Warenkorb ändern
		public boolean bestandImWarenkorbAendern(int nummerAsInt, int mengeAsInt) {
			// Kennzeichen für gewählte Aktion senden
			sout.println("v");
			// Parameter für Aktion senden
			sout.println(nummerAsInt);
			sout.println(mengeAsInt);
			
			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return false;
			}
			System.out.println(antwort);
			if(antwort.equals("Erfolg")) {
				return true;
			} else {
				return false;
			}
		}
		//such nach Artikelnummer
		public Artikel sucheNachNummer(int nummerAlsInt) {
			List<Artikel> alleArtikel = gibAlleArtikel();
			for(Artikel a : alleArtikel) {
				if(a.getNummer() == nummerAlsInt) {
					return a;
				}
			}
			return null;
		}
		/**
		 * Methode zum Speichern des Artikel in einer Datei.
		 * 
		 * @throws IOException
		 */
		public void speichern() {
			// Kennzeichen für gewählte Aktion senden
			sout.println("s");
			// (Parameter sind hier nicht zu senden)

			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return;
			}
			System.out.println(antwort);
		}
		
		public void disconnect() throws IOException {
			// Kennzeichen für gewählte Aktion senden
			sout.println("q");
			// (Parameter sind hier nicht zu senden)

			// Antwort vom Server lesen:
			String antwort = "Fehler";
			try {
				antwort = sin.readLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return;
			}
			System.out.println(antwort);
		}

		public Person eingeloggtePerson() {
			return eingeloggtePerson;
		}
		
		public void ausloggen() {
			sout.println("x");
			try {
				sin.readLine();
				eingeloggtePerson = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public int artikelMenge() {return 0;}
		public void schreibePerson() throws IOException {}
		public void schreibeEreignis() throws IOException {}
		public void schreibeArtikel() throws IOException {}

		@Override
		public void aendereBestand(int nummer, int menge) {
			// TODO Auto-generated method stub
			
		}

	}