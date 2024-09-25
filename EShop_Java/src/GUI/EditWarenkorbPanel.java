package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import Domain.EShop;
import Exceptions.ArtikelBestandZuWenigException;
import Exceptions.ArtikelExistiertNichtException;
import Server.EShopInterface;

//Wichtig: Das AddBookPanel _ist ein_ Panel und damit auch eine Component;
//es kann daher in das Layout eines anderen Containers
//(in unserer Anwendung des Frames) eingef�gt werden.
public class EditWarenkorbPanel extends JPanel {

  // Ueber dieses Interface uebermittelt das AddArtikelPanel
  // ein neu hinzugefuegter Artikel an einen Empfaenger.
  // In unserem Fall ist der Empfaenger die EshopGui mit Komponenten,
  // die dieses Interface implementiert und auf ein neuen hinzugefuegten
  // Artikel reagiert, indem sie die Artikelliste aktualisiert.
  public interface EditWarenkorbListener {
      public void onWarenkorbEdited();
      public void onCheckOut();
  }


  private EShopInterface eShop = null;
  private EditWarenkorbListener editListener = null;


  private JButton editButton;
  private JButton btnCheckOut;
  private JTextField numberTextField = null;
  private JSpinner mengeTextField = null;
  private JLabel mengeHelpLabel = null;

  public EditWarenkorbPanel() {
      setupUI();

      setupEvents();
  }

  public void initialize(EShopInterface eShop2, EditWarenkorbListener listener) {
	  this.eShop = eShop2;
      editListener = listener;
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
      this.add(mengeTextField);

      mengeHelpLabel = new JLabel("");
      this.add(mengeHelpLabel); // Abstandshalter

      editButton = new JButton("Bearbeiten");
      this.add(editButton);

      // Mit leeren Labels als Platzhalter auffuellen,
      // damit die einzelnen Zellen nicht uebermaessig gross werden
      // (bei GridLayout sind alle Zellen gleich gross!).

      for (int i=6; i<anzahlZeilen; i++) {
          this.add(new JLabel());

      }

      // Rahmen definieren
      setBorder(BorderFactory.createTitledBorder("Bearbeiten"));

      add(new JLabel());

      btnCheckOut = new JButton("Check Out");
      add(btnCheckOut);
  }

  private void setupEvents() {
      editButton.addActionListener(
              new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent ae) {
                      System.out.println("Event: " + ae.getActionCommand());
                      // TODO: Artikel in Warenkorb �ndern
                      try {
                          bestandImWarenkorbaendern();
                      } catch (IOException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                      } catch (ArtikelExistiertNichtException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ArtikelBestandZuWenigException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                  }
              });

      btnCheckOut.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
              try {
                  warenkorbKaufen();
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          }
      });
      mengeTextField.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent arg0) {
              //mengeHelpLabel.setText("Um Bestand zu Verringern, Einfach eine Negative Zahl eingeben.");
          }
          @Override
          public void mouseExited(MouseEvent e) {
              mengeHelpLabel.setText("");
          }
      });
  }
  private void bestandImWarenkorbaendern() throws IOException, ArtikelExistiertNichtException, ArtikelBestandZuWenigException {
      String nummer = numberTextField.getText();
      String menge = mengeTextField.getValue().toString();
      if(!nummer.isEmpty() && !menge.isEmpty()) {
          try {
              int nummerAsInt = Integer.parseInt(nummer);
              int mengeAsInt = Integer.parseInt(menge);
              if (mengeAsInt <= 0) {
            	  JOptionPane.showMessageDialog(this, "Bitte Menge > 0 angeben" , "Error", JOptionPane.ERROR_MESSAGE);
              	return;
              }
              boolean bestandAenderung = eShop.bestandImWarenkorbAendern(nummerAsInt, mengeAsInt);
              if(bestandAenderung) {
            	  JOptionPane.showMessageDialog(this, "Artikelmenge in Warenkorb wurde geaendert");
              } else {
            	  JOptionPane.showMessageDialog(this, "Artikel in Warenkorb nicht gefunden oder der Bestand des Artikels in Shop ist zu wenig" , "Error", JOptionPane.ERROR_MESSAGE);
              }
              editListener.onWarenkorbEdited();
          }catch(NumberFormatException nfe) {
        	  System.out.println("Bitte nummern benutzen!");
          }
          catch(ArtikelBestandZuWenigException e) {
        	  System.out.println("Der Bestand des Artikels in Shop ist zu wenig");
          }
      } else {
    	  JOptionPane.showMessageDialog(this, "Bitte Nummer und Menge angeben" , "Error", JOptionPane.ERROR_MESSAGE);
      }
  }
  private void warenkorbKaufen() throws IOException{
	if(eShop.warenkorbAnzeigen().size() > 0) {
		try {
			String kaufen = "Rechnung: \n" + eShop.warenkorbKaufen();
			System.out.println(kaufen);
			JOptionPane.showMessageDialog(this, kaufen);
		} catch (ArtikelExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	      editListener.onCheckOut();
	  } else {
		  JOptionPane.showMessageDialog(this, "Warenkorb ist leer");
	  }
  }
}
