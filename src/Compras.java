import java.util.TreeMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import java.io.Serializable;
import HipermercadoExceptions.*;

public class Compras implements Serializable
{
    private Map<String,Map<String,RegistoCompra>> compras;
    private int comprasNulas;

    /* Construtores */
    public Compras()
    {
        this.compras = new TreeMap<String,Map<String,RegistoCompra>>();
        this.comprasNulas = 0;
    }
    public Compras(Compras c)
    {
        this.compras = c.getCompras();
        this.comprasNulas = c.getComprasNulas();
    }
    
    //gets e sets
    public int getComprasNulas(){
        return this.comprasNulas;
    }
    public Map<String,Map<String,RegistoCompra>> getCompras(){
        Map<String,Map<String,RegistoCompra>> novo = new TreeMap<String,Map<String,RegistoCompra>>();
        
        for(String cod_cli: this.compras.keySet()){
            Map<String,RegistoCompra> novo_in = new TreeMap<String,RegistoCompra>();
            for(String cod_prod: (this.compras.get(cod_cli)).keySet()){
                novo_in.put(cod_prod,((this.compras.get(cod_cli)).get(cod_prod)).clone());
            }
            novo.put(cod_cli,novo_in);
        }
        return novo;
    }
    public void addCompra(String codigoCliente, double preco, int quantidade, char modo, String codigoProduto, int mes){
        if(preco == 0) this.comprasNulas++;
        
        if(this.compras.containsKey(codigoCliente)){    // ja tem cod cliente
            Map<String,RegistoCompra> produtos = this.compras.get(codigoCliente);
            if(produtos.containsKey(codigoProduto)){ // ja existe o produto
                RegistoCompra reg = produtos.get(codigoProduto);
                if(modo == 'N'){reg.setVendasN(1,mes);}
                else{reg.setVendasP(1,mes);}
                reg.setqtd(quantidade,mes);
                reg.setFaturacao(preco*quantidade,mes);
            }
            else{   // ainda nao existe o produto
                RegistoCompra novoRegistoCompra = new RegistoCompra();  // cria Registo relativo ao codProduto
                if(modo == 'N'){novoRegistoCompra.setVendasN(1,mes);}
                else{novoRegistoCompra.setVendasP(1,mes);}
                novoRegistoCompra.setqtd(quantidade,mes);
                novoRegistoCompra.setFaturacao(preco*quantidade,mes);
                produtos.put(codigoProduto,novoRegistoCompra);
            }
        }
        
        else{   // ainda nao
            Map<String,RegistoCompra> novoTreeProdutos = new TreeMap<String,RegistoCompra>();   // criar treeProdutos
            
            RegistoCompra novoRegistoCompra = new RegistoCompra();  // cria Registo relativo ao codProduto
            if(modo == 'N'){novoRegistoCompra.setVendasN(1,mes);}
            else{novoRegistoCompra.setVendasP(1,mes);}
            novoRegistoCompra.setqtd(quantidade,mes);
            novoRegistoCompra.setFaturacao(preco*quantidade,mes);
            
            novoTreeProdutos.put(codigoProduto,novoRegistoCompra);  // insere na tree dos produtos
            
            this.compras.put(codigoCliente,novoTreeProdutos);   // adiciona o cliente
        }
    }
    public int nrClientesQRealizaramCompras(){return this.compras.size();}
    public Set<String> listaClientesQRealizaramCompras(){
        Set<String> clientesQCompraram = new TreeSet<String>();
        
        for(String codigoCliente : this.compras.keySet()){
            clientesQCompraram.add(codigoCliente);
        }
        return clientesQCompraram;
    }
    
    //1.2
    public int nrClientesQCompraramNumMes(int mes){ // query 3 tbm usa esta func
        int total = 0;
        for(Map<String,RegistoCompra> treeProds : this.compras.values()){ // vai a todos os clientes
            for(RegistoCompra r : treeProds.values()){// vai aos produtos de 1 clientes
                if(r.getQtd(mes) > 0){ // ve se encontr algum propduto comprado nesse mes
                    total++;break;
                }
            }
        }
        return total;
    }
    
    // QUERIES INTERATIVAS
    //Query 4
    //Devolve para todos os meses as compras, o nr prods distintos e o total gasto por um cliente
    public Map <Integer,TrioCompsEProdDistETotalGasto> comprasProdutosEGastoNumMes(String codigoCliente)
    throws ClienteSemComprasException{
        Map <Integer,TrioCompsEProdDistETotalGasto> resultado = new TreeMap <Integer,TrioCompsEProdDistETotalGasto> ();
        for(int i = 1;i<=12; i++){
            resultado.put(new Integer(i), new TrioCompsEProdDistETotalGasto());
        }
        
        Map<String,RegistoCompra> minhasCompras = this.compras.get(codigoCliente);
        if(minhasCompras == null){throw new ClienteSemComprasException();}
        else{
            TrioCompsEProdDistETotalGasto aux;
            int nrComprasMes; double totalGastoMes;
            
            for(RegistoCompra registo : minhasCompras.values()){ // Vai a todos os codigos de Produto
                for(int i=1; i<=12;i++){    // Para cada codigo vai aos meses todos
                    nrComprasMes = registo.getVendasN(i) + registo.getVendasP(i); // -> total de vezes q foi comprar este produto neste mes
                    totalGastoMes = registo.getFaturacao(i);
       
                    if(nrComprasMes > 0 ) resultado.get(new Integer(i)).setNrProdutosDistintos(1);
                    resultado.get(new Integer(i)).setNrCompras(nrComprasMes);
                    resultado.get(new Integer(i)).setTotalGasto(totalGastoMes);
                }
            }
        }
        return resultado;
    }
    //Query5
    //dado um produto devolve pra cada mes qts x foi comprado, qts clientes diferentes e total faturado
    public Map<Integer,TrioXsCompradoENrCliEFaturado> xsCompradoClientesEFaturado(String codigoProduto){
        Map <Integer,TrioXsCompradoENrCliEFaturado> resultado = new TreeMap <Integer,TrioXsCompradoENrCliEFaturado> ();
        for(int i = 1;i<=12; i++){
            resultado.put(new Integer(i), new TrioXsCompradoENrCliEFaturado());
        }
        
        RegistoCompra regProduto;
        int xscomprado = 0;
        
        for(Map<String,RegistoCompra> treeCli : this.compras.values()){ // Vai a todos os clientes
            regProduto = treeCli.get(codigoProduto); // "Pega" no registo de cada cliente relativo ao codigoProduto em questao
            if(regProduto != null){
                for(int i = 1;i<=12; i++){
                    xscomprado = regProduto.getVendasN(i)+regProduto.getVendasP(i);
                    if(xscomprado>0)resultado.get(new Integer(i)).setClientesQCompraram(1);
                    resultado.get(new Integer(i)).setXsComprado(xscomprado);
                    resultado.get(new Integer(i)).setTotalFaturado(regProduto.getFaturacao(i));
                }
            }
        }
        
        return resultado;
    }
    //Query7
    public Set<ParCodigoValor> produtosQMaisComprou(String codigoCliente) throws ClienteSemComprasException{
        Set<ParCodigoValor> resultado = new TreeSet<ParCodigoValor>(new ComparatorQtdECod());
        Map<String,RegistoCompra> produtosDoCliente = this.compras.get(codigoCliente);
        if(produtosDoCliente == null){throw new ClienteSemComprasException();}
        
        String codigo = new String();
        double valor = 0;
        
        for(String codigoProduto: produtosDoCliente.keySet()){
            ParCodigoValor par = new ParCodigoValor(codigoProduto,produtosDoCliente.get(codigoProduto).qtdTotal());
            resultado.add(par);
        }
        return resultado;
    }
    //Query8
    public Set<TrioProdutosUnidadesClientes> produtosMaisVendidos(){
        Map<String,TrioProdutosUnidadesClientes> todosProdutos = new TreeMap<String,TrioProdutosUnidadesClientes>();
        
        for(String codigoCliente : this.compras.keySet()){
            Map<String,RegistoCompra> prods = this.compras.get(codigoCliente);
            for(String codigoProduto: prods.keySet()){
                if(todosProdutos.containsKey(codigoProduto)){  // o codigo ja esta na tree
                    TrioProdutosUnidadesClientes aux = todosProdutos.get(codigoProduto);
                    aux.setUnidadesVendidas(prods.get(codigoProduto).qtdTotal());
                    aux.setNrClientes(1);
                }
                else{
                    TrioProdutosUnidadesClientes novo = new TrioProdutosUnidadesClientes(codigoProduto, prods.get(codigoProduto).qtdTotal(),1);
                    todosProdutos.put(codigoProduto, novo);
                }
            }
        }
        Set<TrioProdutosUnidadesClientes> resultado = new TreeSet<TrioProdutosUnidadesClientes>(new ComparatorUnidadesVendidas());
        
        for(String s: todosProdutos.keySet()){
            resultado.add(todosProdutos.get(s));
        }
        
        return resultado;
    }
    //Query9
    public Set<ParCodigoValor> clientesMaiorVariedadeProdutos(){
        Set<ParCodigoValor> resultado = new TreeSet<ParCodigoValor>(new ComparatorQtdECod());
        
        for(String codigoCliente : this.compras.keySet()){
            ParCodigoValor par = new ParCodigoValor(codigoCliente, this.compras.get(codigoCliente).size());
            resultado.add(par);
        }
        return resultado;
    }
    //Query10
    public Set<TrioCodigoQtdValor> maioresCompradoresProduto(String codigoProduto){
        Set<TrioCodigoQtdValor> resultado = new TreeSet<TrioCodigoQtdValor>(new ComparatorQtdECodQ10());
        
        for(String codigoCliente : this.compras.keySet()){
            RegistoCompra aux = this.compras.get(codigoCliente).get(codigoProduto);
            if(aux!=null){
                TrioCodigoQtdValor novoPar = new TrioCodigoQtdValor(codigoCliente, aux.qtdTotal(), aux.faturacaoAnual());
                resultado.add(novoPar);
            }
        }
        return resultado;
    }
    // clone, equals, toString
    public Compras clone(){
        return new Compras(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        Compras comp = (Compras)o;
        if (this.getComprasNulas() != comp.getComprasNulas()) return false;
        Map<String,Map<String,RegistoCompra>> comp_aux = comp.getCompras();
        for(String cod_cli: comp_aux.keySet()){
            if(this.compras.get(cod_cli) == null){ return false;}
            for(String cod_prod: comp_aux.get(cod_cli).keySet()){
                if(!(((this.compras.get(cod_cli)).get(cod_prod)).equals(((comp_aux.get(cod_cli)).get(cod_prod))))){ return false;}
            }
        }
        return true;
    }
    
    public String toString(){
        int total_reg_prod = 0;
        for(Map<String,RegistoCompra> reg_comp: this.compras.values()){
            total_reg_prod += reg_comp.size();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Estas compras têm:\n");
        sb.append(this.compras.size() + " registos de clientes diferentes.\n");
        sb.append(total_reg_prod + " registos de compras diferentes.\n");
        return sb.toString();
    }
}
