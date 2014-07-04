/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftf.modelo;

import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "escudos")
public class Escudo extends Item {
    private Integer defesa;

    public Integer getDefesa() {
        return defesa;
    }

    public void setDefesa(Integer defesa) {
        this.defesa = defesa;
    }
}
