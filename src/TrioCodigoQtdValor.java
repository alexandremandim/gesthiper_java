import java.io.Serializable;
public class TrioCodigoQtdValor implements Serializable, MostrarCodigos
{
    private String codigo;
    private int quantidade;
    private double valor;

    /* construtores */
    public TrioCodigoQtdValor()
    {
        codigo = new String();
        this.quantidade = 0;
        this.valor = 0;
    }
    public TrioCodigoQtdValor(String codigo, int qtd, double valor)
    {
        this.codigo = codigo;
        this.quantidade = qtd;
        this.valor = valor;
    }
    public TrioCodigoQtdValor(TrioCodigoQtdValor tcqv)
    {
        this.codigo = tcqv.getCodigo();
        this.quantidade = tcqv.getQuantidade();
        this.valor = tcqv.getValor();
    }

    // gets e sets
    public String getCodigo(){return codigo;}
    public int getQuantidade(){return quantidade;}
    public double getValor(){return valor;}
    
    public void setCodigo(String codigo){this.codigo = codigo;}
    public void setQuantidade(int unidadesVendidas){this.quantidade += quantidade;}
    public void setValor(double valor){this.valor += valor;}
    
    public String linhaAMostrar(){
        return "Código: "+this.codigo+" Quantidade: " + this.quantidade +
                " Valor: " + this.valor;
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        TrioCodigoQtdValor tcqc = (TrioCodigoQtdValor)o;
        if(!(this.codigo.equals(tcqc.getCodigo()))) return false;
        if(this.quantidade != tcqc.getQuantidade()) return false;
        if(this.valor != tcqc.getValor()) return false;
        return true;
    }
    
    public TrioCodigoQtdValor clone(){
        return new TrioCodigoQtdValor(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Código: " + this.codigo);
        sb.append("\nQuantidade: " + this.quantidade);
        sb.append("\nValor: " + this.valor);
        return sb.toString();
    }
}
