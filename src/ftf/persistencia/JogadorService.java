package ftf.persistencia;

import ftf.modelo.Jogador;
import ftf.modelo.Usuario;
import java.util.List;

public class JogadorService extends BaseService<Jogador> {

    private static final JogadorService jogadorService = new JogadorService(Jogador.class);

    private JogadorService(Class<Jogador> aClass) {
        super(aClass);
    }

    public static JogadorService getInstance() {
        return jogadorService;
    }

    public Jogador getJogadorPorNome(String nome) {
        return getCustomUnico("nome = '" + nome + "'");
    }

    public List<Jogador> getJogadores(Usuario usuario) {
        return jogadorService.getCustomListagem("usuario_id = " + usuario.getId());
    }
    
    public static void main(String[] args) {
        
    }
}
