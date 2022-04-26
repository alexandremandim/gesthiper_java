import java.io.Serializable;
public class TrioXsCompradoENrCliEFaturado implements Serializable
{
    private int xSComprado, clientesQCompraram;
    private double totalFaturado;

    /* construtores */
    public TrioXsCompradoENrCliEFaturado()
    {
        this.xSComprado = 0;
        this.clientesQCompraram = 0;
        this.totalFaturado = 0;
    }
    
    public TrioXsCompradoENrCliEFaturado(TrioXsCompradoENrCliEFaturado tccf)
    {
        this.xSComprado = tccf.getXsComprado();
        this.clientesQCompraram = tccf.getClientesQCompraram();
        this.totalFaturado = tccf.getTotalFaturado();
    }
    
    //gets e sets
    //gets e set
    public int getXsComprado(){return this.xSComprado;}
    public int getClientesQCompraram(){return this.clientesQCompraram;}
    public double getTotalFaturado(){return this.totalFaturado;}
    
    public void setXsComprado(int xSComprado){this.xSComprado += xSComprado;}
    public void setClientesQCompraram(int clientesQCompraram){this.clientesQCompraram += clientesQCompraram;}
    public void setTotalFaturado(double totalFaturado){this.totalFaturado += totalFaturado;}
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        TrioXsCompradoENrCliEFaturado tccf = (TrioXsCompradoENrCliEFaturado)o;
        if(this.xSComprado != tccf.getXsComprado()) return false;
        if(this.clientesQCompraram != tccf.getClientesQCompraram()) return false;
        if(this.totalFaturado != tccf.getTotalFaturado()) return false;
        return true;
    }
    
    public TrioXsCompradoENrCliEFaturado clone(){
        return new TrioXsCompradoENrCliEFaturado(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vezes comprado: " + this.xSComprado);
        sb.append("\nClientes que compraram: " + this.clientesQCompraram);
        sb.append("\nTotal faturado: " + this.totalFaturado);
        return sb.toString();
    }
}
