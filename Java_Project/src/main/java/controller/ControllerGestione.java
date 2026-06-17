package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Categoria;
import entity.Collocazione;
import entity.GestoreUtenti;
import entity.Prodotto;
import entity.GestoreMovimenti;
import entity.GestoreProdotti;
import entity.TipoMovimentoProdotto;
import entity.Utente;
import entity.EsitoMovimenti;

public class ControllerGestione {

    public static final int MANCATA_DISPONIBILITA = 1;
    public static final int SUCCESSO_MOVIMENTO = 2;
    public static final int MOVIMENTO_FALLITO = 3;
	public static final int SUCCESSO_MOVIMENTO_CON_NOTIFICA = 4;

	public enum EsitoModifica {
		SUCCESSO,
		NOME_DUPLICATO,
		ERRORE,
		SUCCESSO_CON_NOTIFICA
	}

	public enum EsitoAccesso{
		LOGIN_FALLITO,
		LOGIN_CON_SUCCESSO,
		UTENTE_INESISTENTE
	}

	/**
	 * Questo metodo invocato dall Boundary , si occupa di ricevere le credenziali e il ruolo
	 * selezionati dall'Utente nella schermata di login ed effettuare tutta una serie di controlli
	 * su queste informazioni passando per il GestoreUtenti (si verifica che in primo luogo
	 * l'utente esista , si verifica poi che le credenziali e il ruolo selezionato siano corretti).
	 */
    public static EsitoAccesso verificaLogin(String username, String password,
	boolean isResp) {

	GestoreUtenti gestore = new GestoreUtenti();

	Utente utente = gestore.cercaUtentePerUsername(username);

	if(utente == null){
		return EsitoAccesso.UTENTE_INESISTENTE;
	}else if(gestore.verificaRuolo(utente,isResp) && gestore.verificaCredenziali(utente,password)){
		return EsitoAccesso.LOGIN_CON_SUCCESSO;
	}else{
		return EsitoAccesso.LOGIN_FALLITO;
	}
    }

	/**
	Questo metodo raccoglie i dati necessari alla registrazione
	di un prodotto dalla Boundary e invia quest'ultimi verso la classe
	"GestoreMovimenti" del package Entity, che avrà il compito di occuparsi
	della creazione dell'operazione di carico/scarico
	**/
	public static int salvaMovimento(String prodotto, Integer unita,
	                                 String tipo, LocalDate data) {

		GestoreMovimenti regMov = new GestoreMovimenti();
		//Conversione Stringa nell enumerativo "TipoMovimento"
		TipoMovimentoProdotto tipoMovimento = TipoMovimentoProdotto
				.valueOf(tipo);

		EsitoMovimenti esito = regMov.registraMovimentoProdotto(prodotto, unita,
				tipoMovimento, data);

		if (esito == EsitoMovimenti.MOVIMENTO_FALLITO) {
			return MOVIMENTO_FALLITO;
		} else if (esito == EsitoMovimenti.MANCATA_DISPONIBILITA) {
			return MANCATA_DISPONIBILITA;
		} else if (esito == EsitoMovimenti.SUCCESSO_MOVIMENTO_CON_NOTIFICA){
			return SUCCESSO_MOVIMENTO_CON_NOTIFICA;
		}else
			return SUCCESSO_MOVIMENTO;

	}

	/**
	Il metodo getElencoProdotti(), dopo essersi interfacciato con un oggetto
	della classe "GestoreProdotti", invia verso la Boundary un array di
	stringhe contenenti tutti i prodotti registrati nel sistema
	**/
    public static String[] getElencoProdotti() {
	List<String> elencoNomiProdotti = new ArrayList<>();

	GestoreProdotti regProd = new GestoreProdotti();
	List<Prodotto> listaProdotti = regProd.recuperaProdottiInMagazzino();

	return regProd.recuperaProdottiInStringhe(elencoNomiProdotti,listaProdotti);
    }

	/**
	 * I due successivi metodi si occupano di fare la stessa cosa , ma ovviamente
	 * il primo verrà invocato nel momento in cui si stia cercando di filtrare i prodotti
	 * per categoria , il secondo per Collocazione. I metodi si occupano di ricevere una Lista di Stringhe,
	 * corrispondenti ai filtri scelti , dalla Boundary più la tipologia di filtro per poi invocare
	 * i metodi del GestoreProdotti per recuperare sotto forma di vettore di Stringhe i prodotti che
	 * rispondono a quella ricerca per filtro
	 */

    public static String[] getElencoProdottiFiltratiPerCategoria(List<String> filtri,
	String tipoFiltro) {

		List<String> elencoNomiProdotti = new ArrayList<>();
		GestoreProdotti gesProd = new GestoreProdotti();

		List<Categoria> categorieEnum = new ArrayList<>();

		for (String catStr : filtri) {
			categorieEnum.add(Categoria.valueOf(catStr));
		}

		List<Prodotto> listaProdotti = gesProd.eseguiRicercaFiltri(tipoFiltro, categorieEnum);

		return gesProd.recuperaProdottiInStringhe(elencoNomiProdotti, listaProdotti);
	}


	public static String[] getElencoProdottiFiltratiPerPosizione(List<String> filtri,
	                                                             String tipoFiltro) {
		List<String> elencoNomiProdotti = new ArrayList<>();
		GestoreProdotti regProd = new GestoreProdotti();

		List<Collocazione> categoriePosizione = new ArrayList<>();

		for (String catStr : filtri) {
			categoriePosizione.add(Collocazione.valueOf(catStr));
		}

		List<Prodotto> listaProdotti = regProd.eseguiRicercaFiltri(tipoFiltro, categoriePosizione);

		return regProd.recuperaProdottiInStringhe(elencoNomiProdotti, listaProdotti);
	}

	/**
	 * Similmente a quanto fanno i metodi sovrastanti , questo metodo si occupa di ricevere dalla Boundary
	 * l'id del prodotto selezionato (come in un ComboBox) per poi ricercare le informazioni di quest'
	 * ultimo attraverso l'ausilio e l'invocazione dei metodi del GestoreProdotti
	 */
	public static Map<String, String> getDettagliProdotto(Long id) {

		GestoreProdotti gesProd = new GestoreProdotti();
		Prodotto p = gesProd.cercaProdottoPerId(id);

		if (p == null) return null;

		Map<String, String> dettagli = new LinkedHashMap<>();
		dettagli.put("id",          String.valueOf(p.getId()));
		dettagli.put("nome",        p.getNome());
		dettagli.put("descrizione", p.getDescrizione());
		dettagli.put("quantita",    String.valueOf(p.getQuantita()));
		dettagli.put("soglia",      String.valueOf(p.getSoglia()));
		dettagli.put("categoria",   p.getCategoria().name());
		dettagli.put("posizione",   p.getPosizione().name());
		return dettagli;
	}

	/**
	 * Metodo usato per ricevere l elenco dei prodotti sotto scorta
	 * usando una lista e chiamando il GestoreProdotti per la stringa da
	 * far visualizzare e aggiungendola alla lista
	 *
	 * @return elenco dei prodotti sotto scorta
	 */
	public static List<String> getElencoProdottiSottoScorta() {

		List<String> listaProdottiSottoScorta = new ArrayList<>();

		GestoreProdotti regProd = new GestoreProdotti();

		List<Prodotto> elencoProdotti = regProd.recuperaProdottiInMagazzino();

		for (Prodotto p : elencoProdotti) {
			if (p.getSottoScorta()) {
				// per ridurre accoppiamento
				String riga = regProd.prodottoSottoScortatoString(p);
				listaProdottiSottoScorta.add(riga);
			}
		}
		return listaProdottiSottoScorta;
	}

	/**
	 * Metodo utilizzato per creare la mail da mandare al responsabile
	 *
	 * @param prodotto prodotto da cui prendere i dati
	 * @return ritorna la mail da mandare
	 */
	public static String notificaSottoScorta(String prodotto){
		GestoreProdotti gestoreProdotti = new GestoreProdotti();
		Prodotto prod = gestoreProdotti.cercaProdottoPerNome(prodotto);
		String email = "Attenzione: il prodotto " + prod;
		if (prod.getQuantita() == 0){
			email += " è esaurito";
		}else
			email += " è in esaurimento quantità rimanente: " + prod.getQuantita();

		return email;
	}

	/**
	 * Crea e persiste un nuovo prodotto in magazzino.
	 *
	 * Riceve i dati grezzi dalla UI (Stringhe per categoria e posizione)
	 * e si occupa della conversione nei tipi di dominio (enum Categoria e
	 * Collocazione) prima di delegare la persistenza a GestoreProdotti.
	 *
	 * @return true se il salvataggio è andato a buon fine, false altrimenti
	 */
	public static boolean aggiungiProdotto(String nome, String descrizione, int quantita, int soglia, String categoriaStr, String posizioneStr) {

		// Conversione dei tipi grezzi (String) nei tipi di Dominio (Enum)
		Categoria categoria = Categoria.valueOf(categoriaStr);
		Collocazione posizione = Collocazione.valueOf(posizioneStr);

		GestoreProdotti regProd = new GestoreProdotti();
		return regProd.registraNuovoProdotto(nome, descrizione, quantita, soglia, categoria, posizione);
	}


	/**
	 * I due metodi che seguono sono usati per restituire verso la Boundary
	 * sotto forma di vettore di Stringhe tutte le Categorie o le Collocazioni disponibili
	 * nelle classi Enum presenti a livello inferiore(Entity) cosi da poter popolare eventuali
	 * sezioni di filtraggio/selezione.
	 */

	public static String[] getCategorieDisponibili() {
		return new GestoreProdotti().getCategorieDisponibili();
	}

	public static String[] getCollocazioniDisponibili() {
		return new GestoreProdotti().getCollocazioniDisponibili();
	}

	/**
	 * Aggiorna i dati di un prodotto esistente identificato dal suo ID.
	 *
	 * Verifica prima l'univocità del nuovo nome escludendo il prodotto stesso
	 * dal controllo (un prodotto può mantenere il proprio nome invariato).
	 * Converte le stringhe di categoria e posizione negli enum di dominio,
	 * quindi delega la modifica a GestoreProdotti.
	 *
	 * @return SUCCESSO se la modifica è avvenuta correttamente,
	 *         NOME_DUPLICATO se il nome è già usato da un altro prodotto,
	 *         ERRORE se la persistenza ha fallito
	 */	public static EsitoModifica modificaProdottoPerId(
			Long id,
			String nuovoNome,
			String descrizione,
			int soglia,
			String categoriaStr,
			String posizioneStr) {

		if (!isNomeUnivoco(nuovoNome, id)) return EsitoModifica.NOME_DUPLICATO;

		Categoria categoria = Categoria.valueOf(categoriaStr);
		Collocazione posizione = Collocazione.valueOf(posizioneStr);

		GestoreProdotti regProd = new GestoreProdotti();

		GestoreMovimenti gestoreMovimenti = new GestoreMovimenti();

		boolean modificaEffettuata = regProd.modificaProdotto(id, nuovoNome, descrizione, soglia, categoria, posizione);

		boolean notifica = gestoreMovimenti.controlloSottoScorte(regProd.cercaProdottoPerId(id));

		if (notifica){
			return EsitoModifica.SUCCESSO_CON_NOTIFICA;
		}else
			return modificaEffettuata ? EsitoModifica.SUCCESSO : EsitoModifica.ERRORE;
	}

	/**
	 * Verifica che il nome fornito non sia già assegnato a un altro prodotto.
	 *
	 * Viene fornito un idEscluso, il prodotto con quell'ID viene ignorato
	 * nel controllo: questo permette di salvare un prodotto mantenendo il
	 * proprio nome invariato senza generare un falso NOME_DUPLICATO.
	 *
	 * @return true se il nome è disponibile, false se è già usato da un altro prodotto
	 */
	public static boolean isNomeUnivoco(String nome, Long idEscluso) {
		GestoreProdotti regProd = new GestoreProdotti();
		return regProd.isNomeUnivoco(nome, idEscluso);
	}


}