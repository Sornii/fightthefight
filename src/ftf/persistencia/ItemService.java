package ftf.persistencia;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import ftf.modelo.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemService extends BaseService<Item> {

    private static final ItemService instance = new ItemService(Item.class);
    
    private final ArmaService armaService = ArmaService.getInstance();
    private final EscudoService escudoService = EscudoService.getInstance();

    private ItemService(Class<Item> aClass) {
        super(aClass);
    }

    public static ItemService getInstance() {
        return instance;
    }
    
    public List<Item> getItensOrdernadoPreco() {
        List<Item> itens = new ArrayList<>();
        itens.addAll(armaService.getListagem());
        itens.addAll(escudoService.getListagem());
        
        Ordering<Item> ordenarItens = new Ordering<Item>() {
            @Override
            public int compare(Item t, Item t1) {
                return Ints.compare(t.getPreco(), t1.getPreco());
            }
        };
        
        return ordenarItens.sortedCopy(itens);
    }
    
}
