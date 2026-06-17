package boundary;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.*;

import database.JpaUtil;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

	/**
	 * Il MainFrame come si evince dal nome è il
	 * JFrame unico e pincipale di tutto il programma,
	 * per emulare meglio il funzionamento di un vero
	 * programma abbiamo scelto di implementare il CardLayout che
	 * ci consente di popolare il contentPane del MainFrame con diversi
	 * JPanel a nostro piacimento e a runtime sfruttando il metodo
	 * switchPanel()
	 */

    private CardLayout cl = new CardLayout(0, 0);
    private LoginPanel loginPanel;
    private UseCasesResponsabilePanel respPanel;
    private UseCasesOperatorePanel opPanel;
    private MovimentazioneProdottiPanel movePanel;
    private RicercaProdottiPerFiltroPanel filterPanel;
    private VisualizzaSottoScorta visualUnderPanel;
	private CreaProdottoPanel createPanel;
	private ModificaProdottoPanel editPanel;

    public MainFrame() {

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		checkExit();
	    }
	});

	setTitle("Gestione Magazzino");
	setBounds(100, 100, 1000, 700);
	setMinimumSize(new Dimension(900, 650));
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	setLocationRelativeTo(null);
		URL imgUrl = getClass().getResource("/images/logo.png");
		if (imgUrl != null) {
			Image imgScalata = new ImageIcon(imgUrl).getImage().getScaledInstance(120, -1, Image.SCALE_SMOOTH);
			setIconImage(imgScalata);
		}else{
			System.out.println("Errore nel setting dell'icon del programma!!");
		}

	getContentPane().setLayout(cl);

		/**
		 * Per ogni JPanel da collegare nel MainFrame creiamo
		 * la sua istanza e mediante il metodo .add , passiamo
		 * il panel da aggiungere più una String "chiave" utile a
		 * identificare il modo in cui possiamo switchare tra i vari
		 * Panel a run-time
		 */

	loginPanel = new LoginPanel(this);
	respPanel = new UseCasesResponsabilePanel(this);
	opPanel = new UseCasesOperatorePanel(this);
	movePanel = new MovimentazioneProdottiPanel(this);
	filterPanel = new RicercaProdottiPerFiltroPanel(this);
	visualUnderPanel = new VisualizzaSottoScorta(this);
	createPanel = new CreaProdottoPanel(this);
	editPanel = new ModificaProdottoPanel(this);

	getContentPane().add(loginPanel, "LOGIN_VIEW");
	getContentPane().add(respPanel, "DASH_RESP");
	getContentPane().add(opPanel, "DASH_OP");
	getContentPane().add(movePanel, "MOVE_PROD");
	getContentPane().add(filterPanel, "FILTER_PROD");
	getContentPane().add(visualUnderPanel, "VISUAL_UNDER");
	getContentPane().add(createPanel, "CREATE_PROD");
	getContentPane().add(editPanel, "EDIT_PROD");

		/**
		 * All'avvio del programma , il primo JPanel
		 * mostrato è ovviamente quello di login
		 */

	cl.show(getContentPane(), "LOGIN_VIEW");
    }

	/**
	 * Il metodo checkExit() serve a verificare che
	 * l'Utente voglia effettivamente uscire dal programma
	 * e che non abbia sbagliato a selezionare il comando
	 * di uscita
	 */

    private void checkExit() {
	int choice = JOptionPane.showConfirmDialog(rootPane,
	    "Sei sicuro di voler uscire dall'applicazione?", "Conferma Uscita",
	    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

	if (choice == JOptionPane.YES_OPTION) {
		JpaUtil.getInstance().chiudi();
		JpaUtil.getInstance().chiudi();
	    System.exit(0);
	}
    }

	/**
	 * Per far funzionare correttamente la navigabilità
	 * tra le varie schermate del programma , abbiamo dovuto
	 * aggiungere alcuni metodi particolari che vengono richiamati
	 * a seguito di specifiche azioni da alcuni Panel ,
	 * per esempio siccome sia un utente di tipo Responsabile che di tipo
	 * Operatore può accedere al Panel di ricerca dei prodotti per filtro,
	 * abbiamo implementato il metodo ricercaProdotti() , che consente
	 * di memorizzare se si sta accedendo al panel di ricerca in quanto operatore
	 * o in quanto reponsabile cosi da poter poi successivamente riportare
	 * l'Utente nella dashboard corretta e coerente rispetto ai suoi privilegi
	 *
	 */

    public void ricercaProdotti(String panelDiProvenienza) {
	filterPanel.setPannelloDiProvenienza(panelDiProvenienza);
	switchPanel("FILTER_PROD");
    }

    public void visualizzaSottoScorta(String panelDiProvenienza) {
	visualUnderPanel.setPannelloDiProvenienza(panelDiProvenienza);
	switchPanel("VISUAL_UNDER");
    }

	/**
	 * Metodo generico per cambiare il JPanel che popola il MainFrame
	 * a seguito di una specifica interazione da parte dell'Utente,
	 * il metodo prende in ingresso la String "chiave" associata
	 * inizialmente a un JPanel specifico
	 */

    public void switchPanel(String nameOfThePanel) {
	if (nameOfThePanel.equals("LOGIN_VIEW")) {
	    loginPanel.resetLogin();
	}else if(nameOfThePanel.equals("EDIT_PROD")) {
		editPanel.popolaComboBox();
	}else if(nameOfThePanel.equals("MOVE_PROD")) {
		movePanel.popolaComboBox();
		movePanel.resetCampi();
	} else if (nameOfThePanel.equals("VISUAL_UNDER")){
		visualUnderPanel.refresh();
	} else if (nameOfThePanel.equals("FILTER_PROD")){
		filterPanel.resetCampi();
	}
	cl.show(getContentPane(), nameOfThePanel);
    }
}