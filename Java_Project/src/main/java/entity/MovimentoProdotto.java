package entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class MovimentoProdotto {

    /*
    Ciascun movimento sarà identificato da
    un codice numerico univoco auto-generato
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    Tra Prodotto e MovimentoProdotto sussite un'associazione 1 a Molti.
    Tramite quest'annotazione stiamo implementando l'associazione 1 a Molti, lato Molti.
    Dunque , all'interno della tabella MovimentiProdotto sarà presente una Foreign Key
    che fa riferimento al codice univoco del prodotto su cui è stata effettuata la movimentaizone.
    */
    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    private int unita;

    @Enumerated(EnumType.STRING)
    private TipoMovimentoProdotto tipo;

    private LocalDate data;

    public MovimentoProdotto() {
        //Costruttore vuoto richiesto da Hibernate
    }

    public MovimentoProdotto(Prodotto prodotto, int unita, TipoMovimentoProdotto tipo, LocalDate data) {
        this.prodotto = prodotto;
        this.unita = unita;
        this.tipo = tipo;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getUnita() {
        return unita;
    }

    public void setUnita(int unita) {
        this.unita = unita;
    }

    public TipoMovimentoProdotto getTipo() {
        return tipo;
    }
    public void setTipo(TipoMovimentoProdotto tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MovimentoProdotto{" +
                "id=" + id +
                ", prodotto=" + prodotto +
                ", unita=" + unita +
                ", tipo=" + tipo +
                ", data='" + data + '\'' +
                '}';
    }
}

