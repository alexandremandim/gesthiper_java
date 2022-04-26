import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;
public class CatalogoClientes implements Serializable
{
    private Set<String> clientes;
    public CatalogoClientes()
    {
        this.clientes = new TreeSet<String>();
    }
    public CatalogoClientes(CatalogoClientes c){
            this.clientes = c.getClientes();
    }
    //gets e sets
    public Set<String> getClientes(){
        Set<String> novo = new TreeSet<String>();
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
   
    // toString,equals e clone
    public CatalogoClientes clone(){
       return new CatalogoClientes(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        CatalogoClientes catCli = (CatalogoClientes)o;
        
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
