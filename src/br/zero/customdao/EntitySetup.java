/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zero.customdao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author kxorroloko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntitySetup {
    public String findAllQueryName();
    public String findByIdQueryName();
    public String idFieldName();
}
