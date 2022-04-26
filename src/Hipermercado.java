import HipermercadoExceptions.*;
import java.util.*;
import java.io.*;
public class Hipermercado implements Serializable
{
    private CatalogoClientes clientes;
    private CatalogoProdutos produtos;
    private Compras compras;
    private ArrayList<String> linhasComprasInvalidas;
    private Contabilidade contabilidade;
    private String ultimoFileComprasLido, fileProdutos, fileClientes;
    
    /* construtores */
    public Hipermercado()
    {
        this.clientes = new CatalogoClientes();
        this.produtos = new CatalogoProdutos();
        this.compras = new Compras();
        this.linhasComprasInvalidas = new ArrayList<String>();
        this.contabilidade = new Contabilidade();
        this.ultimoFileComprasLido = new String();
        this.fileProdutos = new String();
        this.fileClientes = new String();
    }
    public Hipermercado(Hipermercado hiper){
        this.clientes = hiper.getClientes();
        this.produtos = hiper.getProdutos();
        this.compras = hiper.getCompras();
        this.linhasComprasInvalidas = hiper.getLinhasComprasInvalidas();
        this.contabilidade = hiper.getContabilidade();
        this.ultimoFileComprasLido = hiper.getUltimoFileComprasLido();
        this.fileProdutos = hiper.getFileProdutos();
        this.fileClientes = hiper.getFileClientes();
    }
    
    /* gets */
    CatalogoClientes getClientes(){return this.clientes.clone();}
    CatalogoProdutos getProdutos(){return this.produtos.clone();}
    Compras getCompras(){return this.compras.clone();}
    ArrayList<String> getLinhasComprasInvalidas(){
        ArrayList<String> res = new ArrayList<String>();
        for(String s : this.linhasComprasInvalidas){
            res.add(s);
        }
        return res;
    }
    Contabilidade getContabilidade(){return this.contabilidade.clone();}
    String getUltimoFileComprasLido(){return this.ultimoFileComprasLido;}
    String getFileProdutos(){return this.fileProdutos;}
    String getFileClientes(){return this.fileClientes;}
    /* restante API */
    /* carregar e guardar dados */
    void gravarHipermercadoObject(String nomeFicheiro) throws FileNotFoundException, IOException{
        String nome = new String();
        if(nomeFicheiro == "") nome = "hipermercado.obj";
        else nome = nomeFicheiro;
        FileOutputStream fops = new FileOutputStream(nome);
        ObjectOutputStream o = new ObjectOutputStream(fops);
        o.writeObject(this);o.flush();
        o.close();fops.close();
    }
    //void carregarHipermercadoObject();
    void lerProdutos(String file) throws FileNotFoundException, IOException{
        FileReader arq = new FileReader(file);
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = new String();
        this.fileProdutos = file;
        
        for(linha = lerArq.readLine();linha != null; linha = lerArq.readLine()){
            this.produtos.addProdutos(linha); // adiciona ao catalogo
            this.contabilidade.addProduto(linha);
        }
        lerArq.close();
        arq.close();
    }
    void lerClientes(String file) throws FileNotFoundException, IOException{
        FileReader arq = new FileReader(file);
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = new String();
        this.fileClientes = file;
        
        for(linha = lerArq.readLine();linha != null; linha = lerArq.readLine()){
            this.clientes.addCliente(linha);
        }
        lerArq.close();
        arq.close();
    }
    void lerCompras(String file) throws FileNotFoundException, IOException, ProdutoNaoExisteException{ // quando encontra compras inválidas guarda-as numa estrutura
        FileReader arq = new FileReader(file);
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = new String();
        StringTokenizer st;
        this.ultimoFileComprasLido = file;
        
        String codigoProduto = new String(), codigoCliente = new String();
        double preco = -1;
        int quantidade = -1, mes = -1, i = -1, flagcompraInvalida = 0;;
        char modo = 'X';
        
        for(linha = lerArq.readLine();linha != null; linha = lerArq.readLine()){
            st = new StringTokenizer(linha);
            i = 1;
            flagcompraInvalida = 0;
            while(st.hasMoreTokens() && flagcompraInvalida == 0){
                switch (i){
                    case 1: // produto
                        codigoProduto = st.nextToken();
                        if(!this.produtos.contains(codigoProduto)) flagcompraInvalida = 1;
                        break;
                    case 2: // preco
                        preco = Double.parseDouble(st.nextToken());
                        if(preco < 0 ) flagcompraInvalida = 1;
                        break;
                    case 3: // quantia
                        quantidade = Integer.parseInt(st.nextToken());
                        if(quantidade <= 0) flagcompraInvalida = 1;
                        break;
                    case 4: // modo
                        modo = st.nextToken().charAt(0);
                        if(modo !=  'P' && modo != 'N') flagcompraInvalida = 1;
                        break;
                    case 5: // cliente
                        codigoCliente = st.nextToken();
                        if(!this.clientes.contains(codigoCliente)) flagcompraInvalida = 1;
                        break;
                    case 6: // mes
                        mes = Integer.parseInt(st.nextToken());
                        if(mes < 1 || mes > 12) flagcompraInvalida = 1;
                        break;
                }
                i++;
            }
            if(flagcompraInvalida == 1){ // a compra é inválida
                this.linhasComprasInvalidas.add(linha);
            }
            else{
                
                this.compras.addCompra(codigoCliente,preco,quantidade,modo,codigoProduto,mes);  // mete nas compras
                this.contabilidade.updateRegistoContabilidade(codigoProduto,modo,mes,quantidade,preco); // atualiza contabilidade
            }
        }
        lerArq.close();
        arq.close();
    }
    void lerComprasComParsingBufferedReader(String file) throws FileNotFoundException, IOException, ProdutoNaoExisteException{ // quando encontra compras inválidas guarda-as numa estrutura
        FileReader arq = new FileReader(file);
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = new String();
        StringTokenizer st;
        this.ultimoFileComprasLido = file;
        
        String codigoProduto = new String(), codigoCliente = new String();
        double preco = -1;
        int quantidade = -1, mes = -1, i = -1, flagcompraInvalida = 0;;
        char modo = 'X';
        
        for(linha = lerArq.readLine();linha != null; linha = lerArq.readLine()){
            st = new StringTokenizer(linha);
            i = 1;
            flagcompraInvalida = 0;
            while(st.hasMoreTokens() && flagcompraInvalida == 0){
                switch (i){
                    case 1: // produto
                        codigoProduto = st.nextToken();
                        if(!this.produtos.contains(codigoProduto)) flagcompraInvalida = 1;
                        break;
                    case 2: // preco
                        preco = Double.parseDouble(st.nextToken());
                        if(preco < 0 ) flagcompraInvalida = 1;
                        break;
                    case 3: // quantia
                        quantidade = Integer.parseInt(st.nextToken());
                        if(quantidade <= 0) flagcompraInvalida = 1;
                        break;
                    case 4: // modo
                        modo = st.nextToken().charAt(0);
                        if(modo !=  'P' && modo != 'N') flagcompraInvalida = 1;
                        break;
                    case 5: // cliente
                        codigoCliente = st.nextToken();
                        if(!this.clientes.contains(codigoCliente)) flagcompraInvalida = 1;
                        break;
                    case 6: // mes
                        mes = Integer.parseInt(st.nextToken());
                        if(mes < 1 || mes > 12) flagcompraInvalida = 1;
                        break;
                }
                i++;
            }
        }
        lerArq.close();
        arq.close();
    }
    void lerComprasComParsingScanner(String file) throws FileNotFoundException, IOException, ProdutoNaoExisteException{ // quando encontra compras inválidas guarda-as numa estrutura
        FileReader arq = new FileReader(file);
        Scanner lerArq = new Scanner(arq);
        String linha = new String();
        StringTokenizer st;
        this.ultimoFileComprasLido = file;
        
        String codigoProduto = new String(), codigoCliente = new String();
        double preco = -1;
        int quantidade = -1, mes = -1, i = -1, flagcompraInvalida = 0;;
        char modo = 'X';
            
        while(lerArq.hasNextLine()){
            linha = lerArq.nextLine();
            st = new StringTokenizer(linha);
            i = 1;
            flagcompraInvalida = 0;
            while(st.hasMoreTokens() && flagcompraInvalida == 0){
                switch (i){
                    case 1: // produto
                        codigoProduto = st.nextToken();
                        if(!this.produtos.contains(codigoProduto)) flagcompraInvalida = 1;
                        break;
                    case 2: // preco
                        preco = Double.parseDouble(st.nextToken());
                        if(preco < 0 ) flagcompraInvalida = 1;
                        break;
                    case 3: // quantia
                        quantidade = Integer.parseInt(st.nextToken());
                        if(quantidade <= 0) flagcompraInvalida = 1;
                        break;
                    case 4: // modo
                        modo = st.nextToken().charAt(0);
                        if(modo !=  'P' && modo != 'N') flagcompraInvalida = 1;
                        break;
                    case 5: // cliente
                        codigoCliente = st.nextToken();
                        if(!this.clientes.contains(codigoCliente)) flagcompraInvalida = 1;
                        break;
                    case 6: // mes
                        mes = Integer.parseInt(st.nextToken());
                        if(mes < 1 || mes > 12) flagcompraInvalida = 1;
                        break;
                }
                i++;
            }
        }
        lerArq.close();
        arq.close();
    }
    void lerComprasNoParseBufferedReader(String file) throws FileNotFoundException, IOException, ProdutoNaoExisteException{ // quando encontra compras inválidas guarda-as numa estrutura
        FileReader arq = new FileReader(file);
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = new String();
        
        for(linha = lerArq.readLine();linha != null; linha = lerArq.readLine());
        lerArq.close();
        arq.close();
    }
    void lerComprasNoParseScanner(String file) throws FileNotFoundException, IOException, ProdutoNaoExisteException{ // quando encontra compras inválidas guarda-as numa estrutura
        FileReader arq = new FileReader(file);
        Scanner lerArq = new Scanner(arq);
        String linha = new String();

        while(lerArq.hasNextLine()){
            linha = lerArq.nextLine();
        }
        
        lerArq.close();
        arq.close();
    }
    /* CONSULTAS ESTATISTICAS */
    // 1.1
    public int nrTotalProdutosExistentes(){return this.produtos.totalProdutos();}
    public int nrTotalProdutosComprados(){return this.contabilidade.totalProdsComprados();}
    public int nrTotalClientesExistentes(){return this.clientes.totalClientes();}
    public int nrTotalClientesQRealizaramCompras(){return this.compras.nrClientesQRealizaramCompras();}
    public int nrComprasGratuitas(){return this.compras.getComprasNulas();} // Compras com preco igual a 0
    // 1.2
    //vai a contabilidade, a todos os produtos e ve qtd d x q foram comprados nesses mes(1-12)
    public int nrTotalComprasPMes(int mes) throws MesInvalidoException{
        if(mes<1 || mes >12) throw new MesInvalidoException();
        return this.contabilidade.comprasMes(mes);
    }
    // Nr de clientes distintos q compraram num mes
    public int nrClientesQCompraramNumMes(int mes) throws MesInvalidoException{
        if(mes<1 || mes >12) throw new MesInvalidoException();
        return this.compras.nrClientesQCompraramNumMes(mes);
    }
    public int nrRegistosComprasInvalidas(String nomeFicheiro) throws IOException { // Esta funcao tem que guardar num ficheiro essas linhas
        int total = this.linhasComprasInvalidas.size();
        //escrever num file
        FileWriter fr = new FileWriter(nomeFicheiro);
        BufferedWriter br = new BufferedWriter(fr);
        for(String linha : this.linhasComprasInvalidas){
            br.write(linha);
            br.write("\n");
        }
        br.flush();br.close();fr.close();

        return total;
    }
    // 1.1 e 1.2
    public double faturacaoTotal(int mes)throws MesInvalidoException{
        if(mes<1 || mes >12) throw new MesInvalidoException();
        return this.contabilidade.faturacaoMes(mes);
    }
    
    /* CONSULTAS INTERATIVAS */
    //Query1
    public Set<String> produtosNuncaComprados(){return this.contabilidade.produtosNuncaComprados();} //lista ordenadas dos prods nunca comprados
    //Query2
    public Set<String> clientesQNuncaCompraram(){ //lista ordenadas dos clientes que nunca compraram nada (retira a todos os clientes aqueles q compraram)
        Set<String> clientesQCompraram = this.compras.listaClientesQRealizaramCompras();
        Set<String> clientesQNaoCompraram = this.clientes.getClientes();
        for(String codigoClienteComprou: clientesQCompraram){
            clientesQNaoCompraram.remove(codigoClienteComprou);
        }
        return clientesQNaoCompraram;
    }
    //Query3
    // dado 1 mes determinar o nr total de compras realizadas e o nr clientes distintos q a realizaram
    public ParCompsCli totalComprasEClientesDistintos(int mes) throws MesInvalidoException{
        if(mes<1 || mes >12) throw new MesInvalidoException();
        int nrTotalCompras = this.contabilidade.comprasMes(mes);
        int nrClientesQCompraramNumMes = this.compras.nrClientesQCompraramNumMes(mes);
        ParCompsCli res = new ParCompsCli(nrTotalCompras,nrClientesQCompraramNumMes);
        return res;
    }
    //Query4
    public Map<Integer,TrioCompsEProdDistETotalGasto> nrComprasProdsDistintosTotalGasto(String codigoCliente)
    throws ClienteNaoExisteException, ClienteSemComprasException{
        if(this.clientes.contains(codigoCliente) == false) throw new ClienteNaoExisteException();
        return this.compras.comprasProdutosEGastoNumMes(codigoCliente);
    }
    //Query5
    public Map<Integer,TrioXsCompradoENrCliEFaturado> qtsXCompradoClientesDifFat(String codProduto) throws ProdutoNaoExisteException{
        if(this.produtos.contains(codProduto)==false) throw new ProdutoNaoExisteException();
        return this.compras.xsCompradoClientesEFaturado(codProduto);
    }
    //Query 6
    public Map<Integer,ParModoFat> xsCompradoEFatModoNeP(String codProduto) throws ProdutoNaoExisteException{
        if(this.produtos.contains(codProduto)==false) throw new ProdutoNaoExisteException();
        return this.contabilidade.xsCompradoEFaturacaoNP(codProduto);
    }
    //Query7
    // ordenadacao: decrescente de quantidade e, para qtds iguais, ordem alfabética dos cods;
    public Set<ParCodigoValor> produtosQMaisComprou(String codigoCliente) throws ClienteNaoExisteException, ClienteSemComprasException{
        if(this.clientes.contains(codigoCliente) == false) throw new ClienteNaoExisteException();
        return this.compras.produtosQMaisComprou(codigoCliente);
    }
    //Query8
    public Set<TrioProdutosUnidadesClientes> ProdutosMaisVendidos(){
        return this.compras.produtosMaisVendidos();
    }
    //Query9
    public Set<ParCodigoValor> xcliCompraramMaiorVariedadeProdutos(){ // atencao ordenacao igual a 7
        return this.compras.clientesMaiorVariedadeProdutos();
    }
    //Query10
    public Set<TrioCodigoQtdValor> xCliQMaisCompraramProduto(String codigoProduto) throws ProdutoNaoExisteException{ // atencao ordenacao igual a 7
        if(this.produtos.contains(codigoProduto)==false) throw new ProdutoNaoExisteException();
        return this.compras.maioresCompradoresProduto(codigoProduto);
    }
    /* EQUALS TOSTRING CLONE */
    public Hipermercado clone(){
        return new Hipermercado(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("O hipermercado tem:\n");
        sb.append(produtos.totalProdutos() + " clientes registados.\n");
        sb.append(clientes.totalClientes() + " produtos registados.\n");
        return sb.toString();
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        Hipermercado hiper = (Hipermercado)o;
        
        if(this.linhasComprasInvalidas.size() != hiper.getLinhasComprasInvalidas().size()){ return false;}
        for(String s: hiper.getLinhasComprasInvalidas()){if(!this.linhasComprasInvalidas.contains(s)) return false;}
        return (this.clientes.equals(hiper.getClientes()) && this.produtos.equals(hiper.getProdutos()) && this.compras.equals(hiper.getCompras()) 
                && this.contabilidade.equals(hiper.getContabilidade()) && this.ultimoFileComprasLido.equals(hiper.getUltimoFileComprasLido())
                && this.fileProdutos.equals(hiper.getFileProdutos()) && this.fileClientes.equals(hiper.getFileClientes()));
    }
}
