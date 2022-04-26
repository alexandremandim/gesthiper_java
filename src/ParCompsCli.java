import java.io.Serializable;
public class ParCompsCli implements Serializable
{
    private int nrCompras, nrClientes;

    /* Construtores */
    public ParCompsCli(int compras, int clientes)
    {
        this.nrCompras = compras;
        this.nrClientes = clientes;
    }
    
    public ParCompsCli(ParCompsCli pcc){
        this.nrCompras = pcc.getNrCompras();
        this.nrClientes = pcc.getNrClientes();
    }
    
    //gets e sets
    public int getNrCompras(){ return this.nrCompras;}
    public int getNrClientes(){ return this.nrClientes;}
    public void setNrCompras(int compras){this.nrCompras += compras;}
    public void setNrClientes(int clientes){this.nrClientes += clientes;}
    
    /* clone, toString e equals */
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        ParCompsCli pcc = (ParCompsCli)o;
        if(this.nrCompras != pcc.getNrCompras()) return false;
        if(this.nrClientes != pcc.getNrClientes()) return false;
        return true;
    }
    
    public ParCompsCli clone(){
        return new ParCompsCli(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Número de Compras " + nrCompras);
        sb.append("\nNúmero de Clientes " + nrClientes);
        return sb.toString();
    }
}
