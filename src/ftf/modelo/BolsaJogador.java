package ftf.modelo;

import ftf.persistencia.BolsaJogadorService;
import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "jogadores_itens")
public class BolsaJogador extends Model {
    
    @NaoMapear
    private final BolsaJogadorService bolsaService = BolsaJogadorService.getInstance();
    
    private Jogador jogador;
    private Item item;
    private String descriminador;
    
    {
        descriminador = "";
    }

    public BolsaJogador() {
    }

    public BolsaJogador(Jogador jogador, Item item) {
        this.jogador = jogador;
        this.item = item;
    }

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

    public String getDescriminador() {
        return descriminador;
    }

    public void setDescriminador(String descriminador) {
        this.descriminador = descriminador;
    }

    @Override
    public void salvar() {
        if (descriminador.isEmpty()) {
            descriminador =  item.getClass().getSimpleName();
        }
        bolsaService.salvar(this);
    }
}
