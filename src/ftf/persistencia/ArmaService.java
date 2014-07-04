package ftf.persistencia;

import ftf.modelo.Arma;

public class ArmaService extends BaseService<Arma> {

    private static final ArmaService instance = new ArmaService(Arma.class);

    private ArmaService(Class<Arma> aClass) {
        super(aClass);
    }

    public static ArmaService getInstance() {
        return instance;
    }
}
