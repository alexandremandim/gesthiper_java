import java.io.Serializable;
public class ParCodigoValor implements Serializable, MostrarCodigos
{
    // instance variables - replace the example below with your own
    private String codigo;
    private int valor;

    public ParCodigoValor()
    {
        codigo = new String();
        valor = 0;
    }
    public ParCodigoValor(String codigo, int valor)
    {
        this.codigo = codigo;
        this.valor = valor;
    }
    public ParCodigoValor(ParCodigoValor pcv)
    {
        this.codigo = pcv.getCodigo();
        this.valor = pcv.getValor();
    }

    // gets e sets
    public String getCodigo(){return this.codigo;}
    public int getValor(){return this.valor;}
    public void setCodigo(String codigo){this.codigo = codigo;}
    public void setValor(int valor){this.valor += valor;}
    
    public String linhaAMostrar(){
        return ("Código: " + this.codigo + " Valor ->" + this.valor);
    }
    //equals, tos
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        ParCodigoValor pcv = (ParCodigoValor)o;
        if(!(this.codigo.equals(pcv.getCodigo()))) return false;
        if(this.valor != pcv.getValor()) return false;
        return true;
    }
    
    public ParCodigoValor clone(){
        return new ParCodigoValor(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: " + this.codigo);
        sb.append("\nValor: " + this.valor);
        return sb.toString();
    }
}
