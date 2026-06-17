package setup;

import database.GestorePersistenza;
import entity.GestoreUtenti;
import entity.TipoOperatore;
import entity.Utente;

public class DatiTestUtenti {

    private DatiTestUtenti(){

    }

    public static void popolaUtenti(GestoreUtenti gestoreUtenti) {

        // Creiamo i 4 utenti fissi
        gestoreUtenti.registraUtente("Marekiaro17","Ciro",
                "Romano","ciro.capitano@email.it",
                "fns", TipoOperatore.OPERATORE);
        gestoreUtenti.registraUtente("KvaraMagic77","Martina",
                "Russo","marty.kvara@email.it",
                "admin", TipoOperatore.RESPONSABILE);
    }
}