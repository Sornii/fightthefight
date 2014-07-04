package ftf.modelo;

import ftf.persistencia.JogadorService;
import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "jogadores")
public class Jogador extends Criatura {
    
    @NaoMapear
    private final JogadorService jogadorService = JogadorService.getInstance();
    
    private Usuario usuario;
    private Integer dinheiro;
    private Integer nivel;
    private Integer experiencia;
    
    {
        dinheiro =
        experiencia = 0;
        nivel = 1;
    }
    
    public Jogador() {
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    @Override
    public void salvar() {
        jogadorService.salvar(this);
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
