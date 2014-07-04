package ftf.modelo;

public abstract class Model {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    abstract void salvar();
}
