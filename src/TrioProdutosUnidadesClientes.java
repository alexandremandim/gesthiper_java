import java.io.Serializable;
public class TrioProdutosUnidadesClientes implements Serializable, MostrarCodigos
{
    // instance variables - replace the example below with your own
    private String codigo;
    private int unidadesVendidas, nrClientes;

    /* construtores */
    public TrioProdutosUnidadesClientes()
    {
        codigo = new String();
        this.unidadesVendidas = 0;
        this.nrClientes = 0;
    }
    public TrioProdutosUnidadesClientes(String codigo, int unV, int nrCli)
    {
        this.codigo = codigo;
        this.unidadesVendidas = unV;
        this.nrClientes = nrCli;
    }
    public TrioProdutosUnidadesClientes(TrioProdutosUnidadesClientes tpuc){
        this.codigo = tpuc.getCodigo();
        this.unidadesVendidas = tpuc.getUnidadesVendidas();
        this.nrClientes = tpuc.getNrClientes();
    }

    // gets e sets
    public String getCodigo(){return codigo;}
    public int getUnidadesVendidas(){return unidadesVendidas;}
    public int getNrClientes(){return nrClientes;}
    
    public void setCodigo(String codigo){this.codigo = codigo;}
    public void setUnidadesVendidas(int unidadesVendidas){this.unidadesVendidas += unidadesVendidas;}
    public void setNrClientes(int nrClientes){this.nrClientes += nrClientes;}
    
    public String linhaAMostrar(){
        return ("Código: " + this.codigo + " Unidades vendidas : " + this. unidadesVendidas + " Clientes : " + this.nrClientes);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        TrioProdutosUnidadesClientes tpuc = (TrioProdutosUnidadesClientes)o;
        if(!(this.codigo.equals(tpuc.getCodigo()))) return false;
        if(this.unidadesVendidas != tpuc.getUnidadesVendidas()) return false;
        if(this.nrClientes != tpuc.getNrClientes()) return false;
        return true;
    }
    
    public TrioProdutosUnidadesClientes clone(){
        return new TrioProdutosUnidadesClientes(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Código: " + this.codigo);
        sb.append("\nUnidades vendidas: " + this.unidadesVendidas);
        sb.append("\nNúmero de Clientes: " + this.nrClientes);
        return sb.toString();
    }
}
