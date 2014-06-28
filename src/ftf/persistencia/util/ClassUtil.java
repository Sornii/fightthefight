/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftf.persistencia.util;

import ftf.modelo.Jogador;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Igor
 */
public class ClassUtil {
    public static void main(String[] args) {
        ClassUtil.getNomeTabela(Jogador.class);
    }
    
    public static String getNomeTabela(Class value){
        return value.getSimpleName().toLowerCase();
    }
    
    public static String getMethodGet(String fieldName) {
        return "get" + StringUtil.capitalize(fieldName);
    }

    public static String getMethodSet(String fieldName) {
        return "set" + StringUtil.capitalize(fieldName);
    }
    
    public static List<Field> getCampos(Class value) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(value.getDeclaredFields()));
        fields.addAll(Arrays.asList(value.getSuperclass().getDeclaredFields()));
        return fields;
    }
}
