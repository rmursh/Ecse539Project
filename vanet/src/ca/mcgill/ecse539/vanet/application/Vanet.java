package ca.mcgill.ecse539.vanet.application;

public class Vanet {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load model
		// TODO
		
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VanetPage().setVisible(true);
            }
        });
        
	}
}
