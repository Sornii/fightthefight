package ftf.persistencia;

import ftf.modelo.Monstro;

public class MonstroService extends BaseService<Monstro> {

    private static final MonstroService monstroService = new MonstroService(Monstro.class);

    private MonstroService(Class<Monstro> aClass) {
        super(aClass);
    }

    public static MonstroService getInstance() {
        return monstroService;
    }
}
