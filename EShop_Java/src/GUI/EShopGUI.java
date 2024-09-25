package GUI;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import Datenstrukturen.Kunde;
import Datenstrukturen.Mitarbeiter;
import Datenstrukturen.Person;
import Domain.EShop;
import GUI.LoginPanel.LoginListener;
import Server.EShopFassade;
import Server.EShopInterface;

//author Mohamad und Amr 
public class EShopGUI extends JFrame implements LoginListener {
	
	public static final int DEFAULT_PORT = 6789;
    private EShopInterface eShop;
	
    //private Person p;
    private LoginPanel loginPanel;

    public EShopGUI(String titel) {
        super(titel);

        try {
        	eShop = new EShop("SHOP");
            initializeLogin();
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public EShopGUI(String titel, String host, int port) throws IOException {
        super(titel);

        eShop = new EShopFassade(host, port);
        initializeLogin();
    }

    private void initializeLogin() {
        //Menu definieren
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.NORTH);
        loginPanel = new LoginPanel();
        tabbedPane.addTab("Login", null, loginPanel, null);

        JPanel registrationPanel = new RegistrationPanel(eShop);
        tabbedPane.addTab("Registrieren", null, registrationPanel, null);
        loginPanel.initialize(eShop, this);

        pack();
        setLocationRelativeTo(null);

        this.setVisible(true);



    }

    private void initializeArbeiter() {
        //this.remove(loginPanel);
        this.dispose();

        ArbeiterFrame frame = new ArbeiterFrame(eShop);
        frame.setVisible(true);
    }

    private void initializeKunde() {
        //this.remove(loginPanel);
        this.dispose();

        KundenFrame frame = new KundenFrame(eShop);
        frame.setVisible(true);
    }

    public void OnLogin(EShopInterface eShop){
        if(eShop.eingeloggtePerson() instanceof Mitarbeiter) {
            initializeArbeiter();
        }else if(eShop.eingeloggtePerson() instanceof Kunde) {
            initializeKunde();
        }
    }

    public static void main(String[] args) {
        int portArg = 0;
        String hostArg = null;
        InetAddress ia = null;

        // ---
        // Hier werden die main-Parameter geprüft:
        // ---

        // Host- und Port-Argument einlesen, wenn angegeben
        if (args.length > 2) {
            System.out.println("Aufruf: java <Klassenname> [<hostname> [<port>]]");
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
                hostArg = ia.getHostName(); // host ist lokale Maschine
                portArg = DEFAULT_PORT;
                break;
            case 1:
                portArg = DEFAULT_PORT;
                hostArg = args[0];
                break;
            case 2:
                hostArg = args[0];
                try {
                    portArg = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Aufruf: java BibClientGUI [<hostname> [<port>]]");
                    System.exit(0);
                }
        }
        // Swing-UI auf dem GUI-Thread initialisieren
        // (host und port müssen für Verwendung in inner class final sein)
        final String host = hostArg;
        final int port = portArg;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
            		EShopGUI gui = new EShopGUI("Eshop", host, port);
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
            }
        });
    }

}
