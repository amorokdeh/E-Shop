package Domain;
import Datenstrukturen.Person;
import Exceptions.BenutzerExistiertBereitsException;
import Exceptions.LoginFehlgeschlagenException;
import Persistence.*;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.io.IOException;

public class PersonManager {
	
	List<Person> nutzer = new Vector();
	
    private PersistenceManager pm = new FilePersistenceManager();
    
    
    
    /**
     * Liest Daten aus Datei ueber PersistenzManager.
     * Fuegt Person im Nutzer ein.
     * @param datei
     * @throws IOException
     * @throws BenutzerExistiertBereitsException
     */
    public void liesDaten(String datei) throws IOException {
	        pm.openForReading(datei);
	        Person einNutzer;
	        do {
	            einNutzer = pm.ladePerson();
	            if (einNutzer != null) {
	                try {
	                    einfuegen(einNutzer);
	                } catch (BenutzerExistiertBereitsException e) {
	                	System.out.println(e.getMessage());

	                }
	            }
	        } while (einNutzer != null);

	        pm.close();
	    }
	    
    /**
     * Schreibt Daten in Datei ueber PersistenzManager.
     * @param datei
     * @throws IOException
     */
	    public void schreibePerson(String datei) throws IOException {
	        pm.openForWriting(datei);
	        if (!nutzer.isEmpty()) {
	            Iterator<Person> iter = nutzer.iterator();
	            while (iter.hasNext()) {
	                Person p = iter.next();
	                pm.speicherePerson(p);
	            }
	        }
	        pm.close();
	    }
    
    
    /**
     * p person  in Liste nutzer einfuegen.
     * @param p
     * @throws BenutzerExistiertBereitsException
     */
    
	public void einfuegen(Person p) throws BenutzerExistiertBereitsException {
	   if ( nutzer.contains(p)) {
            throw new BenutzerExistiertBereitsException();
        }
        nutzer.add(p);
		
	}
	
	 /**
     * Fuegt person in nutzer hinzu, wenn noch nicht vorhanden.
     * @param p
     * @throws BenutzerExistiertBereitsException
     */
    public boolean registrieren(Person p) throws BenutzerExistiertBereitsException{
    	boolean existiert = false;
        for (int i = 0; i < nutzer.size(); i++){
        	if (p.getBenutzerName().equals(nutzer.get(i).getBenutzerName())) {
        		existiert = true;
        		//throw new BenutzerExistiertBereitsException();
        		return false;
        	}
        }
        
		nutzer.add(p);
		p.setNummer(naechsteFreieNummer());
		return true;
    }
    
    /**
     * Gibt naechste freie Nutzer Nr.
     * @return nutzer.size()+1
     */
    private int naechsteFreieNummer(){
        return nutzer.size() + 1;
    }
    
	  
	  /**
	     * Benutzer einloggen.
	     * @param benutzerName
	     * @param passwort
	     * @return Person p
	     * @throws LoginFehlgeschlagenException
	     */
	    public Person einloggen(String benutzerName, String password) throws LoginFehlgeschlagenException {
	        Person p = sucheNachBenutzerName(benutzerName);
	        if (p.getPassword().equals(password)) {
	            return p;
	        }
	        throw new LoginFehlgeschlagenException("Benutzer Name oder Passwort ist ungültig! ");
	    }

	    /**
	     * Sucht benutzerName in nutzer
	     * @param benutzerName
	     * @return Person p
	     * @throws LoginFehlgeschlagenException
	     */
	    private Person sucheNachBenutzerName(String benutzerName) throws LoginFehlgeschlagenException {

	        for (Person p : nutzer) {
	            if (p.getBenutzerName().equals(benutzerName)) {
	                return p;
	            }
	        }
	        throw new LoginFehlgeschlagenException("Nutzer mit Benutzername: " + benutzerName + " wurde nicht gefunden.");
	    }
	
}
