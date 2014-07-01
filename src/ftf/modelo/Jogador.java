package ftf.modelo;

import ftf.persistencia.JogadorService;
import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "jogadores")
public class Jogador extends Criatura {
    
    private final JogadorService jogadorService = JogadorService.getInstance();
    
    private Usuario usuario;
    private Integer dinheiro;

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

    @Override
    public void salvar() {
        jogadorService.salvar(this);
    }
}
