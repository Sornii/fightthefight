package ftf.persistencia.util;

import ftf.modelo.Model;

public class CampoValor {

    private String campo;
    private Object valor;

    public CampoValor(String campo, Object valor) {
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() {
        if (valor instanceof String
                || valor instanceof Integer) {
            return campo;
        } else {
            return campo + "_id";
        }
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getValorString() {
        if (valor instanceof String) {
            String v = (String) this.valor;
            return "'" + v + "'";
        } else if (valor instanceof Integer) {
            return valor.toString();
        } else {
            Model base = (Model) valor;
            return base.getId().toString();
        }
    }

    public boolean isNull() {
        return valor == null;
    }
}
