package ftf.persistencia;

import ftf.modelo.Jogador;

public class JogadorService extends BaseService<Jogador> {
    
    private static final JogadorService jogadorService = new JogadorService(Jogador.class);
    
    private JogadorService(Class<Jogador> aClass) {
        super(aClass);
    }
    
    public static JogadorService getInstance(){
        return jogadorService;
    }
    
    public Jogador getJogadorPorNome(String nome){
        return getCustomUnico("nome = '"+ nome +"'");
    }
    
    public static void main(String[] args) {
        jogadorService.toString();
    }
}
