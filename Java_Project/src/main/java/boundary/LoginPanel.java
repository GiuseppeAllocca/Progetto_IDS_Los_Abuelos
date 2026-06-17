package boundary;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ControllerGestione;

public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private MainFrame parentFrame;

    private JLabel lblLogo;
    private JPanel panelCentro;
    private JLabel LblUsername;
    private JLabel LblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPanel panelSud;
    private JPanel panelCentroSud;
    private JPanel panelSudSud;
    private JRadioButton rdbtnResponsabile;
    private JRadioButton rdbtnOperatore;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JButton btnAccedi;

    public LoginPanel(MainFrame parentFrame) {
	this.parentFrame = parentFrame;

	setBorder(new EmptyBorder(20, 20, 20, 20));
	setLayout(new BorderLayout(0, 20));

	add(getLblLogo(), BorderLayout.NORTH);
	add(getPanelCentro(), BorderLayout.CENTER);
	add(getPanelSud(), BorderLayout.SOUTH);
    }

    private JLabel getLblLogo() {
	if (lblLogo == null) {
	    lblLogo = new JLabel("");
	    lblLogo.setHorizontalAlignment(JLabel.CENTER);
	    URL imgUrl = getClass().getResource("/images/logo.png");
	    if (imgUrl != null) {
		Image imgScalata = new ImageIcon(imgUrl).getImage()
		    .getScaledInstance(120, -1, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(imgScalata));
	    } else {
		lblLogo.setText("LOGO APPLICAZIONE");
	    }
	}
	return lblLogo;
    }

    private JPanel getPanelCentro() {
	if (panelCentro == null) {
	    panelCentro = new JPanel();
	    GridBagLayout gbl_panelCentro = new GridBagLayout();
	    gbl_panelCentro.columnWidths = new int[] { 100, 200, 0 };
	    gbl_panelCentro.rowHeights = new int[] { 30, 30, 0 };
	    gbl_panelCentro.columnWeights = new double[] { 0.0, 1.0,
		Double.MIN_VALUE };
	    gbl_panelCentro.rowWeights = new double[] { 0.0, 0.0,
		Double.MIN_VALUE };
	    panelCentro.setLayout(gbl_panelCentro);

	    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
	    gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
	    gbc_lblNewLabel.insets = new Insets(0, 0, 15, 10);
	    gbc_lblNewLabel.gridx = 0;
	    gbc_lblNewLabel.gridy = 0;
	    panelCentro.add(getLblUsername(), gbc_lblNewLabel);

	    GridBagConstraints gbc_txtUsername = new GridBagConstraints();
	    gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
	    gbc_txtUsername.insets = new Insets(0, 0, 15, 0);
	    gbc_txtUsername.gridx = 1;
	    gbc_txtUsername.gridy = 0;
	    panelCentro.add(getTxtUsername(), gbc_txtUsername);

	    GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
	    gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
	    gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 10);
	    gbc_lblNewLabel_1.gridx = 0;
	    gbc_lblNewLabel_1.gridy = 1;
	    panelCentro.add(getLblPassword(), gbc_lblNewLabel_1);

	    GridBagConstraints gbc_txtPassword = new GridBagConstraints();
	    gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
	    gbc_txtPassword.gridx = 1;
	    gbc_txtPassword.gridy = 1;
	    panelCentro.add(getTxtPassword(), gbc_txtPassword);
	}
	return panelCentro;
    }

    private JLabel getLblUsername() {
	if (LblUsername == null) {
		LblUsername = new JLabel("Username:");
	}
	return LblUsername;
    }

    private JLabel getLblPassword() {
	if (LblPassword == null) {
		LblPassword = new JLabel("Password:");
	}
	return LblPassword;
    }

    private JTextField getTxtUsername() {
	if (txtUsername == null) {
	    txtUsername = new JTextField();
	    txtUsername.setColumns(10);
	}
	return txtUsername;
    }

    private JPasswordField getTxtPassword() {
	if (txtPassword == null) {
	    txtPassword = new JPasswordField();
	    txtPassword.setColumns(10);
	}
	return txtPassword;
    }

    private JPanel getPanelSud() {
	if (panelSud == null) {
	    panelSud = new JPanel();
	    panelSud.setLayout(new BorderLayout(0, 10));
	    panelSud.add(getPanelCentroSud(), BorderLayout.CENTER);
	    panelSud.add(getPanelSudSud(), BorderLayout.SOUTH);
	}
	return panelSud;
    }

    private JPanel getPanelCentroSud() {
	if (panelCentroSud == null) {
	    panelCentroSud = new JPanel();
	    panelCentroSud.add(getRdbtnResponsabile());
	    panelCentroSud.add(getRdbtnOperatore());
	}
	return panelCentroSud;
    }

    private JPanel getPanelSudSud() {
	if (panelSudSud == null) {
	    panelSudSud = new JPanel();
	    panelSudSud.add(getBtnAccedi());
	}
	return panelSudSud;
    }

    private JRadioButton getRdbtnResponsabile() {
	if (rdbtnResponsabile == null) {
	    rdbtnResponsabile = new JRadioButton("Responsabile");
	    buttonGroup.add(rdbtnResponsabile);
	    rdbtnResponsabile.setSelected(true);
	}
	return rdbtnResponsabile;
    }

    private JRadioButton getRdbtnOperatore() {
	if (rdbtnOperatore == null) {
	    rdbtnOperatore = new JRadioButton("Operatore");
	    buttonGroup.add(rdbtnOperatore);
	}
	return rdbtnOperatore;
    }

    private JButton getBtnAccedi() {
	if (btnAccedi == null) {
	    btnAccedi = new JButton("Accedi");

	    btnAccedi.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			/**
			 * Quando l'Utente selezione il Jbutton "Accedi" , il listener
			 * inizia una sequenza di operazioni la cui resposabilità è
			 * legata alla boundary prima di invocare i metodi del Controller
			 * per la verifica delle credeniali e ruolo di accesso selezionati
			 * (come il controllo dei campi vuoti)
			 */

		    String username = getTxtUsername().getText();
		    String password = new String(getTxtPassword().getPassword());
		    boolean isResp = getRdbtnResponsabile().isSelected();

		    if (username.trim().isEmpty()
			|| password.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
			    "Attenzione: Username e Password sono obbligatori.",
			    "Dati Mancanti", JOptionPane.WARNING_MESSAGE);
			return;
		    }

			/**
			 * Superati i controlli più superficiali a livello di boundary
			 * si invoca il metodo verificaLogin del Controller a cui vengono
			 * passate le credenziali dell'utente e il ruolo selezionato,
			 * fatto ciò in funzione del tipo di esito ritornato dal Controller
			 * si prosegue a consentire l'accesso o meno dell'utente ai Panel successivi
			 */

			ControllerGestione.EsitoAccesso esito = ControllerGestione.verificaLogin(username,
			password, isResp);

		    if (esito == ControllerGestione.EsitoAccesso.UTENTE_INESISTENTE) {
				JOptionPane.showMessageDialog(null,
						"Utente inesistente, inserire un utente valido.",
						"Errore Login", JOptionPane.ERROR_MESSAGE);

		    } else if (esito == ControllerGestione.EsitoAccesso.LOGIN_CON_SUCCESSO) {
				if(isResp){
					JOptionPane.showMessageDialog(null,
							"Accesso eseguito come Responsabile.", "Login OK",
							JOptionPane.INFORMATION_MESSAGE);

					parentFrame.switchPanel("DASH_RESP");
				}else{
					JOptionPane.showMessageDialog(null,
							"Accesso eseguito come Operatore.", "Login OK",
							JOptionPane.INFORMATION_MESSAGE);

					parentFrame.switchPanel("DASH_OP");
				}
		    } else {
			JOptionPane.showMessageDialog(null,
			    "Credenziali errate o ruolo non corrispondente.",
			    "Errore Login", JOptionPane.ERROR_MESSAGE);
		    }
		}

	    });
	}
	return btnAccedi;
    }

	/**
	 * Il metodo resetLogin() viene usato per rimuovere i dati
	 * di un precedente accesso dalla schermata di login
	 * simulando l'utilizzo da parte di vari utenti della stessa
	 * istanza di programma a runtime , il metodo è pubblico perchè
	 * viene invocato dal MainFrame ogni qualvolta si seleziona
	 * l'opzione di logOut da uno degli altri Panel implementati
	 */

    public void resetLogin() {
	getTxtUsername().setText("");
	getTxtPassword().setText("");
	getTxtUsername().requestFocus();
	getRdbtnResponsabile().doClick();
    }
}