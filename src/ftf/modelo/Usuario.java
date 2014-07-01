package ftf.modelo;

import ftf.persistencia.UsuarioService;
import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;
import java.util.List;

@Tabela(nome = "usuarios")
public class Usuario implements ModelBase {

    @NaoMapear
    private final UsuarioService usuarioService = UsuarioService.getInstance();

    private Integer id;
    private String nome;
    private String senha;

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public void salvar() {
        usuarioService.salvar(this);

        if (getId() == null) {
            Usuario usuarioPorNome = usuarioService.getUsuarioPorNome(nome);
            setId(usuarioPorNome.getId());
        }
    }

    public List<Jogador> getJogadores() {
        return usuarioService.getJogadores(this);
    }
}
