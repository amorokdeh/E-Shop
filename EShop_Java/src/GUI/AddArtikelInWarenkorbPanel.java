package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import Datenstrukturen.Artikel;
import Domain.EShop;
import Exceptions.ArtikelBestandZuWenigException;
import Exceptions.ArtikelExistiertNichtException;
import Server.EShopInterface;

public class AddArtikelInWarenkorbPanel extends JPanel {

    public interface AddArtikelInWarenkorbListener {
        public void onArtikelAdded(Artikel artikel);
    }
 

    private EShopInterface eShop = null;
    private AddArtikelInWarenkorbListener addListener = null;

    public void setNumberTextFieldText(String numberTextField) {
        this.numberTextField.setText(numberTextField);
    }

    private JButton addButton;
    private JTextField numberTextField = null;
    private JSpinner mengeTextField = null;
    private ShowArtikelTablePanel artikelPanel;

    public AddArtikelInWarenkorbPanel() {
        setupUI();

        setupEvents();
    }

    public void initialize(EShopInterface eShop2, AddArtikelInWarenkorbListener listener) {
        this.eShop = eShop2;
        addListener = listener;
    }

    private void setupUI() {
        // GridLayout (nicht zu verwechseln mit GridBagLayout!)
        int anzahlZeilen = 12; //fuer leere Labels als Abstandshalter
        this.setLayout(new GridLayout(anzahlZeilen, 1));

        this.add(new JLabel("Nummer:"));
        numberTextField = new JTextField(10);
        this.add(numberTextField);

        this.add(new JLabel("Menge:"));
        mengeTextField = new JSpinner();
        mengeTextField.setValue(1);
        this.add(mengeTextField);

        this.add(new JLabel()); // Abstandshalter
        addButton = new JButton("Einfuegen");
        this.add(addButton);

        // Mit leeren Labels als Platzhalter auff�llen,
        // damit die einzelnen Zellen nicht �berm��ig gro� werden
        // (bei GridLayout sind alle Zellen gleich gro�!).

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
                        // TODO: Artikel in Warenkorb hinzuf�gen
                        try {
                            if(artikelInWarenkorbEinfuegen()) {
                            	JOptionPane.showMessageDialog(null, "Artikel wurde in den Warenkorb gelegt");
                            } else {
                            	JOptionPane.showMessageDialog(null, "Bitte eine Zahl als Nummer und Menge > 0 oder Menge < Artikelbestand angeben!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
    }
    private boolean artikelInWarenkorbEinfuegen() throws IOException {
        String nummer = numberTextField.getText();
        String menge = mengeTextField.getValue().toString();
        if(!nummer.isEmpty() && !menge.isEmpty()) {
            try {
                int nummerAlsInt = Integer.parseInt(nummer);
                int mengeAlsInt = Integer.parseInt(menge);
                Artikel artikel = eShop.sucheNachNummer(nummerAlsInt);
                if ( (mengeAlsInt <= 0) || (mengeAlsInt > artikel.getBestand()) ) {
                	return false;
                }
                eShop.artikelInWarenkorb(nummerAlsInt, mengeAlsInt);
                
                addListener.onArtikelAdded(eShop.sucheNachNummer(nummerAlsInt));
                numberTextField.setText("");
                mengeTextField.setValue(0);
                return true;
            }catch(NumberFormatException nfe) {
            
            } catch (ArtikelExistiertNichtException e) {
            
            } catch (ArtikelBestandZuWenigException e) {
            
            }
        }
        return false;
    }
}