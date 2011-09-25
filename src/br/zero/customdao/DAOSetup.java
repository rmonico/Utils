/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zero.customdao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author kxorroloko
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DAOSetup {
    public String findAllQueryName();
    public String findByIdQueryName();
    public String idFieldName();
}
