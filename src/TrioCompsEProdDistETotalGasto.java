import java.io.Serializable;
public class TrioCompsEProdDistETotalGasto implements Serializable
{
    private int nrCompras,nrProdutosDistintos;
    private double totalGasto;

    /* construtor */
    public TrioCompsEProdDistETotalGasto()
    {
        this.nrCompras = 0;
        this.nrProdutosDistintos = 0;
        this.totalGasto = 0;
    }
    public TrioCompsEProdDistETotalGasto(int nrCompras, int nrProdutosDistintos, double totalGasto)
    {
        this.nrCompras = nrCompras;
        this.nrProdutosDistintos = nrProdutosDistintos;
        this.totalGasto = totalGasto;
    }
    public TrioCompsEProdDistETotalGasto(TrioCompsEProdDistETotalGasto tcpg)
    {
        this.nrCompras = tcpg.getNrCompras();
        this.nrProdutosDistintos = tcpg.getNrProdutosDistintos();
        this.totalGasto = tcpg.getTotalGasto();
    }
    
    //gets e set
    public int getNrCompras(){return this.nrCompras;}
    public int getNrProdutosDistintos(){return this.nrProdutosDistintos;}
    public double getTotalGasto(){return this.totalGasto;}
    
    public void setNrCompras(int nrCompras){this.nrCompras += nrCompras;}
    public void setNrProdutosDistintos(int nrProdutosDistintos){this.nrProdutosDistintos += nrProdutosDistintos;}
    public void setTotalGasto(double totalGasto){this.totalGasto += totalGasto;}
    
    // toString, equals, clone
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        TrioCompsEProdDistETotalGasto tcpg = (TrioCompsEProdDistETotalGasto)o;
        if(this.nrCompras != tcpg.getNrCompras()) return false;
        if(this.nrProdutosDistintos != tcpg.getNrProdutosDistintos()) return false;
        if(this.totalGasto != tcpg.getTotalGasto()) return false;
        return true;
    }
    
    public TrioCompsEProdDistETotalGasto clone(){
        return new TrioCompsEProdDistETotalGasto(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Número de Compras: " + this.nrCompras);
        sb.append("\nNúmero de produtos distintos: " + this.nrProdutosDistintos);
        sb.append("\nTotal gasto: " + this.totalGasto);
        return sb.toString();
    }
}
