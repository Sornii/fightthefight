/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftf.modelo;

import ftf.persistencia.annotation.Tabela;

@Tabela(nome = "criaturas")
public abstract class Criatura extends Model {

    private String nome;
    private Integer vidaAtual;
    private Integer vidaTotal;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(Integer vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public Integer getVidaTotal() {
        return vidaTotal;
    }

    public void setVidaTotal(Integer vidaTotal) {
        this.vidaTotal = vidaTotal;
    }

    @Override
    public String toString() {
        return getNome();
    }
    
    
}
