package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controller.ControllerGestione;

public class VisualizzaSottoScorta extends JPanel {

    private static final long serialVersionUID = 1L;
    private String pannelloDiProvenienza;
    private MainFrame parentFrame;

    private JPanel pnCentro;
    private JPanel pnSud;
    private JScrollPane scrollPaneProdottiSottoScorta;
    private JList<String> listProdottiSottoScorta;
    private DefaultListModel<String> modelListProdottiSottoScorta = null;

    private JPanel pnBottoniEst;
    private JPanel pnBottoniOvest;
    private JButton btnIndietro;

    /**
     * Create the panel.
     */
    public VisualizzaSottoScorta(MainFrame parentFrame) {
	this.parentFrame = parentFrame;

	setBackground(Color.WHITE);
	setBorder(new EmptyBorder(25, 25, 25, 25));
	setLayout(new BorderLayout(0, 25));
	add(getPnCentro(), BorderLayout.CENTER);
	add(getPnSud(), BorderLayout.SOUTH);
    }

    public void setPannelloDiProvenienza(String provenienza) {
	this.pannelloDiProvenienza = provenienza;
    }

    private JPanel getPnCentro() {
	if (pnCentro == null) {
	    pnCentro = new JPanel();
	    pnCentro.setBackground(Color.WHITE);
	    pnCentro.setLayout(new BorderLayout(0, 0));
	    pnCentro.add(getScrollPaneProdottiSottoScorta(),
		BorderLayout.CENTER);
	}
	return pnCentro;
    }

    private JScrollPane getScrollPaneProdottiSottoScorta() {
	if (scrollPaneProdottiSottoScorta == null) {
	    scrollPaneProdottiSottoScorta = new JScrollPane();
	    scrollPaneProdottiSottoScorta.setBackground(Color.WHITE);
	    scrollPaneProdottiSottoScorta.getViewport()
		.setBackground(Color.WHITE);
	    scrollPaneProdottiSottoScorta
		.setBorder(BorderFactory.createEmptyBorder());
	    scrollPaneProdottiSottoScorta
		.setViewportView(getListProdottiSottoScorta());
	    scrollPaneProdottiSottoScorta
		.setBorder(BorderFactory.createTitledBorder(
		    BorderFactory.createLineBorder(new Color(200, 200, 200), 1,
			true),
		    "Prodotti attualmente Sotto Scorta", TitledBorder.LEADING,
		    TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 16),
		    Color.DARK_GRAY));
	}
	return scrollPaneProdottiSottoScorta;
    }

    private JList<String> getListProdottiSottoScorta() {
	if (listProdottiSottoScorta == null) {
	    listProdottiSottoScorta = new JList<>();
	    listProdottiSottoScorta
		.setFont(new Font("SansSerif", Font.PLAIN, 16));
	    listProdottiSottoScorta.setFixedCellHeight(50);
	    listProdottiSottoScorta
		.setSelectionBackground(new Color(255, 240, 240));
	    listProdottiSottoScorta.setSelectionForeground(Color.BLACK);
	    javax.swing.DefaultListCellRenderer renderer = (javax.swing.DefaultListCellRenderer) listProdottiSottoScorta
		.getCellRenderer();
	    renderer.setBorder(BorderFactory.createCompoundBorder(
		BorderFactory.createMatteBorder(0, 0, 1, 0,
		    new Color(240, 240, 240)),
		BorderFactory.createEmptyBorder(0, 20, 0, 0)));
	    modelListProdottiSottoScorta = new DefaultListModel<>();
	    listProdottiSottoScorta.setModel(modelListProdottiSottoScorta);
	    aggiornaDati();
	}
	return listProdottiSottoScorta;
    }

    public void aggiornaDati() {
	modelListProdottiSottoScorta.clear();

	List<String> prodottiSottoScorta = ControllerGestione
	    .getElencoProdottiSottoScorta();

	if (prodottiSottoScorta != null && !prodottiSottoScorta.isEmpty()) {
	    for (String p : prodottiSottoScorta) {
		modelListProdottiSottoScorta.addElement(p);
	    }
	} else {
	    modelListProdottiSottoScorta
		.addElement("Nessun prodotto sotto scorta al momento.");
	}

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
	}
	return pnBottoniEst;
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
	    btnIndietro.setPreferredSize(new Dimension(140, 40));
	    btnIndietro.addActionListener(e -> {
		parentFrame.switchPanel("DASH_RESP");
	    });
	    btnIndietro.setForeground(new Color(108, 117, 125));
	    btnIndietro.setFont(new Font("SansSerif", Font.BOLD, 14));
	    btnIndietro.setBackground(Color.WHITE);
	    btnIndietro.setFocusPainted(false);
	}
	return btnIndietro;
    }

	/**
	 * Metodo per aggiurnare la gui per la visualizzazione
	 * dei prodotti in sotto scorta
	 */
	public void refresh(){
		aggiornaDati();
	}
}