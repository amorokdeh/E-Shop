package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Domain.EShop;
import Server.EShopInterface;

public class DeleteArtikelInWarenkorb extends JPanel {

    public interface DeleteArtikelInWarenkorbListener {
        public void onArtikelRemoved();
    }

    private EShopInterface eShop = null;
    private DeleteArtikelInWarenkorbListener deleteListener = null;

    private JTextField txtNummer;
    private JButton btnWarenkorbLeeren;
    JButton btnArtikelLoeschen;


    public DeleteArtikelInWarenkorb() {
        setupUI();

        setupEvent();
    }

    public void initialize(EShopInterface eShop2, DeleteArtikelInWarenkorbListener listener) {

    	this.eShop = eShop2;
        deleteListener = listener;
    }

    private void setupUI() {
        setBorder(new TitledBorder(null, "L\u00F6schen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        int anzahlZeilen = 12; //f�r leere Labels als Abstandshalter
        this.setLayout(new GridLayout(anzahlZeilen, 1));

        JLabel lblNummer = new JLabel("Nummer:");
        add(lblNummer);

        txtNummer = new JTextField();
        add(txtNummer);
        txtNummer.setColumns(10);

        add(new JLabel());

        btnArtikelLoeschen = new JButton("Artikel Loeschen");

        add(btnArtikelLoeschen);

        add(new JLabel());

        btnWarenkorbLeeren = new JButton("Warenkorb leeren");
        add(btnWarenkorbLeeren);

    }

    private void setupEvent() {
        btnArtikelLoeschen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                artikelAusWarenkorbEntfernen();
            }
        });

        btnWarenkorbLeeren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                warenkorbLeeren();
            }
        });
    }

    private void artikelAusWarenkorbEntfernen() {
        String nummer = txtNummer.getText();
        if(!nummer.isEmpty()) {
            try {
                int nummerAsInt = Integer.parseInt(nummer);
                eShop.artikelAusWarenkorbNehmen(nummerAsInt);

                deleteListener.onArtikelRemoved();;
            }catch(NumberFormatException nfe) {
                System.err.println("Nur Zahlen!");
            }
        }
    }

    private void warenkorbLeeren() {
    	eShop.warenKorbLoeschen();

        deleteListener.onArtikelRemoved();
    }


}
