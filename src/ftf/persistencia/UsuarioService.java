package ftf.persistencia;

import ftf.modelo.Jogador;
import ftf.modelo.Usuario;
import java.util.List;

public class UsuarioService extends BaseService<Usuario> {
    private static final UsuarioService usuarioService = new UsuarioService(Usuario.class);
    private static final JogadorService jogadorService = JogadorService.getInstance();
    
    private UsuarioService(Class<Usuario> aClass) {
        super(aClass);
    }
    
    public static UsuarioService getInstance(){
        return usuarioService;
    }
    
    public Usuario getUsuarioPorNome(String nome) {
        return getCustomUnico("nome = '" + nome + "'");
    }
    
    public Usuario validarUsuario(Usuario usuario){
        Usuario usuarioExistente = getCustomUnico(" nome = '" + usuario.getNome() + "' and senha = '" + usuario.getSenha() + "'");
        if(usuarioExistente != null){
            return usuarioExistente;
        }
        return null;
    }
    
    public boolean usuarioExistente(String usuario) {
        Usuario usuarioExistente = getCustomUnico("nome = '" + usuario + "'");
        return usuarioExistente != null;
    }
}
