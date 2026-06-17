package setup;

import entity.Categoria;
import entity.Collocazione;
import entity.GestoreProdotti;

public class DatiTestProdotti {

    private DatiTestProdotti(){}

    public static void popolaProdotti(GestoreProdotti gestoreProdotti) {
        gestoreProdotti.registraNuovoProdotto("Maglia N. 10 - Diego Armando Maradona", "La divisa originale indossata da 'El Pibe de Oro' durante la storica stagione del primo Scudetto.", 10, 1, Categoria.NAPOLI, Collocazione.SCAFFALE_1);
        gestoreProdotti.registraNuovoProdotto("Terzo Scudetto (2022-2023)", "Il trofeo vinto sotto la guida di Luciano Spalletti, che ha riportato il titolo di Campione.", 5, 3, Categoria.NAPOLI, Collocazione.SCAFFALE_2);
        gestoreProdotti.registraNuovoProdotto("Fascia da Capitano di Marek Hamšík", "La fascia da capitano autografata dal centrocampista slovacco.", 1, 3, Categoria.NAPOLI, Collocazione.SCAFFALE_1);
        gestoreProdotti.registraNuovoProdotto("Apple watch series 11", "Smartwatch per il fitness", 1, 3, Categoria.DISPOSITIVI_APPLE, Collocazione.SCAFFALE_4);
        gestoreProdotti.registraNuovoProdotto("Adidas AdiZero Pro Evo 3", "Scarpe per runner professionisti.", 1, 3, Categoria.VESTIARIO_SPORTIVO, Collocazione.SCAFFALE_2);
        gestoreProdotti.registraNuovoProdotto("Nike Pegasus 41", "Scarpe da running per amanti del fitness.", 1, 3, Categoria.VESTIARIO_SPORTIVO, Collocazione.SCAFFALE_3);
        gestoreProdotti.registraNuovoProdotto("Peppeloni Burger", "Ha un sapore molto intenso,stile americano meglio se con bacon.", 1, 3, Categoria.CIBO, Collocazione.SCAFFALE_3);
    }
}
