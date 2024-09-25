package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Datenstrukturen.Kunde;
import Datenstrukturen.Mitarbeiter;
import Domain.EShop;
import Exceptions.BenutzerExistiertBereitsException;
import Server.EShopInterface;

public class RegistrationPanel extends JPanel {
    private JTextField txtVorname;
    private JTextField txtNachname;
    private JTextField txtNutzername;
    private JTextField txtStrasse;
    private JTextField txtHausNum;
    private JTextField txtPlz;
    private JTextField txtOrt;
    private JPasswordField pwdPasswort;
    private JPasswordField pwdZweitespasswort;
    private JLabel lblStrasse;
    private JLabel lblHausNum;
    private JLabel lblPlz;
    private JLabel lblOrt;

    private JRadioButton rdbtnJa;
    private JRadioButton rdbtnNein;
    private final ButtonGroup BenutzerTyp = new ButtonGroup();
    private JButton btnRegistrieren;

    private EShopInterface eShop;

    /**
     * Create the panel.
     */
    public RegistrationPanel(EShopInterface eShop2) {
    	this.eShop = eShop2;

        setupUI();

        setupEvents();
    }

    private void setupUI() {
        setBorder(new TitledBorder(null, "Registrieren", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowHeights = new int[]{0, -46, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        setLayout(gridBagLayout);

        JLabel lblVorname = new JLabel("Vorname:");
        GridBagConstraints gbc_lblVorname = new GridBagConstraints();
        gbc_lblVorname.insets = new Insets(0, 0, 5, 5);
        gbc_lblVorname.anchor = GridBagConstraints.EAST;
        gbc_lblVorname.gridx = 0;
        gbc_lblVorname.gridy = 0;
        add(lblVorname, gbc_lblVorname);

        txtVorname = new JTextField();
        GridBagConstraints gbc_txtVorname = new GridBagConstraints();
        gbc_txtVorname.insets = new Insets(0, 0, 5, 0);
        gbc_txtVorname.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtVorname.gridx = 1;
        gbc_txtVorname.gridy = 0;
        add(txtVorname, gbc_txtVorname);
        txtVorname.setColumns(40);

        JLabel lblNachname = new JLabel("Nachname:");
        GridBagConstraints gbc_lblNachname = new GridBagConstraints();
        gbc_lblNachname.insets = new Insets(0, 0, 5, 5);
        gbc_lblNachname.anchor = GridBagConstraints.EAST;
        gbc_lblNachname.gridx = 0;
        gbc_lblNachname.gridy = 1;
        add(lblNachname, gbc_lblNachname);

        txtNachname = new JTextField();
        GridBagConstraints gbc_txtNachname = new GridBagConstraints();
        gbc_txtNachname.insets = new Insets(0, 0, 5, 0);
        gbc_txtNachname.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNachname.gridx = 1;
        gbc_txtNachname.gridy = 1;
        add(txtNachname, gbc_txtNachname);
        txtNachname.setColumns(40);

        JLabel lblNutzername = new JLabel("Nutzername:");
        GridBagConstraints gbc_lblNutzername = new GridBagConstraints();
        gbc_lblNutzername.insets = new Insets(0, 0, 5, 5);
        gbc_lblNutzername.anchor = GridBagConstraints.EAST;
        gbc_lblNutzername.gridx = 0;
        gbc_lblNutzername.gridy = 2;
        add(lblNutzername, gbc_lblNutzername);

        txtNutzername = new JTextField();
        GridBagConstraints gbc_txtNutzername = new GridBagConstraints();
        gbc_txtNutzername.insets = new Insets(0, 0, 5, 0);
        gbc_txtNutzername.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNutzername.gridx = 1;
        gbc_txtNutzername.gridy = 2;
        add(txtNutzername, gbc_txtNutzername);
        txtNutzername.setColumns(40);

        JLabel lblPasswort = new JLabel("Passwort:");
        GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
        gbc_lblPasswort.anchor = GridBagConstraints.EAST;
        gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswort.gridx = 0;
        gbc_lblPasswort.gridy = 3;
        add(lblPasswort, gbc_lblPasswort);

        pwdPasswort = new JPasswordField();
        pwdPasswort.setColumns(40);
        GridBagConstraints gbc_pwdPasswort = new GridBagConstraints();
        gbc_pwdPasswort.insets = new Insets(0, 0, 5, 0);
        gbc_pwdPasswort.fill = GridBagConstraints.HORIZONTAL;
        gbc_pwdPasswort.gridx = 1;
        gbc_pwdPasswort.gridy = 3;
        add(pwdPasswort, gbc_pwdPasswort);

        JLabel lblPasswortWiederholen = new JLabel("Passwort wiederholen:");
        GridBagConstraints gbc_lblPasswortWiederholen = new GridBagConstraints();
        gbc_lblPasswortWiederholen.anchor = GridBagConstraints.EAST;
        gbc_lblPasswortWiederholen.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswortWiederholen.gridx = 0;
        gbc_lblPasswortWiederholen.gridy = 4;
        add(lblPasswortWiederholen, gbc_lblPasswortWiederholen);

        pwdZweitespasswort = new JPasswordField();
        pwdZweitespasswort.setColumns(40);
        GridBagConstraints gbc_pwdZweitespasswort = new GridBagConstraints();
        gbc_pwdZweitespasswort.insets = new Insets(0, 0, 5, 0);
        gbc_pwdZweitespasswort.fill = GridBagConstraints.HORIZONTAL;
        gbc_pwdZweitespasswort.gridx = 1;
        gbc_pwdZweitespasswort.gridy = 4;
        add(pwdZweitespasswort, gbc_pwdZweitespasswort);
        
        JLabel lbladresse = new JLabel("Moechtest du Adresse hinzufuegen ?");
        GridBagConstraints gbc_lbladresse = new GridBagConstraints();
        gbc_lbladresse.anchor = GridBagConstraints.EAST;
        gbc_lbladresse.insets = new Insets(25, 5, 5, 5);
        gbc_lbladresse.gridx = 0;
        gbc_lbladresse.gridy = 5;
        add(lbladresse, gbc_lbladresse);
        
        rdbtnJa = new JRadioButton("Ja");
        rdbtnJa.setSelected(true);
        BenutzerTyp.add(rdbtnJa);
        GridBagConstraints gbc_rdbtnKunde = new GridBagConstraints();
        gbc_rdbtnKunde.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnKunde.gridx = 0;
        gbc_rdbtnKunde.gridy = 6;
        add(rdbtnJa, gbc_rdbtnKunde);

        rdbtnNein = new JRadioButton("Nein");
        BenutzerTyp.add(rdbtnNein);
        GridBagConstraints gbc_rdbtnArbeiter = new GridBagConstraints();
        gbc_rdbtnArbeiter.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnArbeiter.gridx = 1;
        gbc_rdbtnArbeiter.gridy = 6;
        add(rdbtnNein, gbc_rdbtnArbeiter);
        
        lblStrasse = new JLabel("Strasse:");
        GridBagConstraints gbc_lblStrasse = new GridBagConstraints();
        gbc_lblStrasse.insets = new Insets(0, 0, 5, 5);
        gbc_lblStrasse.anchor = GridBagConstraints.EAST;
        gbc_lblStrasse.gridx = 0;
        gbc_lblStrasse.gridy = 7;
        add(lblStrasse, gbc_lblStrasse);

        txtStrasse = new JTextField();
        GridBagConstraints gbc_txtStrasse = new GridBagConstraints();
        gbc_txtStrasse.insets = new Insets(0, 0, 5, 0);
        gbc_txtStrasse.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtStrasse.gridx = 1;
        gbc_txtStrasse.gridy = 7;
        add(txtStrasse, gbc_txtStrasse);
        txtStrasse.setColumns(40);
        
        lblHausNum = new JLabel("Hasunummer:");
        GridBagConstraints gbc_lbllblHausNum= new GridBagConstraints();
        gbc_lbllblHausNum.insets = new Insets(0, 0, 5, 5);
        gbc_lbllblHausNum.anchor = GridBagConstraints.EAST;
        gbc_lbllblHausNum.gridx = 0;
        gbc_lbllblHausNum.gridy = 8;
        add(lblHausNum, gbc_lbllblHausNum);

        txtHausNum = new JTextField();
        GridBagConstraints gbc_txtHausnum = new GridBagConstraints();
        gbc_txtHausnum.insets = new Insets(0, 0, 5, 0);
        gbc_txtHausnum.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtHausnum.gridx = 1;
        gbc_txtHausnum.gridy = 8;
        add(txtHausNum, gbc_txtHausnum);
        txtHausNum.setColumns(40);
        
        lblPlz = new JLabel("PLZ:");
        GridBagConstraints gbc_lblPlz= new GridBagConstraints();
        gbc_lblPlz.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlz.anchor = GridBagConstraints.EAST;
        gbc_lblPlz.gridx = 0;
        gbc_lblPlz.gridy = 9;
        add(lblPlz, gbc_lblPlz);

        txtPlz = new JTextField();
        GridBagConstraints gbc_txtPlz = new GridBagConstraints();
        gbc_txtPlz.insets = new Insets(0, 0, 5, 0);
        gbc_txtPlz.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPlz.gridx = 1;
        gbc_txtPlz.gridy = 9;
        add(txtPlz, gbc_txtPlz);
        txtPlz.setColumns(40);
        
        lblOrt = new JLabel("Ort:");
        GridBagConstraints gbc_lblOrt= new GridBagConstraints();
        gbc_lblOrt.insets = new Insets(0, 0, 5, 5);
        gbc_lblOrt.anchor = GridBagConstraints.EAST;
        gbc_lblOrt.gridx = 0;
        gbc_lblOrt.gridy = 10;
        add(lblOrt, gbc_lblOrt);

        txtOrt = new JTextField();
        GridBagConstraints gbc_txtOrt = new GridBagConstraints();
        gbc_txtOrt.insets = new Insets(0, 0, 5, 0);
        gbc_txtOrt.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtOrt.gridx = 1;
        gbc_txtOrt.gridy = 10;
        add(txtOrt, gbc_txtOrt);
        txtOrt.setColumns(40);
        
        btnRegistrieren = new JButton("Registrieren");
        GridBagConstraints gbc_btnRegistrieren = new GridBagConstraints();
        gbc_btnRegistrieren.gridwidth = 2;
        gbc_btnRegistrieren.gridx = 0;
        gbc_btnRegistrieren.gridy = 11;
        add(btnRegistrieren, gbc_btnRegistrieren);
    }

    private void setupEvents() {
    	rdbtnJa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                radioButtonChanged();
            }
        });

    	rdbtnNein.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                radioButtonChanged();
            }
        });

        btnRegistrieren.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                try {
                    registrieren();
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            }
        });

        KeyAdapter registerKey = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        registrieren();
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
                }
            }
        };
        txtVorname.addKeyListener(registerKey);
        txtNachname.addKeyListener(registerKey);
        txtNutzername.addKeyListener(registerKey);
        pwdPasswort.addKeyListener(registerKey);
        pwdZweitespasswort.addKeyListener(registerKey);
        txtStrasse.addKeyListener(registerKey);
        txtHausNum.addKeyListener(registerKey);
        txtPlz.addKeyListener(registerKey);
        txtOrt.addKeyListener(registerKey);
      //pwdMasterpasswort.addKeyListener(registerKey);
    }

    private void radioButtonChanged() {
        if (BenutzerTyp.isSelected(rdbtnJa.getModel())) {
            txtStrasse.setVisible(true);
            lblStrasse.setVisible(true);
            txtHausNum.setVisible(true);
            lblHausNum.setVisible(true);
            txtPlz.setVisible(true);
            lblPlz.setVisible(true);
            txtOrt.setVisible(true);
            lblOrt.setVisible(true);
        }else if (BenutzerTyp.isSelected(rdbtnNein.getModel())) {
        	txtStrasse.setText("");
        	txtStrasse.setVisible(false);
            lblStrasse.setVisible(false);
            
            txtHausNum.setText("");
            txtHausNum.setVisible(false);
            lblHausNum.setVisible(false);
            
            txtPlz.setText("");
            txtPlz.setVisible(false);
            lblPlz.setVisible(false);
            
            txtOrt.setText("");
            txtOrt.setVisible(false);
            lblOrt.setVisible(false);
        }
        
    }

    private void registrieren() throws IOException {
        String vorname = txtVorname.getText();
        String nachname = txtNachname.getText();
        int nummer = 0;
        String benutzername = txtNutzername.getText();
        String passwort = String.valueOf(pwdPasswort.getPassword());
        String zweitPasswort = String.valueOf(pwdZweitespasswort.getPassword());

        if (!vorname.isEmpty() && !passwort.isEmpty() && !zweitPasswort.isEmpty()) {
            if (passwort.equals(zweitPasswort)) {
                if (BenutzerTyp.isSelected(rdbtnNein.getModel())) {

                    try {
                        Kunde k = new Kunde(vorname, nachname, nummer, benutzername, passwort, "", "", 0, "");
                        boolean registriert = eShop.registrieren(k);
                        eShop.schreibePerson();
                        if(registriert) {
                        	JOptionPane.showMessageDialog(this, "Nutzer wurde registriert");
                        } else {
                        	JOptionPane.showMessageDialog(this, "Nutzer wurde nicht registriert. Benutzername existiert bereits", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (BenutzerExistiertBereitsException e) {
                    	JOptionPane.showMessageDialog(this, "Nutzer wurde nicht registriert. Benutzername existiert bereits", "Error", JOptionPane.ERROR_MESSAGE);
                        //e.printStackTrace();
                    }
                    
                }else if (BenutzerTyp.isSelected(rdbtnJa.getModel())) {
                	
                    	String strasse = txtStrasse.getText();
                        String hausNum = txtHausNum.getText();
                        String cityCode = txtPlz.getText();
                        int plz = Integer.parseInt(cityCode);
                        String ort = txtOrt.getText();
                        
                        try {
                            Kunde k = new Kunde(vorname, nachname, nummer, benutzername, passwort, strasse, hausNum, plz, ort);
                            boolean registriert = eShop.registrieren(k);
                            eShop.schreibePerson();
                            if(registriert) {
                            	JOptionPane.showMessageDialog(this, "Nutzer wurde registriert");
                            } else {
                            	JOptionPane.showMessageDialog(this, "Nutzer wurde nicht registriert. Benutzername existiert bereits", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (BenutzerExistiertBereitsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwoerter nicht gleich", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(this, "Bitte etwas eingeben!!", "Error", JOptionPane.INFORMATION_MESSAGE);
            txtNutzername.setText("");
            pwdPasswort.setText("");
            pwdZweitespasswort.setText("");
        }

    }
}
