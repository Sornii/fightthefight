/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftf.modelo;

import ftf.persistencia.annotation.Tabela;


@Tabela(nome = "base")
public interface ModelBase {
    Integer getId();
}
