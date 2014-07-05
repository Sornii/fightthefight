package ftf.persistencia;

import ftf.modelo.BolsaJogador;
import ftf.modelo.Item;
import ftf.modelo.Jogador;
import java.util.ArrayList;
import java.util.List;

public class BolsaJogadorService extends BaseService<BolsaJogador> {

    private static final BolsaJogadorService instance = new BolsaJogadorService(BolsaJogador.class);

    private BolsaJogadorService(Class<BolsaJogador> aClass) {
        super(aClass);
    }

    public static BolsaJogadorService getInstance() {
        return instance;
    }
    
    public List<BolsaJogador> getBolsas(Jogador jogador){        
        return getCustomListagem("jogador_id = " + jogador.getId());
    }
    
    public List<Item> convertBolsas(List<BolsaJogador> bolsas) {
        List<Item> itens = new ArrayList<>();
        
        bolsas.forEach((bolsa) -> {
            itens.add(bolsa.getItem());
        });
        
        return itens;
    }
    
    public List<Item> getBolsa(Jogador jogador) {
        List<BolsaJogador> bolsas = getBolsas(jogador);
        return convertBolsas(bolsas);
    }
    
    public void salvarBolsa(List<Item> itens, Jogador jogador){
        itens.forEach((item) -> {
            new BolsaJogador(jogador, item).salvar();
        });
    }
    
}
