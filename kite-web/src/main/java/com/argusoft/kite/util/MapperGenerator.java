package com.argusoft.kite.util;

import com.argusoft.kite.Application;
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
public class MapperGenerator {

//    public static void main(String arg[]) {
//
//        final List<String> notFOundList = new LinkedList<>();
//        System.out.println(getMapping(BoxDetailEntity.class, BoxDetailDto.class, notFOundList));
//        System.out.println("not found: " + notFOundList);
//
//    }
    public static String getMapping(final Class<?> fromClass, final Class<?> toClass, final List<String> notFOundList) {
        String fromName = fromClass.getSimpleName();
        String toName = toClass.getSimpleName();
        final StringBuilder sb = new StringBuilder(
                "public static " + toName + " convert" + fromName + "To" + toName + " ( " + fromName + " from) { \n"
                + toName + " to=new " + toName + "();\n");
        ReflectionUtils.doWithFields(toClass,
                new ReflectionUtils.FieldCallback() {

                    @Override
                    public void doWith(final Field field) throws IllegalArgumentException,
                    IllegalAccessException {

                        try {

                            String set = new PropertyDescriptor(field.getName(), toClass).getWriteMethod().getName();
                            try {
                                String name = new PropertyDescriptor(field.getName(), fromClass).getReadMethod().getName();
                                sb.append("to.")
                                .append(set)
                                .append("(from.")
                                .append(name)
                                .append("());\n");

                            } catch (java.beans.IntrospectionException ex) {

                                if (field.getType().equals(Boolean.class)) {
                                    try {
                                        String name = field.getName();
                                        String first = "" + name.charAt(0);
                                        name = name.replaceFirst(first, first.toUpperCase());
                                        Method method = toClass.getMethod("get" + name);
                                        sb.append("to.")
                                        .append(set)
                                        .append("(from.")
                                        .append(method.getName())
                                        .append("());\n");

                                    } catch (NoSuchMethodException ex1) {
                                        notFOundList.add(field.getName());

                                    } catch (SecurityException ex1) {
                                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    sb.append("to.")
                                    .append(set)
                                    .append("(from.get")
                                    .append("());\n");
                                    notFOundList.add(field.getName());

                                }
                            }
                        } catch (java.beans.IntrospectionException ex) {
                            Logger.getLogger(MapperGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
