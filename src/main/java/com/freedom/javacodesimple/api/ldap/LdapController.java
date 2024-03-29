package com.freedom.javacodesimple.api.ldap;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;

@RestController
@RequestMapping("/api")
public class LdapController {
    @GetMapping("/ldap/bad01")
    public String ldap01(String payload) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389");

        // 创建 InitialDirContext 对象
        InitialDirContext ctx = new InitialDirContext(env);

        // BAD: User input used in DN (Distinguished Name) without encoding
        String dn = "OU=People,O=" + payload;

        // BAD: User input used in search filter without encoding
        String filter = "username=" + payload;

        ctx.search(dn, filter, new SearchControls());
        return "{'msg':'success'}";
    }
}
