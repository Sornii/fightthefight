package ftf.persistencia;

import ftf.modelo.Arma;
import ftf.modelo.BolsaJogador;
import ftf.modelo.Escudo;
import ftf.modelo.Item;
import ftf.modelo.Jogador;
import ftf.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class JogadorService extends BaseService<Jogador> {

    private static final JogadorService jogadorService = new JogadorService(Jogador.class);

    private JogadorService(Class<Jogador> aClass) {
        super(aClass);
    }

    public static JogadorService getInstance() {
        return jogadorService;
    }

    public Jogador getJogador(String nome) {
        return getCustomUnico("nome = '" + nome + "'");
    }

    public List<Jogador> getJogadores(Usuario usuario) {
        return jogadorService.getCustomListagem("usuario_id = " + usuario.getId());
    }
    
    public static void main(String[] args) {
        
        Item item = new Arma();
        item.setId(1);
        item.setNome("Espada");
        Item item2 = new Escudo();
        item2.setId(2);
        item2.setNome("Escudo");
        
        BolsaJogador bj = new BolsaJogador(new Jogador(), item);
        BolsaJogador bj2 = new BolsaJogador(new Jogador(), item2);
        
        List<BolsaJogador> bolsas = new ArrayList<>();
        bolsas.add(bj);
        bolsas.add(bj2);
        
        
        
        /*bolsas.remove(bolsas.stream().findFirst().filter((BolsaJogador t) -> {
        return t.getItem().equals(item);
        }).get());*/
        
        bolsas.forEach((bolsa) -> {
            System.out.println(bolsa.getItem().getNome());
        });
        
        System.out.println(bolsas.size());
    }
}
