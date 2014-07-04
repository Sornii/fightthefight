package ftf.modelo;

import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "jogadores_itens")
public class BolsaJogador {
    
    private Jogador jogador;
    private Item item;
    private String desc;

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
