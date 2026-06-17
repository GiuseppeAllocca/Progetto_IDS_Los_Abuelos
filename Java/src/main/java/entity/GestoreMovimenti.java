package entity;

import java.time.LocalDate;

import database.GestorePersistenza;

public class GestoreMovimenti {

    private GestorePersistenza gestorePersistenza;

    public GestoreMovimenti() {
		gestorePersistenza = new GestorePersistenza();
    }

	/**
	Questo metodo si occupa di gestire il salvataggio effettivo di un'eventuale
	operazione di carico/scarico chiedendo l'aiuto del "GestorePersistenza".
	Nello specifico, il metodo è strutturato come segue:
		1) Si richiede il l'entità prodotto su cui lavorare al "GestoreProdotti"
		2) In caso di operazione di scarico, si verifica se la movimentazione può
		   essere effettuata a seconda delle scorte attualmente presenti in magazzino
		3) Se il controllo riguardante le scorte viene superato, si crea l'entità
		   movimento e si richiede il salvataggio/registrazione di quest'ultima
		4) Infine si procede con l'aggiornamento automatico delle scorte in magazzino
	**/
	public EsitoMovimenti registraMovimentoProdotto(String nomeProdotto, int unita,
	                                                TipoMovimentoProdotto tipo, LocalDate data) {

		GestoreProdotti regProd = new GestoreProdotti();

		Prodotto prodotto = regProd.cercaProdottoPerNome(nomeProdotto);
		if (prodotto == null) {
			return EsitoMovimenti.MOVIMENTO_FALLITO;
		}

		// Controllo per vedere se il prodotto è disponibile
		if (tipo == TipoMovimentoProdotto.OPERAZIONE_DI_SCARICO) {
			if (!controlloQuantita(prodotto, unita)) {
				return EsitoMovimenti.MANCATA_DISPONIBILITA;
			}
		}

		MovimentoProdotto movimentoProdotto = new MovimentoProdotto(prodotto,
				unita, tipo, data);

		boolean esitoSalva = gestorePersistenza.salva(movimentoProdotto);

		if (!esitoSalva) {
			return EsitoMovimenti.MOVIMENTO_FALLITO;
		}

		if (aggiornamentoScorte(prodotto, unita, tipo)){
			return EsitoMovimenti.SUCCESSO_MOVIMENTO_CON_NOTIFICA;
		}

		return EsitoMovimenti.SUCCESSO_MOVIMENTO;

	}


	/**
	 * Questo metodo viene chiamato dopo un movimento e consiste nell'aggiornare
	 * il magazzino in modo consistente dopo un operazione di carico o di scarico
	 * Funziona nel seguente modo:
	 * Conrtrolla il tipo di operazione e in base a quest'ultimo aggiorna
	 * il prodotto per poi fare il commit nel database
	 * Restituisce un boolean siccome durante l'aggiornamento un prodotto potrebbe
	 * essere classificato sottoScorta grazie al metodo ControllaSottoScorta
	 */
	private boolean aggiornamentoScorte(Prodotto prodotto, int unita,
	                                    TipoMovimentoProdotto tipo) {
		boolean notifica = false;

		if (tipo == TipoMovimentoProdotto.OPERAZIONE_DI_CARICO) {

			prodotto.setQuantita(prodotto.getQuantita() + unita);

		}

		if (tipo == TipoMovimentoProdotto.OPERAZIONE_DI_SCARICO) {

			prodotto.setQuantita(prodotto.getQuantita() - unita);

		}

		if (controlloSottoScorte(prodotto)){
			notifica = true;
		}

		gestorePersistenza.aggiorna(prodotto);

		return notifica;

	}

	/**
	 * Metodo utilizzato da salva movimento per controllare la disponinilità
	 * del prodotto
	 *
	 * @param prodotto prodotto da controllare
	 * @param unita unità richieste per il movimento
	 * @return un esito per capire se il prodotto è disponibile o meno
	 */
	public boolean controlloQuantita(Prodotto prodotto, int unita) {

		boolean esito = true;

		if (prodotto.getQuantita() - unita < 0) {
			esito = false;
		}

		return esito;

	}

	/** Metodo utilizzato per controllare se il prodotto è in sotto scorta
	 * funziona nel seguente modo:
	 * Si fa il controllo della quantità in base alla soglia e in base al risultato
	 * si setta la notifica
	 * Facciamo ciò per far si che la notifica arrivi anche se il prodotto
	 * è gia sotto scorta e la quantità non è comunque superiore alla soglia
	 *
	 * @param prodotto prodotto da controllare se la sua quantita è sotto soglia
	 * @return esito per capire se il responsabile va notificato o meno
	 */
	public boolean controlloSottoScorte(Prodotto prodotto) {

		boolean notifica = false;

		if (prodotto.getQuantita() > prodotto.getSoglia()) {
			prodotto.setSottoScorta(false);
			notifica = false;
		} else if (prodotto.getQuantita() <= prodotto.getSoglia()) {
			prodotto.setSottoScorta(true);
			notifica = true;
		}

		return notifica;
	}

}
