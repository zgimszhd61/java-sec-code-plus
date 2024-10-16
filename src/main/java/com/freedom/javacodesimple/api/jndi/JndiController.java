import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.groovy.GroovyScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@RestController
@RequestMapping("/api")
public class JndiController {
    @GetMapping("/jndi/bad01")
    public String lookupJndiResource(String jndiResourceName) throws NamingException {
        Hashtable<String, String> jndiEnvironment = new Hashtable<>();
        jndiEnvironment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        jndiEnvironment.put(Context.PROVIDER_URL, "rmi://trusted-server:1099");
        InitialContext initialContext = new InitialContext(jndiEnvironment);

        // BAD: User input used in lookup
        initialContext.lookup(jndiResourceName);
        return  "{'msg':'false'}";
    }
}