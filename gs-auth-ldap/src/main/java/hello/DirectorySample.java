package hello;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class DirectorySample {
    public DirectorySample() {
    }
    
    public void doLookup() {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        
    	String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
        String MY_HOST = "ldap://Localhost:10389";
        String MGR_DN = "uid=admin,ou=system";
        String MGR_PW = "secret";           

        //Identify service provider to use
        Hashtable env_0 = new Hashtable();
        env_0.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
        env_0.put(Context.PROVIDER_URL, MY_HOST);
        env_0.put(Context.SECURITY_AUTHENTICATION, "simple");
        env_0.put(Context.SECURITY_PRINCIPAL, MGR_DN);
        env_0.put(Context.SECURITY_CREDENTIALS, MGR_PW);
        try {
            DirContext context = new InitialDirContext(env_0);
            Attributes attrs = context
                    .getAttributes("employeeNumber=21629724,ou=users,ou=system");
            System.out.println("Surname: " + attrs.get("sn").get());
            System.out.println("Common name : " + attrs.get("cn").get());
            System.out.println("telephone number : "
                    + attrs.get("telephoneNumber").get());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DirectorySample sample = new DirectorySample();
        sample.doLookup();
    }
}
