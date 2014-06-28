/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftf.persistencia;

import ftf.modelo.Jogador;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Igor
 */
public class JogadorService extends BaseService<Jogador> {
    
    private static final JogadorService jogadorService = new JogadorService(Jogador.class);
    
    private JogadorService(Class<Jogador> aClass) {
        super(aClass);
    }
    
    public static JogadorService getInstance(){
        return jogadorService;
    }
    
    public static void main(String[] args) {
        Jogador jogador = new Jogador();
        jogador.setId(1);
        jogador.setNome("Sornii");
        jogador.setDinheiro(3000);
        JogadorService.getInstance().salvar(jogador);
    }
    
    public static void lol2(){
        List<Field> fields = new ArrayList<>();
        
        fields.addAll(Arrays.asList(Jogador.class.getDeclaredFields()));
        fields.addAll(Arrays.asList(Jogador.class.getSuperclass().getDeclaredFields()));
        
        for(Field field : fields){
            System.out.println(field.getName());
        }
    }
    
    //lol
    public static void lol(){
        JogadorService instance = JogadorService.getInstance();
        Jogador unico = instance.getUnico(1);
        System.out.println(unico.getNome());
        List<Jogador> listagem = instance.getListagem();
        listagem.stream().forEach((jogador) -> {
            System.out.println(jogador.getId());
            System.out.println(jogador.getNome());
            System.out.println(jogador.getDinheiro());
        });
    }
}
