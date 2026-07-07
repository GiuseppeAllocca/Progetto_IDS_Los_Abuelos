package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.GestorePersistenza;

public class GestoreProdotti {

    private GestorePersistenza gestorePersistenza;

    public GestoreProdotti() {

		gestorePersistenza = new GestorePersistenza();
    }

	/**
	 * Istanzia un nuovo oggetto Prodotto con i dati forniti e lo persiste
	 * tramite GestorePersistenza.
	 *
	 * @return true se il salvataggio JPA è andato a buon fine, false altrimenti
	 */
    public boolean registraNuovoProdotto(String nomeProdotto,
	String descrizione, int quantita, int soglia, Categoria categoria,
	Collocazione posizione) {

	Prodotto prodotto = new Prodotto(nomeProdotto, descrizione, quantita,
	    soglia, categoria, posizione);


	return gestorePersistenza.salva(prodotto);

    }

	/**
	 * Aggiorna i campi di un prodotto esistente e ricalcola il flag sottoScorta.
	 *
	 * Recupera il prodotto dal DB tramite il suo ID, applica i nuovi valori
	 * e aggiorna sottoScorta confrontando la quantità corrente con la nuova soglia.
	 * Il ricalcolo è necessario perché una variazione della soglia può far
	 * passare il prodotto da "in scorta" a "sotto scorta" (o viceversa)
	 * senza che la quantità fisica sia cambiata.
	 *
	 * @return true se l'aggiornamento JPA è andato a buon fine, false se il prodotto non esiste
	 */
	public boolean modificaProdotto(Long id, String nuovoNome, String nuovaDescrizione, int nuovaSoglia, Categoria nuovaCat, Collocazione nuovaPos) {

		Prodotto prodotto = cercaProdottoPerId(id);

		if (prodotto == null) {
			return false;
		}

		prodotto.setNome(nuovoNome);
		prodotto.setDescrizione(nuovaDescrizione);
		prodotto.setCategoria(nuovaCat);
		prodotto.setPosizione(nuovaPos);
		prodotto.setSoglia(nuovaSoglia);

		GestoreMovimenti gestoreMovimenti = new GestoreMovimenti();
		gestoreMovimenti.controlloSottoScorte(prodotto);

		Prodotto aggiornato = gestorePersistenza.aggiorna(prodotto);

		return aggiornato != null;
	}

    public List<Prodotto> recuperaProdottiInMagazzino() {
	// Passiamo una mappa vuota a cercaPerCampi per ottenere tutti i record
	// senza filtri
	return gestorePersistenza.cercaPerCampi(Prodotto.class,
	    Collections.emptyMap());
    }

	/**
	 * metodo usato per effettuare il parsing in un formato utilizzabile dalla Boundary
	 * dei Prodotti per uno dei svariati usi (come la visualizzazione di prodotti filtrati
	 * o della totalità dei prodotti presenti in magazzino).
	 */

	public String[] recuperaProdottiInStringhe(List<String> elencoNomiProdotti,List<Prodotto> listaProdotti){

		for (Prodotto p : listaProdotti) {
			elencoNomiProdotti.add(p.getId() + " - " + p.getNome());
		}

		return elencoNomiProdotti.toArray(new String[elencoNomiProdotti.size()]);
	}

	/**
	 * Metodo usato per restituire una lista di Prodotti che rispondano alla richiesta
	 * di filtraggio effettuata , il metodo è tipizzato per essere usato sia nel caso
	 * in cui si stia cercando di filtrare i Prodotti per categoria sia che si stia cercando
	 * di filtrarli per Collocazione
	 */

	public <T> List<Prodotto> eseguiRicercaFiltri(String chiaveMappa, List<T> filtri) {
		List<Prodotto> risultatiFinali = new ArrayList<>();

		if (filtri == null || filtri.isEmpty()) {
			return risultatiFinali;
		}

		for (T elemento : filtri) {
			Map<String, Object> mappaFiltro = new HashMap<>();
			mappaFiltro.put(chiaveMappa, elemento);

			List<Prodotto> parziali = gestorePersistenza.cercaPerCampi(Prodotto.class, mappaFiltro);
			risultatiFinali.addAll(parziali);
		}

		return risultatiFinali;
	}

    public Prodotto cercaProdottoPerId(Long id) {
		return gestorePersistenza.trovaPerId(Prodotto.class, id);
    }

	/**
	Il seguente metodo sfrutta il cercaPerCampo() messo a disposizione
	dal "GestoreProdotti" per effettuare una ricerca in base al nominativo dei prodotti
	**/
	public Prodotto cercaProdottoPerNome(String nome) {
		List<Prodotto> risultati = gestorePersistenza.cercaPerCampo(Prodotto.class, "nome", nome);

		if (risultati != null && !risultati.isEmpty()) {
			return risultati.get(0); // Restituisce il prodotto se esiste
		}
		return null;
	}

	/**
	 * Metodo usato per usare i parametri del prodotto per la sua
	 * visualizzazione in caso sia considerato prodotto in sotto scorta
	 *
	 * @param p è il prodotto che si deve visualizzare
	 * @return una stinga per la visualizzazione del prodotto sotto scorta
	 */
    public String prodottoSottoScortatoString(Prodotto p) {
	String riga = "[" + p.getCategoria() + "] " + p.getNome()
	    + " - Disponibilità: " + p.getQuantita() + " (Soglia: "
	    + p.getSoglia() + ")";
	return riga;
    }

	/**
	 * Verifica che il nome fornito non sia già assegnato a un altro prodotto.
	 *
	 * Se idEscluso è non null, il prodotto con quell'ID viene ignorato:
	 * questo consente la modifica di un prodotto mantenendo il proprio nome
	 * senza generare un falso duplicato.
	 *
	 * @return true se il nome è libero, false se è già in uso da un altro prodotto
	 */
	public boolean isNomeUnivoco(String nome, Long idEscluso) {
		Prodotto p = cercaProdottoPerNome(nome);
		if (p == null) return true;
		return idEscluso != null && p.getId().equals(idEscluso);
	}

	public Long getIdPerNome(String nome) {
		Prodotto p = cercaProdottoPerNome(nome);
		return (p != null) ? p.getId() : null;
	}

	/**
	 * Restituisce i nomi di tutti i valori dell'enum Categoria come array di stringhe.
	 * Utilizzato dal Controller per popolare le ComboBox nel Boundary
	 *
	 * @return array di stringhe con i nomi delle categorie disponibili
	 */
	public String[] getCategorieDisponibili() {
		Categoria[] categorie = Categoria.values();
		String[] nomi = new String[categorie.length];
		for (int i = 0; i < categorie.length; i++) {
			nomi[i] = categorie[i].name();
		}
		return nomi;
	}

	/**
	 * Restituisce i nomi di tutti i valori dell'enum Collocazione come array di stringhe.
	 * Utilizzato dal Controller per popolare le ComboBox nel Boundary
	 *
	 * @return array di stringhe con i nomi delle collocazioni disponibili
	 */
	public String[] getCollocazioniDisponibili() {
		Collocazione[] posizioni = Collocazione.values();
		String[] nomi = new String[posizioni.length];
		for (int i = 0; i < posizioni.length; i++) {
			nomi[i] = posizioni[i].name();
		}
		return nomi;
	}

}
