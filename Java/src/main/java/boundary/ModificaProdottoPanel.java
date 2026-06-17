package boundary;

import controller.ControllerGestione;
import service.BrevoEmailAdapter;
import service.NotificatoreService;

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
import java.util.Map;

public class ModificaProdottoPanel extends JPanel {

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
    private JLabel lblSoglia;
    private JComboBox<String> cmbCategoria;
    private JLabel lblCollocazione;
    private JComboBox<String> cmbCollocazioni;
    private JSpinner spnSoglia;
    private JLabel lblQuantit;
    private JComboBox<String> cmbProdotti;

    /**
     * Valori originali del prodotto caricato dal database.
     * Utilizzati da haModificheNonSalvate() per rilevare se l'utente
     * ha apportato cambiamenti rispetto ai dati persistiti.
     * Vengono impostati in popolaCampiProdottoSelezionato() e
     * azzerati in resetCampi().
     */
    private String nomeOriginale;
    private String descrizioneOriginale;
    private String categoriaOriginale;
    private String collocazioneOriginale;
    private int sogliaOriginale;

    public ModificaProdottoPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout(0, 20));
        
        add(getCentralPane(), BorderLayout.CENTER);
        add(getButtonsPane(), BorderLayout.SOUTH);

        popolaComboBox();
        resetCampi();
    }

    private JPanel getCentralPane() {
        if (CentralPane == null) {
            CentralPane = new JPanel();
            CentralPane.setBackground(Color.WHITE);
            
            CentralPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
                "Dettagli e Modifica Prodotto", 
                TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("SansSerif", Font.BOLD, 14), Color.DARK_GRAY));
            
            GridBagLayout gbl = new GridBagLayout();
            gbl.columnWidths = new int[]{150, 300, 0};
            gbl.rowHeights = new int[]{40, 40, 40, 40, 40, 40, 0};
            gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            CentralPane.setLayout(gbl);

            GridBagConstraints gbc_lblProd = new GridBagConstraints();
            gbc_lblProd.anchor = GridBagConstraints.EAST;
            gbc_lblProd.insets = new Insets(15, 10, 10, 15);
            gbc_lblProd.gridx = 0; gbc_lblProd.gridy = 0;
            CentralPane.add(getLblProdotto(), gbc_lblProd);

            GridBagConstraints gbc_cmbProd = new GridBagConstraints();
            gbc_cmbProd.fill = GridBagConstraints.HORIZONTAL;
            gbc_cmbProd.insets = new Insets(15, 0, 10, 10);
            gbc_cmbProd.gridx = 1; gbc_cmbProd.gridy = 0;
            CentralPane.add(getCmbProdotti(), gbc_cmbProd);

            GridBagConstraints gbc_lblNome = new GridBagConstraints();
            gbc_lblNome.anchor = GridBagConstraints.EAST;
            gbc_lblNome.insets = new Insets(10, 10, 10, 15);
            gbc_lblNome.gridx = 0; gbc_lblNome.gridy = 1;
            CentralPane.add(getLblNome(), gbc_lblNome);

            GridBagConstraints gbc_txtNome = new GridBagConstraints();
            gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtNome.insets = new Insets(10, 0, 10, 10);
            gbc_txtNome.gridx = 1; gbc_txtNome.gridy = 1;
            CentralPane.add(getTxtEnterTheName(), gbc_txtNome);

            GridBagConstraints gbc_lblDesc = new GridBagConstraints();
            gbc_lblDesc.anchor = GridBagConstraints.EAST;
            gbc_lblDesc.insets = new Insets(10, 10, 10, 15);
            gbc_lblDesc.gridx = 0; gbc_lblDesc.gridy = 2;
            CentralPane.add(getLblDescrizione(), gbc_lblDesc);

            GridBagConstraints gbc_txtDesc = new GridBagConstraints();
            gbc_txtDesc.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtDesc.insets = new Insets(10, 0, 10, 10);
            gbc_txtDesc.gridx = 1; gbc_txtDesc.gridy = 2;
            CentralPane.add(getTxtEnterTheDescription(), gbc_txtDesc);

            GridBagConstraints gbc_lblCat = new GridBagConstraints();
            gbc_lblCat.anchor = GridBagConstraints.EAST;
            gbc_lblCat.insets = new Insets(10, 10, 10, 15);
            gbc_lblCat.gridx = 0; gbc_lblCat.gridy = 3;
            CentralPane.add(getLblCategoria(), gbc_lblCat);

            GridBagConstraints gbc_cmbCat = new GridBagConstraints();
            gbc_cmbCat.fill = GridBagConstraints.HORIZONTAL;
            gbc_cmbCat.insets = new Insets(10, 0, 10, 10);
            gbc_cmbCat.gridx = 1; gbc_cmbCat.gridy = 3;
            CentralPane.add(getCmbCategoria(), gbc_cmbCat);

            GridBagConstraints gbc_lblColloc = new GridBagConstraints();
            gbc_lblColloc.anchor = GridBagConstraints.EAST;
            gbc_lblColloc.insets = new Insets(10, 10, 10, 15);
            gbc_lblColloc.gridx = 0; gbc_lblColloc.gridy = 4;
            CentralPane.add(getLblCollocazione(), gbc_lblColloc);

            GridBagConstraints gbc_cmbColloc = new GridBagConstraints();
            gbc_cmbColloc.fill = GridBagConstraints.HORIZONTAL;
            gbc_cmbColloc.insets = new Insets(10, 0, 10, 10);
            gbc_cmbColloc.gridx = 1; gbc_cmbColloc.gridy = 4;
            CentralPane.add(getCmbCollocazioni(), gbc_cmbColloc);

            GridBagConstraints gbc_lblSoglia = new GridBagConstraints();
            gbc_lblSoglia.anchor = GridBagConstraints.EAST;
            gbc_lblSoglia.insets = new Insets(10, 10, 15, 15);
            gbc_lblSoglia.gridx = 0; gbc_lblSoglia.gridy = 5;
            CentralPane.add(getLblSoglia(), gbc_lblSoglia);

            GridBagConstraints gbc_spnSoglia = new GridBagConstraints();
            gbc_spnSoglia.anchor = GridBagConstraints.WEST;
            gbc_spnSoglia.insets = new Insets(10, 0, 15, 10);
            gbc_spnSoglia.gridx = 1; gbc_spnSoglia.gridy = 5;
            CentralPane.add(getSpnSoglia(), gbc_spnSoglia);
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

    /**
     * Bottone "Torna Indietro".
     * Prima di navigare alla dashboard del Responsabile, verifica
     * tramite haModificheNonSalvate() se esistono modifiche pendenti:
     * in tal caso mostra un dialogo di conferma all'utente.
     */
    private JButton getCancelButton() {
        if (CancelButton == null) {
            CancelButton = new JButton("Torna Indietro");
            CancelButton.setPreferredSize(new Dimension(150, 35));
            CancelButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            CancelButton.setBackground(Color.WHITE);
            CancelButton.setForeground(new Color(108, 117, 125));
            
            CancelButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {

                    if (haModificheNonSalvate()) {
                        int scelta = JOptionPane.showConfirmDialog(
                                ModificaProdottoPanel.this,
                                "Hai modifiche non salvate. Vuoi davvero tornare indietro?",
                                "Modifiche non salvate",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );
                        if (scelta != JOptionPane.YES_OPTION) return;
                    }
                    parentFrame.switchPanel("DASH_RESP");
            		resetCampi();
            	}
            });
        }
        return CancelButton;
    }

    private JButton getSaveButton() {
        if (SaveButton == null) {
            SaveButton = new JButton("Salva Modifiche");
            SaveButton.setPreferredSize(new Dimension(150, 35));
            SaveButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            SaveButton.setBackground(Color.WHITE);
            SaveButton.setForeground(new Color(0, 123, 255));
            SaveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modificaProdotto();
                }
            });
        }
        return SaveButton;
    }

    /**
     * Raccoglie i valori dai campi del form, esegue la validazione base
     * (campi non vuoti) ed invoca il Controller per persistere le modifiche.
     *
     * L'ID del prodotto viene estratto dalla stringa della ComboBox
     * nel formato "ID - NomeProdotto".
     *
     * In caso di successo aggiorna la ComboBox con i dati dal DB
     * e reimposta il pannello allo stato iniziale tramite resetCampi().
     */
    public void modificaProdotto(){
        String nome = txtEnterTheName.getText().trim();
        String desc = txtEnterTheDescription.getText().trim();

        if (nome.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "I campi non possono essere vuoti!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selezione = (String) cmbProdotti.getSelectedItem();
        String[] parti = selezione.split(" - ");
        Long idProdotto = Long.parseLong(parti[0]);

        int soglia = (Integer) spnSoglia.getValue();
        String cat = cmbCategoria.getSelectedItem().toString();
        String pos = cmbCollocazioni.getSelectedItem().toString();

        ControllerGestione.EsitoModifica esito = ControllerGestione.modificaProdottoPerId(
                idProdotto, nome, desc, soglia, cat, pos
        );

        switch (esito) {
            case SUCCESSO:
                JOptionPane.showMessageDialog(null, "Prodotto aggiornato!");
                popolaComboBox();
                resetCampi();
                break;
            case SUCCESSO_CON_NOTIFICA:
                JOptionPane.showMessageDialog(null, "Prodotto aggiornato!");
                popolaComboBox();
                notificaSottoScortaCots(parti[1]);
                resetCampi();
                break;
            case NOME_DUPLICATO:
                JOptionPane.showMessageDialog(null,
                        "Questo nome è già utilizzato da un altro prodotto!",
                        "Nome Duplicato", JOptionPane.ERROR_MESSAGE);
                break;
            case ERRORE:
                JOptionPane.showMessageDialog(null,
                        "Errore durante l'aggiornamento.");
                break;
        }
    }

    
    private JLabel getLblProdotto() {
        if (lblQuantit == null) {
            lblQuantit = new JLabel("Seleziona Prodotto:");
            lblQuantit.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblQuantit;
    }

    private JComboBox<String> getCmbProdotti() {
        if (cmbProdotti == null) {
            cmbProdotti = new JComboBox<>(ControllerGestione.getElencoProdotti());
            cmbProdotti.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cmbProdotti.setPreferredSize(new Dimension(250, 30));

            cmbProdotti.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    popolaCampiProdottoSelezionato();
                }
            });
        }
        return cmbProdotti;
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

    private JLabel getLblSoglia() {
        if (lblSoglia == null) {
            lblSoglia = new JLabel("Soglia minima:");
            lblSoglia.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblSoglia;
    }

    private JComboBox<String> getCmbCategoria() {
        if (cmbCategoria == null) {
            cmbCategoria = new JComboBox<>(ControllerGestione.getCategorieDisponibili());
            cmbCategoria.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cmbCategoria.setPreferredSize(new Dimension(250, 30));
        }
        return cmbCategoria;
    }

    private JLabel getLblCollocazione() {
        if (lblCollocazione == null) {
            lblCollocazione = new JLabel("Collocazione:");
            lblCollocazione.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return lblCollocazione;
    }

    private JComboBox<String> getCmbCollocazioni() {
        if (cmbCollocazioni == null) {
            cmbCollocazioni = new JComboBox<>(ControllerGestione.getCollocazioniDisponibili());
            cmbCollocazioni.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cmbCollocazioni.setPreferredSize(new Dimension(250, 30));
        }
        return cmbCollocazioni;
    }

    private JSpinner getSpnSoglia() {
        if (spnSoglia == null) {

            spnSoglia = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
            spnSoglia.setFont(new Font("SansSerif", Font.PLAIN, 14));
            spnSoglia.setPreferredSize(new Dimension(100, 30));
        }
        return spnSoglia;
    }

    /**
     * Carica dal database i dati del prodotto correntemente selezionato
     * nella ComboBox e li popola in tutti i campi del form.
     *
     * L'ID viene estratto dalla stringa "ID - NomeProdotto" della ComboBox.
     * I valori caricati vengono anche salvati nelle variabili *Originale
     * per consentire il rilevamento di modifiche successive.
     */
    private void popolaCampiProdottoSelezionato() {
        String selezione = (String) cmbProdotti.getSelectedItem();

        if (selezione == null || selezione.trim().isEmpty()) return;

        String[] parti = selezione.split(" - ");
        Long idProdotto = Long.parseLong(parti[0]);

            Map<String, String> dettagli = ControllerGestione.getDettagliProdotto(idProdotto);

            if (dettagli != null) {
                txtEnterTheName.setText(dettagli.get("nome"));
                txtEnterTheDescription.setText(dettagli.get("descrizione"));
                spnSoglia.setValue(Integer.parseInt(dettagli.get("soglia")));
                cmbCategoria.setSelectedItem(dettagli.get("categoria"));
                cmbCollocazioni.setSelectedItem(dettagli.get("posizione"));

                nomeOriginale         = dettagli.get("nome");
                descrizioneOriginale  = dettagli.get("descrizione");
                sogliaOriginale       = Integer.parseInt(dettagli.get("soglia"));
                categoriaOriginale    = dettagli.get("categoria");
                collocazioneOriginale = dettagli.get("posizione");
            }
    }

    /**
     * Verifica se l'utente ha modificato almeno un campo rispetto
     * ai valori originali caricati dal database.
     *
     *
     * @return true se esiste almeno una modifica non salvata, false altrimenti
     */
    private boolean haModificheNonSalvate() {
        if (nomeOriginale == null) return false;
        return !txtEnterTheName.getText().trim().equals(nomeOriginale)
                || !txtEnterTheDescription.getText().trim().equals(descrizioneOriginale)
                || !cmbCategoria.getSelectedItem().toString().equals(categoriaOriginale)
                || !cmbCollocazioni.getSelectedItem().toString().equals(collocazioneOriginale)
                || (Integer) spnSoglia.getValue() != sogliaOriginale;
    }

    /**
     * Reimposta il pannello allo stato iniziale.
     *
     * Azzera le variabili dei valori originali,
     * riposiziona la ComboBox sul primo elemento e l'ActionListener
     * di cmbProdotti chiamerà automaticamente popolaCampiProdottoSelezionato().
     */
    public void resetCampi() {

        nomeOriginale        = null;
        descrizioneOriginale = null;
        categoriaOriginale   = null;
        collocazioneOriginale = null;
        sogliaOriginale      = 0;

        if (getCmbProdotti().getItemCount() > 0) {
			getCmbProdotti().setSelectedIndex(0);
		}

	}

    /**
     * Aggiorna il modello della ComboBox prodotti con i dati aggioranti dal DB.
     * Viene chiamata da MainFrame al momento della navigazione verso questo
     * pannello, e da modificaProdotto() dopo un salvataggio riuscito,
     * per riflettere eventuali cambi di nome.
     */
    public void popolaComboBox() {
        getCmbProdotti().setModel(
                new DefaultComboBoxModel(ControllerGestione.getElencoProdotti()));
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