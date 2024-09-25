package GUI;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import Datenstrukturen.Artikel;
import Datenstrukturen.Kunde;
import Datenstrukturen.Mitarbeiter;
import Domain.EShop;
import Exceptions.ArtikelExistiertBereitsException;
import Exceptions.BenutzerExistiertBereitsException;
import Server.EShopInterface;

public class AddArtikelPanel extends JPanel {

    public interface AddArtikelListener {
        public void onArtikelAdded (Artikel artikel);
        public void update ();
    }


    private EShopInterface eShop = null;
    private AddArtikelListener addListener = null;


    private JButton addButton;
    private JButton deleteButton;
    private JButton addMit;
    //private JTextField numberTextField = null;
    private JTextField titleTextField = null;
    //private JTextField markeTextField = null;
    private JTextField preisTextField = null;
    private JTextField bestandTextField = null;
    //private JTextField massengutTextField = null;
    private JTextField textField;
    private JTextField vornameTextField;
    private JTextField nachnameTextField;
    private JTextField benutzernummerTextField;
    private JTextField passTextField;
    

    public AddArtikelPanel() {
        setupUI();

        setupEvents();
    }

    public void initialize(EShopInterface eShop2, AddArtikelListener listener) {
        this.eShop = eShop2;
        addListener = listener;
    }

    private void setupUI() {
        int anzahlZeilen = 12;
        setLayout(new GridLayout(0, 2, 0, 0));
        //JLabel label_4 = new JLabel("Nummer:");
        //this.add(label_4);
        //numberTextField = new JTextField();
        //this.add(numberTextField);
        JLabel label_2 = new JLabel("Name:");
        this.add(label_2);
        titleTextField = new JTextField();
        this.add(titleTextField);
        JLabel lblPreis = new JLabel("Preis:");
        this.add(lblPreis);
        preisTextField = new JTextField();
        this.add(preisTextField);
        JLabel lblBestand = new JLabel("Bestand:");
        this.add(lblBestand);
        bestandTextField = new JTextField();
        this.add(bestandTextField);
        //JLabel lblMassengut = new JLabel("Massengut:");
        //this.add(lblMassengut);
        //massengutTextField = new JTextField();
        //this.add(massengutTextField);
        addButton = new JButton("Einfuegen");
        this.add(addButton);

        JLabel label_1 = new JLabel("");
        add(label_1);

        JSeparator separator = new JSeparator();
        add(separator);

        JSeparator separator_1 = new JSeparator();
        add(separator_1);

        JLabel lblLschen = new JLabel("L\u00F6schen");
        add(lblLschen);
        Font font = new Font("Arial", Font.BOLD,12);

        JLabel label_5 = new JLabel("");
        add(label_5);

        JLabel lblNummer = new JLabel("Nummer:");
        add(lblNummer);

        textField = new JTextField();
        add(textField);
        textField.setColumns(10);

        deleteButton = new JButton("Loeschen");
        add(deleteButton);

        JLabel label_6 = new JLabel("");
        add(label_6);
        
        lblLschen.setFont(font);
        
        //Mitarbeiter
        JSeparator separator2 = new JSeparator();
        add(separator2);
             
        JSeparator separator_2 = new JSeparator();
        add(separator_2);
        JLabel lblmit = new JLabel("Mitarbeiter Hinzufuegen");
        lblmit.setFont(font);
        add(lblmit);
        JLabel label_newmit2 = new JLabel("");
        add(label_newmit2);
        
        JLabel label_mitVorname = new JLabel("Vorname:");
        this.add(label_mitVorname);
        vornameTextField = new JTextField();
        this.add(vornameTextField);
        JLabel label_mitNachname = new JLabel("Nachname:");
        this.add(label_mitNachname);
        nachnameTextField = new JTextField();
        this.add(nachnameTextField);
        JLabel lblmitBenutzername = new JLabel("Benutzername:");
        this.add(lblmitBenutzername);
        benutzernummerTextField = new JTextField();
        this.add(benutzernummerTextField);
        JLabel lblPass = new JLabel("Passwort:");
        this.add(lblPass);
        passTextField = new JTextField();
        this.add(passTextField);
        
        addMit = new JButton("Hinzufuegen");
        add(addMit);
        
        JLabel label77 = new JLabel("");
        add(label77);
        
        JSeparator separator3 = new JSeparator();
        add(separator3);
             
        JSeparator separator_3 = new JSeparator();
        add(separator_3);

        for (int i=6; i<anzahlZeilen; i++) {
            this.add(new JLabel());

        }

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Einfuegen"));
    }

    private void setupEvents() {
        addButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("Event: " + ae.getActionCommand());
                        try {
                            artikelEinfuegen();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
        addMit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("Event: " + ae.getActionCommand());
                        try {
                            mitarbeiterEinfuegen();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
        deleteButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        try {
                            artikelLoeschen();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                });
    }

    private void artikelEinfuegen() throws IOException {
    	
        String name = titleTextField.getText();
        String preis = preisTextField.getText();
        String bestand = bestandTextField.getText();

        if (!name.isEmpty() && !preis.isEmpty() && !bestand.isEmpty()) {
            try {
                float preisAlsFloat = Float.parseFloat(preis);
                int bestandAlsInt = Integer.parseInt(bestand);
                
                int nummer = eShop.artikelMenge() + 1;
                Artikel artikel = eShop.fuegeArtikelEin(name, nummer, bestandAlsInt, preisAlsFloat);
                eShop.schreibeArtikel();
                JOptionPane.showMessageDialog(this, "Artikel wurde eingefuegt");
                
                titleTextField.setText("");
                preisTextField.setText("");
                bestandTextField.setText("");
                

                // Am Ende Listener, d.h. unseren Frame benachrichtigen:
                addListener.onArtikelAdded(artikel);
            } catch (NumberFormatException nfe) {
                System.err.println("Bitte eine Zahl eingeben.");
            } catch (ArtikelExistiertBereitsException bebe) {
                System.err.println(bebe.getMessage());
            }
        }else {

            JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfuellen!");
            //numberTextField.setText("");
            titleTextField.setText("");
            preisTextField.setText("");
            bestandTextField.setText("");
            //massengutTextField.setText("");
        }
    }
    
    private void mitarbeiterEinfuegen() throws IOException {
    	//String nummer = numberTextField.getText();
        String vorName = vornameTextField.getText();
        String nachName = nachnameTextField.getText();
        String benutzername = benutzernummerTextField.getText();
        String pass = passTextField.getText();
        //String massengut = massengutTextField.getText();

        if (!vorName.isEmpty() && !nachName.isEmpty() && !benutzername.isEmpty() && !pass.isEmpty()) {
        	try {
        		Mitarbeiter m = new Mitarbeiter(vorName, nachName, 0, benutzername, pass);
                boolean registriert = eShop.registrieren(m);
                eShop.schreibePerson();
                addListener.update();
                if(registriert) {
                	JOptionPane.showMessageDialog(this, "Mitarbeiter wurde registriert");
                } else {
                	JOptionPane.showMessageDialog(this, "Mitarbeiter wurde nicht registriert. Benutzername existiert bereits", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (BenutzerExistiertBereitsException e) {
            	JOptionPane.showMessageDialog(this, "Mitarbeiter wurde nicht registriert. Benutzername existiert bereits", "Error", JOptionPane.ERROR_MESSAGE);
                //e.printStackTrace();
            }
        }else {

            JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfuellen!");
            //numberTextField.setText("");
            titleTextField.setText("");
            preisTextField.setText("");
            bestandTextField.setText("");
            //massengutTextField.setText("");
        }
    }

    private void artikelLoeschen() throws IOException {
        String nummerString = textField.getText();
        int nummerAlsInt = Integer.parseInt(nummerString);

        eShop.loescheArtikel(nummerAlsInt);
        textField.setText("");

        addListener.onArtikelAdded(eShop.sucheNachNummer(nummerAlsInt));
    }

}
