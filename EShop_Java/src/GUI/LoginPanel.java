package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Domain.EShop;
import Exceptions.AlleFelderAusfuellenException;
import Exceptions.LoginFehlgeschlagenException;
import Server.EShopInterface;

public class LoginPanel extends JPanel {

    public interface LoginListener {
        public void OnLogin(EShopInterface eShop);
    }

    private EShopInterface eShop = null;
    private LoginListener loginListener = null;

    private JButton btnLogin;

    private JTextField txtName;
    private JPasswordField pwdPasswort;




    /**
     * Create the panel.
     */
    public LoginPanel() {
        setupUI();
        setupEvents();
    }
    public void initialize(EShopInterface eShop2, LoginListener listener) {
    	this.eShop = eShop2;
        loginListener = listener;
    }
    private void setupUI() {

        setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWeights = new double[]{0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
        setLayout(gridBagLayout);

        JLabel lblName = new JLabel("Name:");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        add(lblName, gbc_lblName);

        txtName = new JTextField();
        GridBagConstraints gbc_txtName = new GridBagConstraints();
        gbc_txtName.insets = new Insets(0, 0, 5, 5);
        gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtName.gridx = 1;
        gbc_txtName.gridy = 0;
        add(txtName, gbc_txtName);
        txtName.setColumns(20);

        JLabel lblPasswort = new JLabel("Passwort:");
        GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
        gbc_lblPasswort.anchor = GridBagConstraints.EAST;
        gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswort.gridx = 0;
        gbc_lblPasswort.gridy = 2;
        add(lblPasswort, gbc_lblPasswort);

        pwdPasswort = new JPasswordField();
        GridBagConstraints gbc_pwdPasswort = new GridBagConstraints();
        gbc_pwdPasswort.insets = new Insets(0, 0, 5, 5);
        gbc_pwdPasswort.fill = GridBagConstraints.HORIZONTAL;
        gbc_pwdPasswort.gridx = 1;
        gbc_pwdPasswort.gridy = 2;
        add(pwdPasswort, gbc_pwdPasswort);

        btnLogin = new JButton("Login");
        GridBagConstraints gbc_btnLogin = new GridBagConstraints();
        gbc_btnLogin.anchor = GridBagConstraints.WEST;
        gbc_btnLogin.gridwidth = 1;
        gbc_btnLogin.gridx = 1;
        gbc_btnLogin.gridy = 4;
        add(btnLogin, gbc_btnLogin);
    }
    private void setupEvents() {
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    login();
                } catch (IOException | AlleFelderAusfuellenException e) {

                }
            }
        });
        pwdPasswort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        login();
                    } catch (IOException | AlleFelderAusfuellenException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        login();
                    } catch (IOException | AlleFelderAusfuellenException e) {

                    }
                }
            }
        });
    }
    private void login() throws IOException, AlleFelderAusfuellenException {
        String name = txtName.getText();
        String passwort = String.valueOf(pwdPasswort.getPassword());

        if(name.isEmpty() || passwort.isEmpty()) {

            txtName.setText("");
            pwdPasswort.setText("");

            throw new AlleFelderAusfuellenException("Bitte alle Felder ausfuellen!");

        }else {
            try {
            	eShop.einloggen(name, passwort);
                loginListener.OnLogin(eShop);
            } catch (LoginFehlgeschlagenException e) {
            	JOptionPane.showMessageDialog(this, "Benutzer nicht gefunden", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
