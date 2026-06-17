package boundary;

import controller.ControllerGestione;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreaProdottoPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private MainFrame parentFrame;
    private JPanel CentralPane;
    private JPanel ButtonsPane;
    private JButton CancelButton;
    private JButton SaveButton;
    private JLabel lblNome;
    private JLabel lblDescrizione;
    private JLabel lblCategoria;
    private JTextField txtEnterTheName;
    private JTextField txtEnterTheDescription;
    private JLabel lblQuantit;
    private JLabel lblSoglia;
    private JComboBox<String> cmbCategoria;
    private JSpinner spinnerQuantita;
    private JLabel lblCollocazione;
    private JComboBox<String> cmbCollocazione;
    private JSpinner SpinnerSogliaMinima;

    public CreaProdottoPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout(0, 20));
        
        add(getCentralPane(), BorderLayout.CENTER);
        add(getButtonsPane(), BorderLayout.SOUTH);
        
        resetCampi();
    }

    private JPanel getCentralPane() {
        if (CentralPane == null) {
            CentralPane = new JPanel();
            CentralPane.setBackground(Color.WHITE);
            
            CentralPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
                "Dettagli Nuovo Prodotto", 
                TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("SansSerif", Font.BOLD, 14), Color.DARK_GRAY));
            
            GridBagLayout gbl = new GridBagLayout();
            gbl.columnWidths = new int[]{150, 300, 0};
            gbl.rowHeights = new int[]{40, 40, 40, 40, 40, 40, 0};
            gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            CentralPane.setLayout(gbl);

            GridBagConstraints gbc_lblNome = new GridBagConstraints();
            gbc_lblNome.anchor = GridBagConstraints.EAST;
            gbc_lblNome.insets = new Insets(15, 10, 10, 15);
            gbc_lblNome.gridx = 0; gbc_lblNome.gridy = 0;
            CentralPane.add(getLblNome(), gbc_lblNome);

            GridBagConstraints gbc_txtNome = new GridBagConstraints();
            gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtNome.insets = new Insets(15, 0, 10, 10);
            gbc_txtNome.gridx = 1; gbc_txtNome.gridy = 0;
            CentralPane.add(getTxtEnterTheName(), gbc_txtNome);

            // --- RIGA 1: DESCRIZIONE ---
            GridBagConstraints gbc_lblDesc = new GridBagConstraints();
            gbc_lblDesc.anchor = GridBagConstraints.EAST;
            gbc_lblDesc.insets = new Insets(10, 10, 10, 15);
            gbc_lblDesc.gridx = 0; gbc_lblDesc.gridy = 1;
            CentralPane.add(getLblDescrizione(), gbc_lblDesc);

            GridBagConstraints gbc_txtDesc = new GridBagConstraints();
            gbc_txtDesc.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtDesc.insets = new Insets(10, 0, 10, 10);
            gbc_txtDesc.gridx = 1; gbc_txtDesc.gridy = 1;
            CentralPane.add(getTxtEnterTheDescription(), gbc_txtDesc);

            // --- RIGA 2: CATEGORIA ---
            GridBagConstraints gbc_lblCat = new GridBagConstraints();
            gbc_lblCat.anchor = GridBagConstraints.EAST;
            gbc_lblCat.insets = new Insets(10, 10, 10, 15);
            gbc_lblCat.gridx = 0; gbc_lblCat.gridy = 2;
            CentralPane.add(getLblCategoria(), gbc_lblCat);

            GridBagConstraints gbc_cmbCat = new GridBagConstraints();
            gbc_cmbCat.fill = GridBagConstraints.HORIZONTAL;
            gbc_cmbCat.insets = new Insets(10, 0, 10, 10);
            gbc_cmbCat.gridx = 1; gbc_cmbCat.gridy = 2;
            CentralPane.add(getCmbCategoria(), gbc_cmbCat);

            // --- RIGA 3: COLLOCAZIONE ---
            GridBagConstraints gbc_lblColloc = new GridBagConstraints();
            gbc_lblColloc.anchor = GridBagConstraints.EAST;
            gbc_lblColloc.insets = new Insets(10, 10, 10, 15);
            gbc_lblColloc.gridx = 0; gbc_lblColloc.gridy = 3;
            CentralPane.add(getLblCollocazione(), gbc_lblColloc);

            GridBagConstraints gbc_cmbColloc = new GridBagConstraints();
            gbc_cmbColloc.fill = GridBagConstraints.HORIZONTAL;
            gbc_cmbColloc.insets = new Insets(10, 0, 10, 10);
            gbc_cmbColloc.gridx = 1; gbc_cmbColloc.gridy = 3;
            CentralPane.add(getCmbCollocazione(), gbc_cmbColloc);

            // --- RIGA 4: QUANTITÀ ---
            GridBagConstraints gbc_lblQta = new GridBagConstraints();
            gbc_lblQta.anchor = GridBagConstraints.EAST;
            gbc_lblQta.insets = new Insets(10, 10, 10, 15);
            gbc_lblQta.gridx = 0; gbc_lblQta.gridy = 4;
            CentralPane.add(getLblQuantit(), gbc_lblQta);

            GridBagConstraints gbc_spnQta = new GridBagConstraints();
            gbc_spnQta.anchor = GridBagConstraints.WEST;
            gbc_spnQta.insets = new Insets(10, 0, 10, 10);
            gbc_spnQta.gridx = 1; gbc_spnQta.gridy = 4;
            CentralPane.add(getSpinnerQuantita(), gbc_spnQta);

            // --- RIGA 5: SOGLIA ---
            GridBagConstraints gbc_lblSoglia = new GridBagConstraints();
            gbc_lblSoglia.anchor = GridBagConstraints.EAST;
            gbc_lblSoglia.insets = new Insets(10, 10, 15, 15);
            gbc_lblSoglia.gridx = 0; gbc_lblSoglia.gridy = 5;
            CentralPane.add(getLblSoglia(), gbc_lblSoglia);

            GridBagConstraints gbc_spnSoglia = new GridBagConstraints();
            gbc_spnSoglia.anchor = GridBagConstraints.WEST;
            gbc_spnSoglia.insets = new Insets(10, 0, 15, 10);
            gbc_spnSoglia.gridx = 1; gbc_spnSoglia.gridy = 5;
            CentralPane.add(getSpinnerSogliaMinima(), gbc_spnSoglia);
        }
        return CentralPane;
    }

    private JPanel getButtonsPane() {
        if (ButtonsPane == null) {
            ButtonsPane = new JPanel();
            ButtonsPane.setBackground(Color.WHITE);
            ButtonsPane.setLayout(new BorderLayout(0, 0));
            
            JPanel pnEst = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            pnEst.setBackground(Color.WHITE);
            pnEst.add(getSaveButton());
            
            JPanel pnOvest = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            pnOvest.setBackground(Color.WHITE);
            pnOvest.add(getCancelButton());
            
            ButtonsPane.add(pnEst, BorderLayout.EAST);
            ButtonsPane.add(pnOvest, BorderLayout.WEST);
        }
        return ButtonsPane;
    }

    private JButton getCancelButton() {
        if (CancelButton == null) {
            CancelButton = new JButton("Torna Indietro");
            CancelButton.setPreferredSize(new Dimension(150, 35));
            CancelButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            CancelButton.setBackground(Color.WHITE);
            CancelButton.setForeground(new Color(108, 117, 125));
            
            CancelButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		resetCampi();
            		parentFrame.switchPanel("DASH_RESP");
            	}
            });
        }
        return CancelButton;
    }

    private JButton getSaveButton() {
        if (SaveButton == null) {
            SaveButton = new JButton("Salva Prodotto");
            SaveButton.setPreferredSize(new Dimension(150, 35));
            SaveButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            SaveButton.setBackground(Color.WHITE);
            SaveButton.setForeground(new Color(40, 167, 69));
            
            SaveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {creaProdotto();}
            });
        }
        return SaveButton;
    }

    /**
     * Raccoglie i dati del form, li valida e delega la creazione del prodotto
     * al ControllerGestione.
     *
     * Il metodo segue tre fasi in sequenza:
     * 1. Validazione dei campi obbligatori (nome e descrizione non vuoti)
     * 2. Verifica dell'univocità del nome prima di procedere al salvataggio
     * 3. Invocazione del Controller e gestione dell'esito
     *
     * In caso di successo il form viene reimpostato tramite resetCampi()
     * per consentire l'inserimento di un nuovo prodotto.
     * In caso di errore viene mostrato un messaggio descrittivo senza
     * alterare i dati già inseriti dall'utente.
     */
    public void creaProdotto(){
        String nome = txtEnterTheName.getText().trim();
        String desc = txtEnterTheDescription.getText();

        if(nome.isEmpty() || desc.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(CreaProdottoPanel.this,
                    "Compila tutti i campi testuali prima di salvare!",
                    "Errore di Inserimento",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        int qta = (Integer) spinnerQuantita.getValue();
        int soglia = (Integer) SpinnerSogliaMinima.getValue();

        String cat = cmbCategoria.getSelectedItem().toString();
        String pos = cmbCollocazione.getSelectedItem().toString();

        if (!ControllerGestione.isNomeUnivoco(nome, null)) {
            javax.swing.JOptionPane.showMessageDialog(CreaProdottoPanel.this,
                    "Attenzione: Esiste già un prodotto con questo nome!",
                    "Nome Duplicato",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean successo = ControllerGestione.aggiungiProdotto(nome, desc, qta, soglia, cat, pos);

        if(successo) {
            javax.swing.JOptionPane.showMessageDialog(CreaProdottoPanel.this,
                    "Prodotto salvato con successo!",
                    "Successo",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            resetCampi();
        } else {
            JOptionPane.showMessageDialog(CreaProdottoPanel.this,
                    "Prodotto non salvato correttamente...",
                    "Errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel getLblNome() {
        if (lblNome == null) {
            lblNome = new JLabel("Nome Prodotto:");
            lblNome.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblNome;
    }

    private JLabel getLblDescrizione() {
        if (lblDescrizione == null) {
            lblDescrizione = new JLabel("Descrizione:");
            lblDescrizione.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblDescrizione;
    }

    private JLabel getLblCategoria() {
        if (lblCategoria == null) {
            lblCategoria = new JLabel("Categoria:");
            lblCategoria.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblCategoria;
    }

    private JTextField getTxtEnterTheName() {
        if (txtEnterTheName == null) {
            txtEnterTheName = new JTextField();
            txtEnterTheName.setFont(new Font("SansSerif", Font.PLAIN, 14));
            txtEnterTheName.setPreferredSize(new Dimension(250, 30));
        }
        return txtEnterTheName;
    }

    private JTextField getTxtEnterTheDescription() {
        if (txtEnterTheDescription == null) {
            txtEnterTheDescription = new JTextField();
            txtEnterTheDescription.setFont(new Font("SansSerif", Font.PLAIN, 14));
            txtEnterTheDescription.setPreferredSize(new Dimension(250, 30));
        }
        return txtEnterTheDescription;
    }

    private JLabel getLblQuantit() {
        if (lblQuantit == null) {
            lblQuantit = new JLabel("Quantità Iniziale:");
            lblQuantit.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblQuantit;
    }

    private JLabel getLblSoglia() {
        if (lblSoglia == null) {
            lblSoglia = new JLabel("Soglia Minima:");
            lblSoglia.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblSoglia;
    }

    private JComboBox<String> getCmbCategoria() {
        if (cmbCategoria == null) {
            cmbCategoria = new JComboBox<>();
            cmbCategoria.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cmbCategoria.setPreferredSize(new Dimension(250, 30));
        }
        return cmbCategoria;
    }

    private JSpinner getSpinnerQuantita() {
        if (spinnerQuantita == null) {
            spinnerQuantita = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
            spinnerQuantita.setFont(new Font("SansSerif", Font.PLAIN, 14));
            spinnerQuantita.setPreferredSize(new Dimension(100, 30));
        }
        return spinnerQuantita;
    }

    private JLabel getLblCollocazione() {
        if (lblCollocazione == null) {
            lblCollocazione = new JLabel("Collocazione:");
            lblCollocazione.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblCollocazione;
    }

    private JComboBox<String> getCmbCollocazione() {
        if (cmbCollocazione == null) {
            cmbCollocazione = new JComboBox<>();
            cmbCollocazione.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cmbCollocazione.setPreferredSize(new Dimension(250, 30));
        }
        return cmbCollocazione;
    }

    private JSpinner getSpinnerSogliaMinima() {
        if (SpinnerSogliaMinima == null) {
        	SpinnerSogliaMinima = new JSpinner(new SpinnerNumberModel(Integer.valueOf(1),
    	    		Integer.valueOf(1), null, Integer.valueOf(1)));
            SpinnerSogliaMinima.setFont(new Font("SansSerif", Font.PLAIN, 14));
            SpinnerSogliaMinima.setPreferredSize(new Dimension(100, 30));
            
        }
        return SpinnerSogliaMinima;
    }

    /**
     * Reimposta il form allo stato iniziale per una nuova creazione.
     *
     * Svuota i campi testuali, riporta gli spinner al valore minimo (1)
     * e ricarica i modelli delle ComboBox categoria e collocazione.
     */
    public void resetCampi() {
		getTxtEnterTheName().setText("");
		getTxtEnterTheDescription().setText("");
		getSpinnerQuantita().setValue(1);
		getSpinnerSogliaMinima().setValue(1);

        getCmbCategoria().setModel(new DefaultComboBoxModel<>(ControllerGestione.getCategorieDisponibili()));
        getCmbCollocazione().setModel(new DefaultComboBoxModel<>(ControllerGestione.getCollocazioniDisponibili()));

		if (getCmbCategoria().getItemCount() > 0) {
			getCmbCategoria().setSelectedIndex(0);
		}
		if (getCmbCollocazione().getItemCount() > 0) {
			getCmbCollocazione().setSelectedIndex(0);
		}
	}

}