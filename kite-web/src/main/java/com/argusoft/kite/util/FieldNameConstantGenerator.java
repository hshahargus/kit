package com.argusoft.kite.util;

import com.argusoft.kite.Application;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @author hshah
 */
public class FieldNameConstantGenerator {

//    public static void main(String args[]) {
//        Class clazz = UserStockTallyDetailEntity.class;
//        System.out.println("static class " + clazz.getSimpleName() + "Fields {");
//        Field[] declaredFields = clazz.getDeclaredFields();
//        for (Field field : declaredFields) {
////            System.out.println("/**");
////            System.out.println("* " + field.getType().getSimpleName() + " " + field.getName());
////            System.out.println("*/");`
//            System.out.println("public static final String " + convert(field.getName()) + " = \"" + field.getName() + "\";");
////            System.out.println("");
//        }
//        System.out.println("}");
//    }
    public static String convert(String s) {
        char[] toCharArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        char old = 'Z';
        for (Character c : toCharArray) {
            if (Character.isUpperCase(c) && !Character.isUpperCase(old)) {
                sb.append('_');
            }
            old = c;
            sb.append(c);
        }
//        System.out.println(sb.toString().toUpperCase());
        return sb.toString().toUpperCase();
    }

    public static String getMapping(final Class<?> fromClass, final Class<?> toClass, final List<String> notFOundList) {
        String fromName = fromClass.getSimpleName();
        String toName = toClass.getSimpleName();
        final StringBuilder sb = new StringBuilder(
                "public static " + toName + " convert" + fromName + "To" + toName
                + " ( " + fromName + " from) { \n"
                + toName + " to=new " + toName + "();\n");
        ReflectionUtils.doWithFields(toClass,
                new ReflectionUtils.FieldCallback() {

                    @Override
                    public void doWith(final Field field) throws IllegalArgumentException,
                    IllegalAccessException {

                        try {

                            String set = new PropertyDescriptor(field.getName(), toClass)
                            .getWriteMethod().getName();
                            try {
                                String name = new PropertyDescriptor(field.getName(), fromClass)
                                .getReadMethod().getName();
                                sb.append("to.")
                                .append(set)
                                .append("(from.")
                                .append(name)
                                .append("());\n");

                            } catch (IntrospectionException ex) {

                                if (field.getType().equals(Boolean.class)) {
                                    try {
                                        String name = field.getName();
                                        String first = "" + name.charAt(0);
                                        name = name.replaceFirst(first, first.toUpperCase());
                                        Method method = fromClass.getMethod("get" + name);
                                        sb.append("to.")
                                        .append(set)
                                        .append("(from.")
                                        .append(method.getName())
                                        .append("());\n");

                                    } catch (NoSuchMethodException ex1) {
                                        sb.append("// to.")
                                        .append(set)
                                        .append("(from.")
                                        .append(");\n");
                                        notFOundList.add(field.getName());

                                    } catch (SecurityException ex1) {
                                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    sb.append("// to.")
                                    .append(set)
                                    .append("(from.")
                                    .append(");\n");
                                    notFOundList.add(field.getName());

                                }
                            }
                        } catch (IntrospectionException ex) {
                            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                },
                new ReflectionUtils.FieldFilter() {

                    @Override
                    public boolean matches(final Field field) {
                        final int modifiers = field.getModifiers();
                        // no static fields please
                        return !Modifier.isStatic(modifiers);
                    }
                });
        sb.append("return to;\n}");
        return sb.toString();
    }
}
