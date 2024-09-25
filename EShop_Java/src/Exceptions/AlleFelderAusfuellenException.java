package Exceptions;

import javax.swing.JOptionPane;

public class AlleFelderAusfuellenException extends EShopException{
	/**
    *
    */
   private static final long serialVersionUID = 1L;


   public AlleFelderAusfuellenException(String zusatzMsg) {
	   super(zusatzMsg);

       JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfuellen!");

   }
}
