package ftf.persistencia.util;

import ftf.persistencia.annotation.NaoMapear;
import ftf.persistencia.annotation.Tabela;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtil {

    public static <T> String getNomeTabela(Class<T> value) {
        Tabela[] tabelaAnnotations = value.getAnnotationsByType(Tabela.class);
        return tabelaAnnotations[0].nome();
    }

    public static String getMethodGet(String fieldName) {
        return "get" + StringUtil.capitalize(fieldName);
    }

    public static String getMethodSet(String fieldName) {
        return "set" + StringUtil.capitalize(fieldName);
    }

    public static List<Field> getCampos(Class value) {
        List<Field> fields = new ArrayList<>();
        adicionarCampos(Arrays.asList(value.getDeclaredFields()), fields);

        while ((value = value.getSuperclass()) != Object.class) {
            adicionarCampos(Arrays.asList(value.getDeclaredFields()), fields);
        }

        return fields;
    }

    private static void adicionarCampos(List<Field> superClassFields, List<Field> fields) {
        superClassFields.forEach((field) -> {
            if (field.getAnnotation(NaoMapear.class) == null) {
                fields.add(field);
            }
        });
    }

    public static List<CampoValor> getCamposValores(Object value) {
        Class valueClass = value.getClass();
        List<Field> campos = getCampos(valueClass);
        List<CampoValor> camposValores = new ArrayList<>();
        campos.stream().forEach((campo) -> {
            String getMethodName = getMethodGet(campo.getName());
            try {
                Method getMethod = valueClass.getMethod(getMethodName);
                Object returnValue = getMethod.invoke(value);
                camposValores.add(new CampoValor(campo.getName(), returnValue));
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                System.out.println("Reflexão falhou");
            }
        });
        return camposValores;
    }
}
