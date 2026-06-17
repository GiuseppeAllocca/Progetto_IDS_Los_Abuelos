package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controller.ControllerGestione;

public class RicercaProdottiPerFiltroPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainFrame parentFrame;
	
	private JPanel pnNord;
	private JPanel pnSud;
	private JPanel pnCentro;
	
	private JLabel lblProdottiFiltrati;
	private JComboBox<String> comboBoxProdotti;
	
	private JPanel pnDettagli;
	private JLabel lblNomeProdotto;
	private JLabel lblNomeProdottoVisualizza;
	private JLabel lblDescrizioneProdotto;
	private JLabel lblDescrizioneProdottoVisualizza;
	private JLabel lblQuantitaProdotto;
	private JLabel lblQuantitaProdottoVisualizza;
	private JLabel lblSogliaProdotto;
	private JLabel lblSogliaProdottoVisualizza;
	private JLabel lbliDProdotto;
	private JLabel lbliDProdottoVisualizza;
	
	private JButton btnIndietro;
	private JButton btnFiltraPer;
	
	private String pannelloDiProvenienza;
	private String tipoFiltro;
	
	private JPanel pnListe;
	private JScrollPane scrollPaneFiltriDisponibili;
	private JScrollPane scrollPaneFiltriSelezionati;
	private JPanel pnBottoni;
	private JButton btnRimuovi;
	private JButton btnAggiungi;
	private JButton btnAvviaRicerca;
	private JList listFiltriDisponibili;
	private JList listFiltriSelezionati;
	
	private DefaultListModel modelListFiltriDisponibili = null;
	private DefaultListModel modelListFiltriSelezionati = null;
	
	public RicercaProdottiPerFiltroPanel(MainFrame parentFrame) {
		this.parentFrame = parentFrame;
		
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 20));
		
		add(getPnNord(), BorderLayout.NORTH);
		add(getPnCentro(), BorderLayout.CENTER);
		add(getPnSud(), BorderLayout.SOUTH);
		
		this.tipoFiltro = "categoria";

		resetCampi();
	}

	private JPanel getPnNord() {
		if (pnNord == null) {
			pnNord = new JPanel();
			pnNord.setBackground(Color.WHITE);
			pnNord.setBorder(new TitledBorder(null, "Filtri da selezionare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnNord.setLayout(new BorderLayout(0, 0));
			pnNord.add(getPnListe(), BorderLayout.CENTER);
			pnNord.add(getPnBottoni(), BorderLayout.SOUTH);
		}
		return pnNord;
	}
	
	private JPanel getPnListe() {
		if (pnListe == null) {
			pnListe = new JPanel();
			pnListe.setBackground(Color.WHITE);
			pnListe.setLayout(new GridLayout(0, 2, 0, 0));
			pnListe.add(getScrollPaneFiltriDisponibili());
			pnListe.add(getScrollPaneFiltriSelezionati());
		}
		return pnListe;
	}
	
	private JScrollPane getScrollPaneFiltriDisponibili() {
		if (scrollPaneFiltriDisponibili == null) {
			scrollPaneFiltriDisponibili = new JScrollPane();
			scrollPaneFiltriDisponibili.setViewportView(getListFiltriDisponibili());
		}
		return scrollPaneFiltriDisponibili;
	}
	private JScrollPane getScrollPaneFiltriSelezionati() {
		if (scrollPaneFiltriSelezionati == null) {
			scrollPaneFiltriSelezionati = new JScrollPane();
			scrollPaneFiltriSelezionati.setViewportView(getListFiltriSelezionati());
		}
		return scrollPaneFiltriSelezionati;
	}
	private JPanel getPnBottoni() {
		if (pnBottoni == null) {
			pnBottoni = new JPanel();
			pnBottoni.setBackground(Color.WHITE);
			pnBottoni.add(getBtnRimuovi());
			pnBottoni.add(getBtnAggiungi());
			pnBottoni.add(getBtnAvviaRicerca());
		}
		return pnBottoni;
	}


	private JButton getBtnRimuovi() {
		if (btnRimuovi == null) {
			btnRimuovi = new JButton("Rimuovi filtro");
			btnRimuovi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					/**
					 *Dopo che l'Utente ha selezionato un filtro e ha deciso di rimuoverlo
					 * il programma controlla prima che abbia effettivamente selezionato qualcosa,
					 * in caso affermtivo procede a rimuovere quell'elemento dalla JList dei filtri
					 * selezionati andando a riaggiungerlo nella JList dei filtri disponibili
					 */
					
					if(getListFiltriSelezionati().getSelectedValue() == null) {
						JOptionPane.showMessageDialog(null, "Non è stato selezionato alcun filtro", "Errore filtro", JOptionPane.ERROR_MESSAGE);
					}
					else {
						modelListFiltriDisponibili.addElement(getListFiltriSelezionati().getSelectedValue());
						modelListFiltriSelezionati.removeElement(getListFiltriSelezionati().getSelectedValue());
					}
					
				}
			});
		}
		return btnRimuovi;
	}
	private JButton getBtnAggiungi() {
		if (btnAggiungi == null) {
			btnAggiungi = new JButton("Aggiungi filtro");
			btnAggiungi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					/**
					 * La sequenza di azioni svolte a seguito dell'interazione con il
					 * JButton di aggiunta del filtro è perfettamente speculare a quella
					 * di rimozione del filtro descritte più in alto nel codice
					 */

					if(getListFiltriDisponibili().getSelectedValue() == null) {
						JOptionPane.showMessageDialog(null, "Non è stato selezionato alcun filtro", "Errore filtro", JOptionPane.ERROR_MESSAGE);
					}
					else {
						modelListFiltriSelezionati.addElement(getListFiltriDisponibili().getSelectedValue());
						modelListFiltriDisponibili.removeElement(getListFiltriDisponibili().getSelectedValue());
					}
					
				}
			});
		}
		return btnAggiungi;
	}

	private JButton getBtnAvviaRicerca() {
		if (btnAvviaRicerca == null) {
			btnAvviaRicerca = new JButton("Avvia Ricerca");
			btnAvviaRicerca.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					avviaRicerca();
				}
			});
		}
		return btnAvviaRicerca;
	}

	/**
	 * Dopo che l'utente ha selezionato il bottone per avviare
	 * la ricerca , il sistema fa prima un controllo preliminare
	 * del fatto che sia stato selezionato e aggiunto almeno un filtro
	 * in caso affermativo procede a popolare una lista di Stringhe
	 * con il contenuto della JList FiltriSelezionati , per poi
	 * invocare il metodo popolaComboBox() che è descritto più
	 * giù nel codice , nel caso in cui il ComboBox dovesse risultare vuoto
	 * a seguito del filtraggio si evince che non erano presenti prodotti
	 * che rispondessero ai filtri selezionati
	 */
	private void avviaRicerca() {
		List<String> filtriSelezionati  = new ArrayList<String>();
		if(modelListFiltriSelezionati.isEmpty()) {
			JOptionPane.showMessageDialog(null, "La ricerca è stata avviata senza aver selezionato dei filtri", "Errore filtro", JOptionPane.ERROR_MESSAGE);
		}
		else {
			getFiltriList(filtriSelezionati);
			popolaComboBox(filtriSelezionati,tipoFiltro);
			
			if(getComboBoxProdotti().getItemCount() == 0){
				JOptionPane.showMessageDialog(null, "Nessun prodotto per questa categoria!", "Errore filtro", JOptionPane.ERROR_MESSAGE);
			}else{
				getComboBoxProdotti().setSelectedIndex(0);
			}
			
		}
	}
	
	private void getFiltriList(List<String> filtri){
		for (int i = 0; i < modelListFiltriSelezionati.getSize(); i++) {
		    filtri.add((String)modelListFiltriSelezionati.getElementAt(i));
		}
	}

	/**
	 *Il seguente metodo serve semplicemente a popolare
	 * le JList dei filtri in funzione delle Categorie o
	 * Collocazioni contemplate fino al momento di avvio del
	 * programma all'intero del magazzino , restituendo degliù
	 * Array di Stringhe alla Boundary, si utilizza il discriminante
	 * "tipoFiltro" per capire se ottenre le Categorie o le Collocazioni
	 * disponibili
	 */

	private String[] getFiltriDisponibili() {
	    if(tipoFiltro.equalsIgnoreCase("categoria")) {
	    	return ControllerGestione.getCategorieDisponibili();
	    }else {
	    	return ControllerGestione.getCollocazioniDisponibili();
	    }
	}
	
	private JList getListFiltriDisponibili() {
		if (listFiltriDisponibili == null) {
			listFiltriDisponibili = new JList();
			listFiltriDisponibili.setBorder(new TitledBorder(null, "Filtri Disponibili", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			listFiltriDisponibili.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			modelListFiltriDisponibili = new DefaultListModel();
			listFiltriDisponibili.setModel(modelListFiltriDisponibili);
		}
		return listFiltriDisponibili;
	}
	private JList getListFiltriSelezionati() {
		if (listFiltriSelezionati == null) {
			listFiltriSelezionati = new JList();
			listFiltriSelezionati.setBorder(new TitledBorder(null, "Filtri Selezionati", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			listFiltriSelezionati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			modelListFiltriSelezionati = new DefaultListModel();
			listFiltriSelezionati.setModel(modelListFiltriSelezionati);
		}
		return listFiltriSelezionati;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(Color.WHITE);
			pnCentro.setLayout(new BorderLayout(0, 20));
			
			JPanel pnTendina = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			pnTendina.setBackground(Color.WHITE);
			pnTendina.add(getLblProdottiFiltrati());
			pnTendina.add(getComboBoxProdotti());
			
			pnCentro.add(pnTendina, BorderLayout.NORTH);
			pnCentro.add(getPnDettagli(), BorderLayout.CENTER);
		}
		return pnCentro;
	}

	private JLabel getLblProdottiFiltrati() {
		if (lblProdottiFiltrati == null) {
			lblProdottiFiltrati = new JLabel("Prodotti trovati:");
			lblProdottiFiltrati.setFont(new Font("SansSerif", Font.BOLD, 14));
		}
		return lblProdottiFiltrati;
	}

	private JComboBox<String> getComboBoxProdotti() {
		if (comboBoxProdotti == null) {
			comboBoxProdotti = new JComboBox<>();
			comboBoxProdotti.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aggiornaVista();
				}
			});
			comboBoxProdotti.setPreferredSize(new Dimension(300, 35));
			comboBoxProdotti.setFont(new Font("SansSerif", Font.PLAIN, 14));
		}
		return comboBoxProdotti;
	}

	private void aggiornaVista() {
		if(getComboBoxProdotti().getSelectedItem() == null) {
			
		}else {
			String selezione = getComboBoxProdotti().getSelectedItem().toString();
			
			String[] parti = selezione.split(" - ");
			Long idProdotto = Long.parseLong(parti[0]);
			
			 Map<String, String> dettagli = ControllerGestione.getDettagliProdotto(idProdotto);
			
				getLbliDProdottoVisualizza().setText(dettagli.get("id"));
				getLblNomeProdottoVisualizza().setText(dettagli.get("nome"));
				getLblDescrizioneProdottoVisualizza().setText(dettagli.get("descrizione"));
				getLblQuantitaProdottoVisualizza().setText(dettagli.get("quantita"));
				getLblSogliaProdottoVisualizza().setText(dettagli.get("soglia"));
			
		}
	}

	private JPanel getPnDettagli() {
		if (pnDettagli == null) {
			pnDettagli = new JPanel();
			pnDettagli.setBackground(Color.WHITE);
			pnDettagli.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
				"Dettagli Prodotto", 
				javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, 
				new Font("SansSerif", Font.BOLD, 14), Color.DARK_GRAY));
			
			GridBagLayout gbl = new GridBagLayout();
			gbl.columnWidths = new int[]{150, 250, 0};
			gbl.rowHeights = new int[]{30, 30, 30, 30, 30, 0};
			gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			pnDettagli.setLayout(gbl);

			GridBagConstraints gbc_lblIdTitolo = new GridBagConstraints();
			gbc_lblIdTitolo.anchor = GridBagConstraints.EAST;
			gbc_lblIdTitolo.insets = new Insets(10, 10, 10, 15);
			gbc_lblIdTitolo.gridx = 0; gbc_lblIdTitolo.gridy = 0;
			pnDettagli.add(getLbliDProdotto(), gbc_lblIdTitolo);

			GridBagConstraints gbc_lblIdVal = new GridBagConstraints();
			gbc_lblIdVal.anchor = GridBagConstraints.WEST;
			gbc_lblIdVal.insets = new Insets(10, 0, 10, 10);
			gbc_lblIdVal.gridx = 1; gbc_lblIdVal.gridy = 0;
			pnDettagli.add(getLbliDProdottoVisualizza(), gbc_lblIdVal);

			GridBagConstraints gbc_lblNomeTitolo = new GridBagConstraints();
			gbc_lblNomeTitolo.anchor = GridBagConstraints.EAST;
			gbc_lblNomeTitolo.insets = new Insets(10, 10, 10, 15);
			gbc_lblNomeTitolo.gridx = 0; gbc_lblNomeTitolo.gridy = 1;
			pnDettagli.add(getLblNomeProdotto(), gbc_lblNomeTitolo);

			GridBagConstraints gbc_lblNomeVal = new GridBagConstraints();
			gbc_lblNomeVal.anchor = GridBagConstraints.WEST;
			gbc_lblNomeVal.insets = new Insets(10, 0, 10, 10);
			gbc_lblNomeVal.gridx = 1; gbc_lblNomeVal.gridy = 1;
			pnDettagli.add(getLblNomeProdottoVisualizza(), gbc_lblNomeVal);

			GridBagConstraints gbc_lblDescTitolo = new GridBagConstraints();
			gbc_lblDescTitolo.anchor = GridBagConstraints.EAST;
			gbc_lblDescTitolo.insets = new Insets(10, 10, 10, 15);
			gbc_lblDescTitolo.gridx = 0; gbc_lblDescTitolo.gridy = 2;
			pnDettagli.add(getLblDescrizioneProdotto(), gbc_lblDescTitolo);

			GridBagConstraints gbc_lblDescVal = new GridBagConstraints();
			gbc_lblDescVal.anchor = GridBagConstraints.WEST;
			gbc_lblDescVal.insets = new Insets(10, 0, 10, 10);
			gbc_lblDescVal.gridx = 1; gbc_lblDescVal.gridy = 2;
			pnDettagli.add(getLblDescrizioneProdottoVisualizza(), gbc_lblDescVal);

			GridBagConstraints gbc_lblQtaTitolo = new GridBagConstraints();
			gbc_lblQtaTitolo.anchor = GridBagConstraints.EAST;
			gbc_lblQtaTitolo.insets = new Insets(10, 10, 10, 15);
			gbc_lblQtaTitolo.gridx = 0; gbc_lblQtaTitolo.gridy = 3;
			pnDettagli.add(getLblQuantitaProdotto(), gbc_lblQtaTitolo);

			GridBagConstraints gbc_lblQtaVal = new GridBagConstraints();
			gbc_lblQtaVal.anchor = GridBagConstraints.WEST;
			gbc_lblQtaVal.insets = new Insets(10, 0, 10, 10);
			gbc_lblQtaVal.gridx = 1; gbc_lblQtaVal.gridy = 3;
			pnDettagli.add(getLblQuantitaProdottoVisualizza(), gbc_lblQtaVal);

			GridBagConstraints gbc_lblSogliaTitolo = new GridBagConstraints();
			gbc_lblSogliaTitolo.anchor = GridBagConstraints.EAST;
			gbc_lblSogliaTitolo.insets = new Insets(10, 10, 10, 15);
			gbc_lblSogliaTitolo.gridx = 0; gbc_lblSogliaTitolo.gridy = 4;
			pnDettagli.add(getLblSogliaProdotto(), gbc_lblSogliaTitolo);

			GridBagConstraints gbc_lblSogliaVal = new GridBagConstraints();
			gbc_lblSogliaVal.anchor = GridBagConstraints.WEST;
			gbc_lblSogliaVal.insets = new Insets(10, 0, 10, 10);
			gbc_lblSogliaVal.gridx = 1; gbc_lblSogliaVal.gridy = 4;
			pnDettagli.add(getLblSogliaProdottoVisualizza(), gbc_lblSogliaVal);
		}
		return pnDettagli;
	}

	private JLabel getLbliDProdotto() {
		if (lbliDProdotto == null) {
			lbliDProdotto = new JLabel("ID:");
			lbliDProdotto.setFont(new Font("SansSerif", Font.BOLD, 14));
		}
		return lbliDProdotto;
	}
	private JLabel getLbliDProdottoVisualizza() {
		if (lbliDProdottoVisualizza == null) {
			lbliDProdottoVisualizza = new JLabel("-");
			lbliDProdottoVisualizza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		}
		return lbliDProdottoVisualizza;
	}

	private JLabel getLblNomeProdotto() {
		if (lblNomeProdotto == null) {
			lblNomeProdotto = new JLabel("Nome:");
			lblNomeProdotto.setFont(new Font("SansSerif", Font.BOLD, 14));
		}
		return lblNomeProdotto;
	}
	private JLabel getLblNomeProdottoVisualizza() {
		if (lblNomeProdottoVisualizza == null) {
			lblNomeProdottoVisualizza = new JLabel("-");
			lblNomeProdottoVisualizza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		}
		return lblNomeProdottoVisualizza;
	}

	private JLabel getLblDescrizioneProdotto() {
		if (lblDescrizioneProdotto == null) {
			lblDescrizioneProdotto = new JLabel("Descrizione:");
			lblDescrizioneProdotto.setFont(new Font("SansSerif", Font.BOLD, 14));
		}
		return lblDescrizioneProdotto;
	}
	private JLabel getLblDescrizioneProdottoVisualizza() {
		if (lblDescrizioneProdottoVisualizza == null) {
			lblDescrizioneProdottoVisualizza = new JLabel("-");
			lblDescrizioneProdottoVisualizza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		}
		return lblDescrizioneProdottoVisualizza;
	}

	private JLabel getLblQuantitaProdotto() {
		if (lblQuantitaProdotto == null) {
			lblQuantitaProdotto = new JLabel("Quantità:");
			lblQuantitaProdotto.setFont(new Font("SansSerif", Font.BOLD, 14));
		}
		return lblQuantitaProdotto;
	}
	private JLabel getLblQuantitaProdottoVisualizza() {
		if (lblQuantitaProdottoVisualizza == null) {
			lblQuantitaProdottoVisualizza = new JLabel("-");
			lblQuantitaProdottoVisualizza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		}
		return lblQuantitaProdottoVisualizza;
	}

	private JLabel getLblSogliaProdotto() {
		if (lblSogliaProdotto == null) {
			lblSogliaProdotto = new JLabel("Soglia Minima:");
			lblSogliaProdotto.setFont(new Font("SansSerif", Font.BOLD, 14));
		}
		return lblSogliaProdotto;
	}
	private JLabel getLblSogliaProdottoVisualizza() {
		if (lblSogliaProdottoVisualizza == null) {
			lblSogliaProdottoVisualizza = new JLabel("-");
			lblSogliaProdottoVisualizza.setFont(new Font("SansSerif", Font.PLAIN, 14));
		}
		return lblSogliaProdottoVisualizza;
	}

	private JPanel getPnSud() {
		if (pnSud == null) {
			pnSud = new JPanel();
			pnSud.setBackground(Color.WHITE);
			pnSud.setLayout(new BorderLayout(0, 0));
			
			JPanel pnOvest = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			pnOvest.setBackground(Color.WHITE);
			pnOvest.add(getBtnIndietro());
			
			JPanel pnEst = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			pnEst.setBackground(Color.WHITE);
			pnEst.add(getBtnFiltraPer());
			
			pnSud.add(pnOvest, BorderLayout.WEST);
			pnSud.add(pnEst, BorderLayout.EAST);
		}
		return pnSud;
	}

	private JButton getBtnIndietro() {
		if (btnIndietro == null) {
			btnIndietro = new JButton("Torna alla Dashboard");
			btnIndietro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parentFrame.switchPanel(pannelloDiProvenienza);
					resetCampi();
				}
			});
			btnIndietro.setPreferredSize(new Dimension(170, 35));
			btnIndietro.setFont(new Font("SansSerif", Font.BOLD, 13));
			btnIndietro.setBackground(Color.WHITE);
			btnIndietro.setForeground(new Color(108, 117, 125));
		}
		return btnIndietro;
	}

	private JButton getBtnFiltraPer() {
		if (btnFiltraPer == null) {
			btnFiltraPer = new JButton("Passa a Ricerca per Posizione");
			btnFiltraPer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aggiornaUI();
					resetCampi();
				}
			});
			btnFiltraPer.setPreferredSize(new Dimension(230, 35));
			btnFiltraPer.setFont(new Font("SansSerif", Font.BOLD, 13));
			btnFiltraPer.setBackground(Color.WHITE);
			btnFiltraPer.setForeground(new Color(23, 162, 184));
		}
		return btnFiltraPer;
	}
	
	private void aggiornaUI() {
		if(tipoFiltro.equalsIgnoreCase("categoria")) {
			getBtnFiltraPer().setText("Passa a Ricerca per Categoria");
			tipoFiltro = "posizione";
		}else {
			getBtnFiltraPer().setText("Passa a Ricerca per Posizione");
			tipoFiltro = "categoria";
		}
	}

	/**
	 *Coerentemente a quanto commentato nel MainFrame , questo metodo serve
	 * a tenere traccia del Panel da cui si proveniva (la dashboard operatore
	 * o responsabile) per poi consentire di tornare indietro evitando di far ritornare
	 * un operatore alla dashboard del responsabile e garantendogli dunque dei privilegi
	 * superiori
	 */

	public void setPannelloDiProvenienza(String provenienza) {
	    this.pannelloDiProvenienza = provenienza;
	}

	/**
	 *Questo è il principale metodo usato per gestire e consentire
	 * il popolamento del combobox con i prodotti che rispettano i
	 * filtri selezionati prima dell'avvio della ricerca,
	 * il metodo invoca il ControllerGestione passando una lista di Stringhe
	 * (che corrispondono ai filtri) e il tipo di filtraggio che si sta eseguendo(per
	 * categoria o per posizione) , si occuperanno poi i livelli inferiori di gestire
	 * le operazioni di filtraggio e ricerca
	 */

	private void popolaComboBox(List<String> filtri,String tipoFiltro){
		if(tipoFiltro.equalsIgnoreCase("categoria")){
			getComboBoxProdotti().setModel(new DefaultComboBoxModel(ControllerGestione.getElencoProdottiFiltratiPerCategoria(filtri,tipoFiltro)));
		}else {
			getComboBoxProdotti().setModel(new DefaultComboBoxModel(ControllerGestione.getElencoProdottiFiltratiPerPosizione(filtri, tipoFiltro)));
		}
		
	}

	/**
	 * metodo utile a resettare tutti i campi che vengono dinamicamente popolati
	 * nel Panel in maniera tale che se si dovesse uscire e poi rientrare , l'utente
	 * trovi tutto resettato e pronto a un nuovo utilizzo , il metodo è publico cosi
	 * che possa essere invocato dal MainFrame
	 */

	public void resetCampi() {
		popolaListaFiltriDisponibili();
		getComboBoxProdotti().removeAllItems();
		getLbliDProdottoVisualizza().setText("-");
		getLblNomeProdottoVisualizza().setText("-");
		getLblDescrizioneProdottoVisualizza().setText("-");
		getLblQuantitaProdottoVisualizza().setText("-");
		getLblSogliaProdottoVisualizza().setText("-");
	}

	/**
	 * Metodo che si occupa di popolare la JList
	 * FiltriDisponibili con le categorie o le collocazioni
	 * attualmente contemplate nel Magazzino
	 */

	private void popolaListaFiltriDisponibili() {
		modelListFiltriSelezionati.removeAllElements();
		modelListFiltriDisponibili.removeAllElements();
		
		for(String filtro : getFiltriDisponibili()) {
			modelListFiltriDisponibili.addElement(filtro);
		}
		
	
	}

}