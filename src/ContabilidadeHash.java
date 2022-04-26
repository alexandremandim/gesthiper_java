import java.util.HashMap;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.io.Serializable;
import HipermercadoExceptions.*;
public class ContabilidadeHash implements Serializable
{
    private Map<String,RegistoContabilidade> ContabilidadeHash;

    /*Constutores*/
    public ContabilidadeHash()
    {
        this.ContabilidadeHash= new HashMap<String,RegistoContabilidade>();
    }
    public ContabilidadeHash(ContabilidadeHash c)
    {
        this.ContabilidadeHash= c.getContabilidadeHash();
    }
    
    //gets e set
    public Map<String,RegistoContabilidade> getContabilidadeHash(){
        Map<String,RegistoContabilidade> novo = new HashMap<String,RegistoContabilidade>();
        for(String s: this.ContabilidadeHash.keySet()){
            novo.put(s, this.ContabilidadeHash.get(s).clone());
        }
        return novo;
    }
    //carregar dados em memoria
    public void addProduto(String codigoProduto){
        RegistoContabilidade novoRegisto = new RegistoContabilidade();
        this.ContabilidadeHash.put(codigoProduto,novoRegisto);
    }
    public void updateRegistoContabilidade(String produto, char modo, int mes, int quantidade, double preco)
    throws ProdutoNaoExisteException{
        RegistoContabilidade aux = this.ContabilidadeHash.get(produto);
        if(aux == null){throw new ProdutoNaoExisteException();}
        
        if(modo == 'N'){
            aux.setFaturacaoN(mes,(quantidade*preco));
            aux.setVendasN(mes,quantidade);
            aux.setVendas(mes,1);
        }
        else{
            aux.setFaturacaoP(mes,(quantidade*preco));
            aux.setVendasP(mes,quantidade);
            aux.setVendas(mes,1);
        }
    }
    //query 1.1
    public int totalProdsComprados(){
        int produtosComprados = 0;
        for(RegistoContabilidade r: this.ContabilidadeHash.values()){
            if(r.totalVendasAnual() > 0) produtosComprados ++;
        }
        return produtosComprados;
    }
    //query 1.2
    public int comprasMes(int mes){//dado 1 mes (1 a 12) diz qtas compras se realizaram
        int total = 0;
        for(RegistoContabilidade registo : this.ContabilidadeHash.values()){
            total += registo.getVendas(mes);
        }
        return total;
    }
    //query 1.1 e 1.2
    public double faturacaoMes(int mes){
        double total = 0;
        for(RegistoContabilidade registo: this.ContabilidadeHash.values()){
            total = total + (registo.getFaturacaoN(mes) + registo.getFaturacaoP(mes));
        }
        return total;
    }
    
    //QUERY INTERATIVAS
    //query1
    public Set<String> produtosNuncaComprados(){
        Set<String> produtosNComprados = new HashSet<String>();
        for(String codigoProduto: this.ContabilidadeHash.keySet()){
            if(this.ContabilidadeHash.get(codigoProduto).totalVendasAnual() == 0){
                produtosNComprados.add(codigoProduto);
            }
        }
        return produtosNComprados;
    }
    //query6
    public Map<Integer,ParModoFat> xsCompradoEFaturacaoNP(String codigoProduto) throws ProdutoNaoExisteException{
        Map <Integer,ParModoFat> resultado = new HashMap <Integer,ParModoFat> ();
        for(int i = 1;i<=12; i++){
            resultado.put(new Integer(i), new ParModoFat());
        }
        RegistoContabilidade regProduto = this.ContabilidadeHash.get(codigoProduto);
        if(regProduto == null){throw new ProdutoNaoExisteException();}
        
        for(int i = 1; i<= 12;i++){
            Integer inteiro = new Integer(i);
            resultado.get(inteiro).setXsCompraN(regProduto.getVendasN(i));
            resultado.get(inteiro).setXsCompraP(regProduto.getVendasP(i));
            resultado.get(inteiro).setFatN(regProduto.getFaturacaoN(i));
            resultado.get(inteiro).setFatP(regProduto.getFaturacaoP(i));
        }
        
        return resultado;
    }
    
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.getContabilidadeHash()});
    }
    //equals clone e toString
    public ContabilidadeHash clone(){
        return new ContabilidadeHash(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        ContabilidadeHash cont = (ContabilidadeHash)o;
        Map<String,RegistoContabilidade> cont_aux = cont.getContabilidadeHash();
        for(String s: cont_aux.keySet()){
            if(this.ContabilidadeHash.get(s) == null){ return false;}
            if(!(this.ContabilidadeHash.get(s).equals(cont_aux.get(s)))){ return false;}
        }
        return true;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Esta ContabilidadeHash tem:\n");
        sb.append(this.ContabilidadeHash.size() + " registos.\n");
        return sb.toString();
    }
}

