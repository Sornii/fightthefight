/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftf.modelo;

import ftf.persistencia.MonstroService;
import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "monstros")
public class Monstro extends Criatura {

    @NaoMapear
    private final MonstroService monstroService = MonstroService.getInstance();

    private Integer recompensa;
    private Integer ataque;

    public Integer getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Integer recompensa) {
        this.recompensa = recompensa;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }
    
    @Override
    public void salvar() {
        monstroService.salvar(this);
    }
}
