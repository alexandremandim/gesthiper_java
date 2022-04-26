import java.io.Serializable;
public class RegistoCompra implements Serializable
{
    // instance variables - replace the example below with your own
    private int vendasN[],vendasP[];
    private int qtd[];
    private double faturacao[];

    /* construtores */
    public RegistoCompra()
    {
        this.vendasN = new int[12];
        this.vendasP = new int[12];
        this.qtd = new int[12];
        this.faturacao = new double[12];
        
        for(int i = 0; i < 12; i++){
            this.vendasN[i] = 0;
            this.vendasP[i] = 0;
            this.qtd[i] = 0;
            this.faturacao[i] = 0;
        }
    }
    
    public RegistoCompra(RegistoCompra reg_comp){
        this.vendasN = new int[12];
        this.vendasP = new int[12];
        this.qtd = new int[12];
        this.faturacao = new double[12];
        
        for(int i = 0; i < 12; i++){
            this.vendasN[i] = reg_comp.getVendasN(i);
            this.vendasP[i] = reg_comp.getVendasP(i);
            this.qtd[i] = reg_comp.getQtd(i);
            this.faturacao[i] = reg_comp.getFaturacao(i);
        }
    }

    public int getVendasN(int mes){return this.vendasN[mes-1];}
    public int getVendasP(int mes){return this.vendasP[mes-1];}
    public int getQtd(int mes){return this.qtd[mes-1];}
    public double getFaturacao(int mes){return this.faturacao[mes-1];}
    
    public void setVendasN(int qtd, int mes){this.vendasN[mes-1] += qtd;}
    public void setVendasP(int qtd, int mes){this.vendasP[mes-1] += qtd;}
    public void setqtd(int qtd, int mes){this.qtd[mes-1] += qtd;}
    public void setFaturacao(double faturacao, int mes){this.faturacao[mes-1] += faturacao;}
    
    public int qtdTotal(){
        int total = 0;
        for(int i=0;i<12;i++){
            total += qtd[i];
        }
        return total;
    }
    
    public double faturacaoAnual(){
        double total = 0;
        for(int i=0;i<12;i++){
            total += faturacao[i];
        }
        return total;
    }
    // clone, equals, toString
    public RegistoCompra clone(){
        return new RegistoCompra(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        RegistoCompra reg_comp = (RegistoCompra)o;
        for(int i = 0; i < 12; i++){
            if(this.vendasN[i] != reg_comp.getVendasN(i)) return false;
            if(this.vendasP[i] != reg_comp.getVendasP(i)) return false;
            if(this.qtd[i] != reg_comp.getQtd(i)) return false;
            if(this.faturacao[i] != reg_comp.getFaturacao(i)) return false;
        }
        return true;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int vN = 0, vP = 0, quant = 0, fat = 0,i;
        for(i = 0; i < 12; i++){
            vN += this.vendasN[i];
            vP += this.vendasP[i];
            quant += this.qtd[i];
            fat += this.faturacao[i];
        }
        sb.append("Número de vendasN total: " + vN);
        sb.append("\nNúmero de vendasP total: " + vP);
        sb.append("\nQuantidade: " + quant);
        sb.append("\nFaturacao: " + fat);
        return sb.toString();
    }
}
