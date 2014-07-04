package ftf.modelo;

import ftf.persistencia.BolsaJogadorService;
import ftf.persistencia.JogadorService;
import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;
import java.util.ArrayList;
import java.util.List;

@Tabela(nome = "jogadores")
public class Jogador extends Criatura {
    
    @NaoMapear
    private final JogadorService jogadorService = JogadorService.getInstance();
    
    @NaoMapear
    private final BolsaJogadorService bjs = BolsaJogadorService.getInstance();
    
    private Usuario usuario;
    
    private Integer dinheiro;
    private Integer experiencia;
    private Integer nivel;
    private Arma arma;
    private Escudo escudo;
    
    @NaoMapear
    private List<Item> bolsa;
    
    {
        dinheiro
                = experiencia = 0;
        nivel = 1;
        
        bolsa = new ArrayList<>();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(Integer dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Escudo getEscudo() {
        return escudo;
    }

    public void setEscudo(Escudo escudo) {
        this.escudo = escudo;
    }

    public List<Item> getBolsa() {
        bolsa = bjs.getBolsa(this);
        return bolsa;
    }
    
    public void setBolsa(List<Item> bolsa) {
        this.bolsa = bolsa;
    }
    
    public void adicionarBolsa(Item item) {
        bolsa.add(item);
    }
    
    public void removerBolsa(Item item){
        bolsa.remove(item);
    }
    
    @Override
    public void salvar() {
        jogadorService.salvar(this);
        
        if (getId() == null) {
            Jogador jogador = jogadorService.getJogadorPorNome(getNome());
            setId(jogador.getId());
        }
        
        bjs.salvarBolsa(bolsa, this);
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
