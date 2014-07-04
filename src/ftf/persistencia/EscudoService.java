package ftf.persistencia;

import ftf.modelo.Escudo;

public class EscudoService extends BaseService<Escudo> {

    private static final EscudoService instance = new EscudoService(Escudo.class);

    private EscudoService(Class<Escudo> aClass) {
        super(aClass);
    }

    public static EscudoService getInstance() {
        return instance;
    }
}
