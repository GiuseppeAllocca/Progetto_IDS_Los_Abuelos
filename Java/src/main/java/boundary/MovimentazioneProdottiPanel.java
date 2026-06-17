package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;

import controller.ControllerGestione;

import service.NotificatoreService;
import service.BrevoEmailAdapter;

public class MovimentazioneProdottiPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private MainFrame parentFrame;

    private JPanel pnCentro;
    private JLabel lblProdotto;
    private JComboBox<String> cmbProdotti;
    private JLabel lblQuantita;
    private JSpinner spnQuantita;
    private JLabel lblTipo;
    private JPanel pnTipologia;
    private JRadioButton rdbtnCarico;
    private JRadioButton rdbtnScarico;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JLabel lblData;
    private DatePicker datePicker;

    private JPanel pnSud;
    private JPanel pnBottoniEst;
    private JPanel pnBottoniOvest;
    private JButton btnCancella;
    private JButton btnSalva;
    private JButton btnIndietro;

    public MovimentazioneProdottiPanel(MainFrame parentFrame) {
		this.parentFrame = parentFrame;

		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 20));

		add(getPnCentro(), BorderLayout.CENTER);
		add(getPnSud(), BorderLayout.SOUTH);

		popolaComboBox();
		getRdbtnCarico().setSelected(true);

		//resetCampi();
    }

    private JPanel getPnCentro() {
	if (pnCentro == null) {
	    pnCentro = new JPanel();
	    pnCentro.setBackground(Color.WHITE);

	    GridBagLayout gbl_pnCentro = new GridBagLayout();
	    gbl_pnCentro.columnWidths = new int[] { 180, 300, 0 };
	    gbl_pnCentro.rowHeights = new int[] { 40, 40, 40, 40, 0 };
	    gbl_pnCentro.columnWeights = new double[] { 0.0, 1.0,
		Double.MIN_VALUE };
	    gbl_pnCentro.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
		Double.MIN_VALUE };
	    pnCentro.setLayout(gbl_pnCentro);

	    GridBagConstraints gbc_lblProdotto = new GridBagConstraints();
	    gbc_lblProdotto.anchor = GridBagConstraints.EAST;
	    gbc_lblProdotto.insets = new Insets(0, 0, 15, 10);
	    gbc_lblProdotto.gridx = 0;
	    gbc_lblProdotto.gridy = 0;
	    pnCentro.add(getLblProdotto(), gbc_lblProdotto);

	    GridBagConstraints gbc_cmbProdotti = new GridBagConstraints();
	    gbc_cmbProdotti.fill = GridBagConstraints.HORIZONTAL;
	    gbc_cmbProdotti.insets = new Insets(0, 0, 15, 0);
	    gbc_cmbProdotti.gridx = 1;
	    gbc_cmbProdotti.gridy = 0;
	    pnCentro.add(getCmbProdotti(), gbc_cmbProdotti);

	    GridBagConstraints gbc_lblQuantita = new GridBagConstraints();
	    gbc_lblQuantita.anchor = GridBagConstraints.EAST;
	    gbc_lblQuantita.insets = new Insets(0, 0, 15, 10);
	    gbc_lblQuantita.gridx = 0;
	    gbc_lblQuantita.gridy = 1;
	    pnCentro.add(getLblQuantita(), gbc_lblQuantita);

	    GridBagConstraints gbc_spnQuantita = new GridBagConstraints();
	    gbc_spnQuantita.anchor = GridBagConstraints.WEST;
	    gbc_spnQuantita.insets = new Insets(0, 0, 15, 0);
	    gbc_spnQuantita.gridx = 1;
	    gbc_spnQuantita.gridy = 1;
	    pnCentro.add(getSpnQuantita(), gbc_spnQuantita);

	    GridBagConstraints gbc_lblTipo = new GridBagConstraints();
	    gbc_lblTipo.anchor = GridBagConstraints.EAST;
	    gbc_lblTipo.insets = new Insets(0, 0, 15, 10);
	    gbc_lblTipo.gridx = 0;
	    gbc_lblTipo.gridy = 2;
	    pnCentro.add(getLblTipo(), gbc_lblTipo);

	    GridBagConstraints gbc_pnTipologia = new GridBagConstraints();
	    gbc_pnTipologia.fill = GridBagConstraints.BOTH;
	    gbc_pnTipologia.insets = new Insets(0, 0, 15, 0);
	    gbc_pnTipologia.gridx = 1;
	    gbc_pnTipologia.gridy = 2;
	    pnCentro.add(getPnTipologia(), gbc_pnTipologia);

	    GridBagConstraints gbc_lblData = new GridBagConstraints();
	    gbc_lblData.anchor = GridBagConstraints.EAST;
	    gbc_lblData.insets = new Insets(0, 0, 0, 10);
	    gbc_lblData.gridx = 0;
	    gbc_lblData.gridy = 3;
	    pnCentro.add(getLblData(), gbc_lblData);

	    GridBagConstraints gbc_datePicker = new GridBagConstraints();
	    gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
	    gbc_datePicker.gridx = 1;
	    gbc_datePicker.gridy = 3;
	    pnCentro.add(getDatePicker(), gbc_datePicker);
	}
	return pnCentro;
    }

    private JLabel getLblProdotto() {
	if (lblProdotto == null) {
	    lblProdotto = new JLabel("Prodotto da movimentare:");
	    lblProdotto.setFont(new Font("SansSerif", Font.BOLD, 14));
	    lblProdotto.setLabelFor(getCmbProdotti());
	}
	return lblProdotto;
    }

    private JComboBox<String> getCmbProdotti() {
	if (cmbProdotti == null) {
	    cmbProdotti = new JComboBox<String>();
	    cmbProdotti.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    /*
		    Ogni qualvolta viene selezionato un prodotto
		    tramite il comboBox, lo Spinner setta
		    automaticamente le unità selezionate ad 1
		    */
			getSpnQuantita().setValue(1);
		}
	    });
	    cmbProdotti.setFont(new Font("SansSerif", Font.PLAIN, 14));
	}
	return cmbProdotti;
    }

    private JLabel getLblQuantita() {
	if (lblQuantita == null) {
	    lblQuantita = new JLabel("Quantità:");
	    lblQuantita.setFont(new Font("SansSerif", Font.BOLD, 14));
	    lblQuantita.setLabelFor(getSpnQuantita());
	}
	return lblQuantita;
    }

    private JSpinner getSpnQuantita() {
	if (spnQuantita == null) {
	    spnQuantita = new JSpinner();
	    spnQuantita.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    spnQuantita.setModel(new SpinnerNumberModel(Integer.valueOf(1),
		Integer.valueOf(1), null, Integer.valueOf(1)));
	    spnQuantita.setPreferredSize(new Dimension(80, 30));
	}
	return spnQuantita;
    }

    private JLabel getLblTipo() {
	if (lblTipo == null) {
	    lblTipo = new JLabel("Tipologia:");
	    lblTipo.setFont(new Font("SansSerif", Font.BOLD, 14));
	    lblTipo.setLabelFor(getPnTipologia());
	}
	return lblTipo;
    }

    private JPanel getPnTipologia() {
	if (pnTipologia == null) {
	    pnTipologia = new JPanel();
	    pnTipologia.setBackground(Color.WHITE);
	    pnTipologia.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
	    pnTipologia.add(getRdbtnCarico());
	    pnTipologia.add(getRdbtnScarico());
	}
	return pnTipologia;
    }

    private JRadioButton getRdbtnCarico() {
	if (rdbtnCarico == null) {
	    rdbtnCarico = new JRadioButton("Carico");
	    rdbtnCarico.setBackground(Color.WHITE);
	    rdbtnCarico.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    rdbtnCarico.setActionCommand("OPERAZIONE_DI_CARICO");
	    buttonGroup.add(rdbtnCarico);
	}
	return rdbtnCarico;
    }

    private JRadioButton getRdbtnScarico() {
	if (rdbtnScarico == null) {
	    rdbtnScarico = new JRadioButton("Scarico");
	    rdbtnScarico.setBackground(Color.WHITE);
	    rdbtnScarico.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    rdbtnScarico.setActionCommand("OPERAZIONE_DI_SCARICO");
	    buttonGroup.add(rdbtnScarico);
	}
	return rdbtnScarico;
    }

    private JLabel getLblData() {
	if (lblData == null) {
	    lblData = new JLabel("Data:");
	    lblData.setFont(new Font("SansSerif", Font.BOLD, 14));
	}
	return lblData;
    }

    private DatePicker getDatePicker() {
	if (datePicker == null) {
	    datePicker = new DatePicker();
	    datePicker.setDateToToday();
	    datePicker.setFont(new Font("SansSerif", Font.PLAIN, 14));
	}
	return datePicker;
    }

    private JPanel getPnSud() {
	if (pnSud == null) {
	    pnSud = new JPanel();
	    pnSud.setBackground(Color.WHITE);
	    pnSud.setLayout(new BorderLayout(0, 0));
	    pnSud.add(getPnBottoniEst(), BorderLayout.EAST);
	    pnSud.add(getPnBottoniOvest(), BorderLayout.WEST);
	}
	return pnSud;
    }

    private JPanel getPnBottoniEst() {
	if (pnBottoniEst == null) {
	    pnBottoniEst = new JPanel();
	    pnBottoniEst.setBackground(Color.WHITE);
	    pnBottoniEst.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
	    pnBottoniEst.add(getBtnCancella());
	    pnBottoniEst.add(getBtnSalva());
	}
	return pnBottoniEst;
    }

    private JButton getBtnCancella() {
	if (btnCancella == null) {
	    btnCancella = new JButton("Cancella");
	    btnCancella.setPreferredSize(new Dimension(110, 35));
	    btnCancella.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    /*
		    Chiamando questo metodo, ogni qualvota viene cliccato
		    il bottone "Cancella" si assiste ad un reset dell'insieme
		    dei componenti modificabili dall'utente
		    */
			resetCampi();
		}
	    });
	    btnCancella.setFont(new Font("SansSerif", Font.BOLD, 13));
	    btnCancella.setForeground(new Color(220, 53, 69));
	    btnCancella.setBackground(Color.WHITE);
	}
	return btnCancella;
    }

    private JButton getBtnSalva() {
	if (btnSalva == null) {
	    btnSalva = new JButton("Salva");
	    btnSalva.setPreferredSize(new Dimension(110, 35));
	    btnSalva.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    /*
		    Un eventuale click sul bottone "Salva" dà
		    inizio alla procedura di salvataggio relativa
		    alla movimentazione che l'utente desidera registrare
		    */
			salvaMovimento();
		}
	    });
	    btnSalva.setFont(new Font("SansSerif", Font.BOLD, 13));
	    btnSalva.setForeground(new Color(40, 167, 69));
	    btnSalva.setBackground(Color.WHITE);
	}
	return btnSalva;
    }

    private JPanel getPnBottoniOvest() {
	if (pnBottoniOvest == null) {
	    pnBottoniOvest = new JPanel();
	    pnBottoniOvest.setBackground(Color.WHITE);
	    pnBottoniOvest.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	    pnBottoniOvest.add(getBtnIndietro());
	}
	return pnBottoniOvest;
    }

    private JButton getBtnIndietro() {
	if (btnIndietro == null) {
	    btnIndietro = new JButton("Torna indietro");
	    btnIndietro.setPreferredSize(new Dimension(130, 35));
	    btnIndietro.addActionListener(e -> {
		parentFrame.switchPanel("DASH_OP");

	    });

	    btnIndietro.setForeground(new Color(108, 117, 125));
	    btnIndietro.setFont(new Font("SansSerif", Font.BOLD, 13));
	    btnIndietro.setBackground(Color.WHITE);
	}
	return btnIndietro;
    }

	/**
	=================================================
				LOGICA RELATIVA ALLA GUI
	=================================================
	**/

	/**
	Il metodo in questione serve a popolare dinamicamente
	il contenuto del comboBox, ogni volta che si accede alla vista "MovimentazioneProdottiPanel"
	**/
    public void popolaComboBox() {
	getCmbProdotti().setModel(
	    new DefaultComboBoxModel(ControllerGestione.getElencoProdotti()));
    }

	/**
	Il metodo resetCampi() serve a migliorare l'esperienza 	dell'utente quando
	interagisce con l'interfaccia di movimentazione del prodotto.
	Nello specifico questo metodo serve a tornare allo stato di defualt e garantisce...
		1) Auto-selezione del primo elemento presente nel comboBox
		2) Auto-selezione della quantità 1 nello spinner
		3) Auto-selezione del giorno corrente nel calendario
		4) Auto-selezione del radioButton "Operazione di Carico"

	NOTA: Il metodo è reso pubblico perché deve essere chiamato dal MainFrame per
	      resettare la GUI ogni volta che si accede alla vista "MovimentazioneProdottiPanel"
	**/
    public void resetCampi() {
		getCmbProdotti().setSelectedIndex(0);
		getSpnQuantita().setValue(1);
		if (getDatePicker() != null) {
			getDatePicker().setDateToToday();
		}
		getRdbtnCarico().setSelected(true);
    }

	/**
	Il metodo salvaMovimento() recupera la configurazione necessaria
	a salvare il movimento che l'utente vuole registrare, e si interfaccia
	con la classe "ControllerGestione" per ricevere l'esito riguardante
	il corretto salvataggio dell'operazione
	**/
    private void salvaMovimento() {

		String prodotto = getCmbProdotti().getSelectedItem().toString();
		if(prodotto == null){
			JOptionPane.showMessageDialog(this,
					"Nessun prodotto disponibile !!!",
					"Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String[] parti = prodotto.split(" - ");
		Integer unita = (Integer) getSpnQuantita().getValue();
		String tipoMovimento = buttonGroup.getSelection().getActionCommand();

		LocalDate data = getDatePicker().getDate();
		if(data == null){
			JOptionPane.showMessageDialog(this,
					"Selezionare una data valida !!!",
					"Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int esito = ControllerGestione.salvaMovimento(parti[1], unita,
			tipoMovimento, data);

		if (esito == ControllerGestione.SUCCESSO_MOVIMENTO) {
			JOptionPane.showMessageDialog(this,
					"La tua operazione è stata correttamente registrata !!!",
					"Salvataggio movimentazione prodotto",
					JOptionPane.INFORMATION_MESSAGE);
			resetCampi();
		} else if (esito == ControllerGestione.MANCATA_DISPONIBILITA) {
			JOptionPane.showMessageDialog(this,
					"Impossibile effettuare lo scarico: la quantità richiesta supera le scorte disponibili in magazzino !!!",
					"Scorte insufficenti", JOptionPane.ERROR_MESSAGE);
		} else if (esito == ControllerGestione.MOVIMENTO_FALLITO){
			JOptionPane.showMessageDialog(this,
					"La tua operazione non è stata correttamente registrata !!!",
					"Salvataggio movimentazione prodotto",
					JOptionPane.ERROR_MESSAGE);
		}else if (esito == ControllerGestione.SUCCESSO_MOVIMENTO_CON_NOTIFICA){
			JOptionPane.showMessageDialog(this,
					"La tua operazione è stata correttamente registrata !!!",
					"Salvataggio movimentazione prodotto",
					JOptionPane.INFORMATION_MESSAGE);
			resetCampi();
			notificaSottoScortaCots(parti[1]);
		}

	}

	/**
	 * Chiamata al componente esterno per la notificazione al responsabile
	 * usa l interfaccia NotificatoreService con la classe concreta BrevoEmailAdapter
	 * per poi usare il metodo per l' invio della notifica
	 *
	 * @param prodotto prodotto sotto scorta
	 */
	public void notificaSottoScortaCots(String prodotto){
		String emailDaInviare = ControllerGestione.notificaSottoScorta(prodotto);
		NotificatoreService service = new BrevoEmailAdapter();

		service.inviaNotifica("Prodotto sotto scorta", emailDaInviare);
	}
}