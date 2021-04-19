/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.test.app;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * @author tomoncle
 */
public class LdapTest {

    public static void main(String[] args) {
        DirContext context = null;

        String userId = "lisi";
        String password = "123";
        String userGroup = "xinnet";
        String baseDn = "dc=xinnet,dc=com";
        String username = String.format("uid=%s,ou=%s,%s", userId, userGroup, baseDn);
        String ldapUrl = "ldap://ldap_hostname:389/" + baseDn;

        Hashtable<String, String> tbl = new Hashtable<>(4);
        tbl.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        tbl.put(Context.SECURITY_AUTHENTICATION, "simple");
        tbl.put(Context.PROVIDER_URL, ldapUrl);
        tbl.put(Context.SECURITY_PRINCIPAL, username);
        tbl.put(Context.SECURITY_CREDENTIALS, password);
        try {
            context = new InitialDirContext(tbl);
            System.out.println("login successfully.:" + context.toString());
        } catch (Exception ex) {
            System.out.println("login failed : " + ex);
        } finally {
            try {
                if (context != null) {
                    context.close();
                }
                tbl.clear();
            } catch (Exception e) {
                System.out.println("context close error : " + e);
            }
        }
    }
}
