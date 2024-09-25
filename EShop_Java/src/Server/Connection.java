package Server;
import java.io.IOException;
import java.net.*;
import java.io.*;

// Die Verbindung mit dem Client wird �ber ein eigenes Thread-Objekt aufrecht erhalten 
// (Thread l�uft autonom vom Server-Objekt weiter)

class Connection extends Thread {
    protected Socket         client;
    protected BufferedReader in;
    protected PrintStream    out;
    private EShopInterface 		 eD;

    // 	I/O-Streams initialisieren und Connection-Obj. als Thread starten:
    public Connection(Socket client_socket, EShopInterface d) {
    	eD = d;
    	client = client_socket;
        try { 
     		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out   = new PrintStream(client.getOutputStream());
        }
        catch (IOException e) {
            try {client.close(); } catch (IOException e2){ ;}
            System.err.println("-->S: Ausnahme bei Bereitstellung Sockets: " + e);
            return;
        }
        this.start();  // Thread starten...
    }
    //in der run() Methode des Verbindungsobjektes wird das I/O mit dem Client abgewickelt(das Protokoll der Verbindung)
    public void run() {
          String a=" "; // Aktion-String

    	  System.out.println("Server hat eine Verbindung");  // auf Konsole!

          // erste Nachricht an den Client:
          out.println( "hier ist ihr Server. Geben sie was ein...");

          do {	// Beginn der Benutzerinteraktion:
    		// Aktion vom Client einlesen [ggf. weitere Daten einlesen ...]
      		try {	a=in.readLine();
    		} catch (Exception e) {}

    		// Eingabe-Menue bearbeiten:
    		// die einzige Eingabe ist: "ein text..." oder "q"
      		if (a.equals("q")){		// Client will Ende
      			out.println("#");	// Client beenden			
      		} else { 	
 // HIER SERVER DIENSTE nach a von Dienstobjekt eD AUFRUFEN:
      			try {
					//out.println(eD.echo(a));
				} catch (Exception e) {}
 // ENDE SERVER DIENST
      		}
      		} while (!(a.equals("q")));
      	    
      		
      		System.out.println( "Verbindung durch Client abgebrochen");

      	    try {client.close();
      	    } catch (IOException e2) {}
    } // run
}  // class

    