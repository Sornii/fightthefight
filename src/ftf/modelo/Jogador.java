/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftf.modelo;

import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "jogadores")
public class Jogador extends Criatura {
    
    private Integer dinheiro;

    public Integer getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(Integer dinheiro) {
        this.dinheiro = dinheiro;
    }
}
