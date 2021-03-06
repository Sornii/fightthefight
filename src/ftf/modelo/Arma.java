/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftf.modelo;

import ftf.persistencia.ArmaService;
import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "armas")
public class Arma extends Item {

    @NaoMapear
    private final ArmaService armaService = ArmaService.getInstance();

    private Integer ataque;

    {
        ataque = 0;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    @Override
    public void salvar() {
        armaService.salvar(this);
    }
}
