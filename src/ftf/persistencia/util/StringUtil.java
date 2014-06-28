/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftf.persistencia.util;

/**
 *
 * @author Igor
 */
public class StringUtil {
    public static String capitalize(String value) {
        String rt = "";
        if (value.length() > 0) {
            rt += value.substring(0, 1).toUpperCase();
        }
        if (value.length() > 1) {
            rt += value.substring(1);
        }
        return rt;
    }
}
