import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;
import HipermercadoExceptions.*;
public class Contabilidade implements Serializable
{
    private Map<String,RegistoContabilidade> contabilidade;

    /*Constutores*/
    public Contabilidade()
    {
        this.contabilidade= new TreeMap<String,RegistoContabilidade>();
    }
    public Contabilidade(Contabilidade c)
    {
        this.contabilidade= c.getContabilidade();
    }
    
    //gets e set
    public Map<String,RegistoContabilidade> getContabilidade(){
        Map<String,RegistoContabilidade> novo = new TreeMap<String,RegistoContabilidade>();
        for(String s: this.contabilidade.keySet()){
            novo.put(s, this.contabilidade.get(s).clone());
        }
        return novo;
    }
    //carregar dados em memoria
    public void addProduto(String codigoProduto){
        RegistoContabilidade novoRegisto = new RegistoContabilidade();
        this.contabilidade.put(codigoProduto,novoRegisto);
    }
    public void updateRegistoContabilidade(String produto, char modo, int mes, int quantidade, double preco)
    throws ProdutoNaoExisteException{
        RegistoContabilidade aux = this.contabilidade.get(produto);
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
        for(RegistoContabilidade r: this.contabilidade.values()){
            if(r.totalVendasAnual() > 0) produtosComprados ++;
        }
        return produtosComprados;
    }
    //query 1.2
    public int comprasMes(int mes){//dado 1 mes (1 a 12) diz qtas compras se realizaram
        int total = 0;
        for(RegistoContabilidade registo : this.contabilidade.values()){
            total += registo.getVendas(mes);
        }
        return total;
    }
    //query 1.1 e 1.2
    public double faturacaoMes(int mes){
        double total = 0;
        for(RegistoContabilidade registo: this.contabilidade.values()){
            total = total + (registo.getFaturacaoN(mes) + registo.getFaturacaoP(mes));
        }
        return total;
    }
    
    //QUERY INTERATIVAS
    //query1
    public Set<String> produtosNuncaComprados(){
        Set<String> produtosNComprados = new TreeSet<String>();
        for(String codigoProduto: this.contabilidade.keySet()){
            if(this.contabilidade.get(codigoProduto).totalVendasAnual() == 0){
                produtosNComprados.add(codigoProduto);
            }
        }
        return produtosNComprados;
    }
    //query6
    public Map<Integer,ParModoFat> xsCompradoEFaturacaoNP(String codigoProduto) throws ProdutoNaoExisteException{
        Map <Integer,ParModoFat> resultado = new TreeMap <Integer,ParModoFat> ();
        for(int i = 1;i<=12; i++){
            resultado.put(new Integer(i), new ParModoFat());
        }
        RegistoContabilidade regProduto = this.contabilidade.get(codigoProduto);
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
    //equals clone e toString
    public Contabilidade clone(){
        return new Contabilidade(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        Contabilidade cont = (Contabilidade)o;
        Map<String,RegistoContabilidade> cont_aux = cont.getContabilidade();
        for(String s: cont_aux.keySet()){
            if(this.contabilidade.get(s) == null){ return false;}
            if(!(this.contabilidade.get(s).equals(cont_aux.get(s)))){ return false;}
        }
        return true;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Esta contabilidade tem:\n");
        sb.append(this.contabilidade.size() + " registos.\n");
        return sb.toString();
    }
}
