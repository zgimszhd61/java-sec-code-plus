
package com.freedom.securitysamples.api.ldapInjection;

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
    public String ldapSearch(String userInput) throws NamingException {
        Hashtable<String, String> ldapEnvironment = new Hashtable<>();
        ldapEnvironment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapEnvironment.put(Context.PROVIDER_URL, "ldap://localhost:389");

        // 创建 InitialDirContext 对象
        InitialDirContext ldapContext = new InitialDirContext(ldapEnvironment);

        // BAD: User input used in DN (Distinguished Name) without encoding
        String distinguishedName = "OU=People,O=" + userInput;

        // BAD: User input used in search filter without encoding
        String searchFilter = "username=" + userInput;

        ldapContext.search(distinguishedName, searchFilter, new SearchControls());
        return "{'msg':'success'}";
    }
}