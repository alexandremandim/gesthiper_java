import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.io.Serializable;
public class CatalogoClientesHash implements Serializable
{
    private Set<String> clientes;
    public CatalogoClientesHash()
    {
        this.clientes = new HashSet<String>();
    }
    public CatalogoClientesHash(CatalogoClientesHash c){
            this.clientes = c.getClientes();
    }
    //gets e sets
    public Set<String> getClientes(){
        Set<String> novo = new HashSet<String>();
        for(String s : this.clientes){
            novo.add(s);
        }
        return novo;
    }
   
    public void addCliente(String cliente){this.clientes.add(cliente);}
    public int totalClientes(){
       return this.clientes.size();
    }
    
    public boolean contains(String codigoCliente){
        return this.clientes.contains(codigoCliente);
    }
    
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.getClientes()});
    }
    
    // toString,equals e clone
    public CatalogoClientesHash clone(){
       return new CatalogoClientesHash(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        CatalogoClientesHash catCli = (CatalogoClientesHash)o;
        
        if(this.totalClientes() != catCli.totalClientes()){ return false;}
        for(String s: this.getClientes()){
            if(!(catCli.contains(s))) return false;}
        return true;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Este catálogo de clientes tem:\n");
        sb.append(this.totalClientes() + " clientes.\n");
        return sb.toString();
    }
}
