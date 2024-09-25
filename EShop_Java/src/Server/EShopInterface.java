package Server;

import java.io.IOException;
import java.util.List;

import Datenstrukturen.Artikel;
import Datenstrukturen.Ereignis;
import Datenstrukturen.Mitarbeiter;
import Datenstrukturen.Person;
import Exceptions.ArtikelBestandZuWenigException;
import Exceptions.ArtikelExistiertBereitsException;
import Exceptions.ArtikelExistiertNichtException;
import Exceptions.BenutzerExistiertBereitsException;
import Exceptions.LoginFehlgeschlagenException;


public interface EShopInterface {

	public abstract List<Artikel> gibAlleArtikel();

	public abstract List<Artikel> sucheNachArtikelName(String artikelName);

	public abstract Artikel fuegeArtikelEin(String artikelName, int artikelNummer, int artikelBestand, float artikelPreis) throws ArtikelExistiertBereitsException, IOException;

	public abstract void disconnect() throws IOException;

	public abstract Person eingeloggtePerson();

	public abstract void schreibePerson() throws IOException;

	public abstract void schreibeEreignis() throws IOException;
	
	public abstract void schreibeArtikel() throws IOException;

	public abstract boolean registrieren(Person p) throws BenutzerExistiertBereitsException, IOException;
	
	public abstract Person einloggen(String benutzerName, String passwort) throws LoginFehlgeschlagenException;

	public abstract void ausloggen();

	public abstract void artikelInWarenkorb(int artikelNummer, int menge) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException;

	public abstract List<Artikel> warenkorbAnzeigen();

	public abstract void artikelAusWarenkorbNehmen(int artikelNummer);

	public void loescheArtikel(int nummer) throws IOException;
	
	public abstract void warenKorbLoeschen();
	
	public abstract String warenkorbKaufen() throws IOException, ArtikelExistiertNichtException;
	
	public abstract void speichern();

	public abstract Artikel sucheNachNummer(int nummerAlsInt);

	public abstract boolean bestandImWarenkorbAendern(int nummerAsInt, int mengeAsInt) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException;

	public abstract int artikelMenge();

	public abstract List<Ereignis> gibEreignisListe();

	public abstract void aendereBestand(int nummer, int menge);
	public abstract List<Mitarbeiter> gibAlleMitarbeiter();

}
