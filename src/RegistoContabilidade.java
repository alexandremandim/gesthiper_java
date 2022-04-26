import java.io.Serializable;
public class RegistoContabilidade implements Serializable
{
    private double faturacaoN[], faturacaoP[];
    private int vendasN[],vendasP[], vendas[];

    /* Construtores */
    public RegistoContabilidade(){
        this.faturacaoN = new double[12];
        this.faturacaoP = new double[12];
        this.vendasN = new int[12];
        this.vendasP = new int[12];
        this.vendas = new int[12];
        
        for(int i=0;i<12;i++){
            this.faturacaoN[i] = 0;
            this.faturacaoP[i] = 0;
            this.vendasN[i] = 0;
            this.vendasP[i] = 0;
            this.vendas[i] = 0;
        }
    }
    
    public RegistoContabilidade(RegistoContabilidade reg_cont){
        this.faturacaoN = new double[12];
        this.faturacaoP = new double[12];
        this.vendasN = new int[12];
        this.vendasP = new int[12];
        this.vendas = new int[12];
        
        for(int i=0;i<12;i++){
            this.faturacaoN[i] = reg_cont.getFaturacaoN(i);
            this.faturacaoP[i] = reg_cont.getFaturacaoP(i);
            this.vendasN[i] = reg_cont.getVendasN(i);
            this.vendasP[i] = reg_cont.getVendasP(i);
            this.vendas[i] = reg_cont.getVendas(i);
        }
    }
    
    // gets e set
    public double getFaturacaoN(int mes){return this.faturacaoN[mes-1];}
    public double getFaturacaoP(int mes){return this.faturacaoP[mes-1];}
    public int getVendasN(int mes){return this.vendasN[mes-1];}
    public int getVendasP(int mes){return this.vendasP[mes-1];}
    public int getVendas(int mes){return this.vendas[mes-1];}
    public void setFaturacaoN(int mes, double valor){this.faturacaoN[mes-1] += valor;}
    public void setFaturacaoP(int mes, double valor){this.faturacaoP[mes-1] += valor;}
    public void setVendasN(int mes, int qtd){this.vendasN[mes-1] += qtd;}
    public void setVendasP(int mes, int qtd){this.vendasP[mes-1] += qtd;}
    public void setVendas(int mes, int qtd){this.vendas[mes-1] += qtd;}
    
    public int totalVendasAnual(){
        int totalVendas = 0;
        for(int i = 0; i<12; i++){
            totalVendas += vendas[i];
        }
        return totalVendas;
    }
    
    //equals toString e clone
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        RegistoContabilidade reg_cont = (RegistoContabilidade)o;
        for(int i = 0; i < 12; i++){
            if(this.vendasN[i] != reg_cont.getVendasN(i)) return false;
            if(this.vendasP[i] != reg_cont.getVendasP(i)) return false;
            if(this.vendas[i] != reg_cont.getVendas(i)) return false;
            if(this.faturacaoN[i] != reg_cont.getFaturacaoN(i)) return false;
            if(this.faturacaoP[i] != reg_cont.getFaturacaoP(i)) return false;
        }
        return true;
    }
    
    public RegistoContabilidade clone(){
        return new RegistoContabilidade(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int vN = 0, vP = 0, nrV = 0, fatN = 0, fatP = 0,i;
        for(i = 0; i < 12; i++){
            vN += this.vendasN[i];
            vP += this.vendasP[i];
            nrV += this.vendas[i];
            fatN += this.faturacaoN[i];
            fatP += this.faturacaoP[i];
        }
        sb.append("Número de vendasN total: " + vN);
        sb.append("\nNúmero de vendasP total: " + vP);
        sb.append("\nNúmero de vendas total: " + nrV);
        sb.append("\nFaturação em modo N total: " + fatN);
        sb.append("\nFaturação em modo P total: " + fatP);
        return sb.toString();
    }
}
