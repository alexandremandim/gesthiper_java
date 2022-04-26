import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.io.*;
import HipermercadoExceptions.*;

public class HiperMercadoTestar
{
    public HiperMercadoTestar(){}
    private Hipermercado carregarDados(){
        Hipermercado meuHiper;
        Scanner input = new Scanner(System.in);
        
        System.out.println("Como pretende carregar os dados em memória?");
        System.out.println("1 -> Carregar objeto;");
        System.out.println("2 -> Ler ficheiros.");
        System.out.println("0 -> Sair.");
        int escolha = input.nextInt();
        
        switch(escolha){
            case 1:
                System.out.println("Introduzir ficheiro?\n1 -> Sim\n2 -> Nao");
                escolha = input.nextInt();
                if(escolha==1){
                    System.out.println("Ficheiro:");
                    String ficheiro = input.next();
                    try{ 
                        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(ficheiro));
                        meuHiper = (Hipermercado)oin.readObject();
                        oin.close();
                        return meuHiper;
                    }
                    catch(FileNotFoundException e){System.out.println("Nao existe esse ficheiro.");}
                    catch(IOException e){}
                    catch(ClassNotFoundException e){}
                }
                else{
                    try{ 
                        ObjectInputStream oin = new ObjectInputStream(new FileInputStream("hipermercado.obj"));
                        System.out.println("A carregar estado do programa...");
                        meuHiper = (Hipermercado)oin.readObject();
                        oin.close();
                        return meuHiper;
                    }
                    catch(FileNotFoundException e){System.out.println("Nao existe esse ficheiro.");}
                    catch(IOException e){}
                    catch(ClassNotFoundException e){}
                }
                break;
            case 2:
                meuHiper = new Hipermercado();
                try{
                    meuHiper.lerProdutos("../files/produtos.txt");
                    meuHiper.lerClientes("../files/clientes.txt");
                    System.out.println("Ficheiro de compras: ");
                    String ficheiro = input.next();
                    meuHiper.lerCompras(ficheiro);
                    return meuHiper;
                } 
                catch(FileNotFoundException e){System.out.println("Nao existe esse ficheiro.");}
                catch(IOException e){}
                catch(ProdutoNaoExisteException e){System.out.println("Erro: Tentou atualizar a contabilidade com produto que nao existe.");}
                break;
            case 0:
                return null;
                
        }
        return null;
    }
    private void menuEstatisticas(Hipermercado meuHiper){
        int escolha = -1,mes = 0, resultado = 0;double resultadoDouble = 0;
        Scanner input = new Scanner(System.in);
        
        while(escolha != 0){
            System.out.println("------------Menu Estatisticas------------");
            System.out.println("\n1.1-----------");
            System.out.println("1 -> Nome dos ficheiros");
            System.out.println("2 -> Número total de produtos");
            System.out.println("3 -> Total de produtos diferentes comprados");
            System.out.println("4 -> Total de produtos não comprados");
            System.out.println("5 -> Número total de clientes");
            System.out.println("6 -> Número total de clientes que realizaram compras");
            System.out.println("7 -> Número total de clientes que não realizaram compras");
            System.out.println("8 -> Número total de compras gratuitas (preço = 0)");
            System.out.println("9 -> Faturacao total");
            System.out.println("\n1.2-----------");
            System.out.println("10 -> Número total de compras num mes");
            System.out.println("11 -> Faturacação total num mes");
            System.out.println("12 -> Número de clientes que compraram num mes");
            System.out.println("13 -> Compras inválidas (gravar num ficheiro)");
            System.out.println("0 -> Sair");
            escolha = input.nextInt();
            switch(escolha){
                case 1:
                    System.out.println("Clientes: " + meuHiper.getFileClientes());
                    System.out.println("Produtos: " + meuHiper.getFileProdutos());
                    System.out.println("Compras: " + meuHiper.getUltimoFileComprasLido());
                    break;
                case 2:
                    Crono.start();
                    int totalProdutos = meuHiper.nrTotalProdutosExistentes();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Número total de produtos: " + totalProdutos );
                    break;
                case 3:
                    Crono.start();
                    int produtosComprados = meuHiper.nrTotalProdutosComprados();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Total de produtos diferentes comprados: " + produtosComprados);
                    break;
                case 4:
                    Crono.start();
                    resultado = (meuHiper.nrTotalProdutosExistentes() - meuHiper.nrTotalProdutosComprados());
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Total de produtos não comprados: " + resultado);
                    break;
                case 5:
                    Crono.start();
                    resultado = meuHiper.nrTotalClientesExistentes();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Total de clientes: " + resultado);
                    break;
                case 6:
                    Crono.start();
                    resultado = meuHiper.nrTotalClientesQRealizaramCompras();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Total de clientes que compraram: " + resultado);
                    break;
                case 7:
                    Crono.start();
                    resultado = ( meuHiper.nrTotalClientesExistentes() - meuHiper.nrTotalClientesQRealizaramCompras());
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Total de clientes que nada compraram: " + resultado);
                    break;
                case 8:
                    Crono.start();
                    resultado = ( meuHiper.nrComprasGratuitas());
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Compras com preco iguala 0: " + resultado);
                    break;
                case 9:
                    try{
                        double ft = 0;
                        Crono.start();
                        for(int i=1;i<=12;i++){
                            ft = ft + meuHiper.faturacaoTotal(i);
                        }
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        System.out.println("Faturacao total: " + ft);
                    }
                    catch(MesInvalidoException e){}
                    break;
                case 10:
                    System.out.println("Insira o mes:");
                    mes = input.nextInt();
                    try{
                        Crono.start();
                        resultado = meuHiper.nrTotalComprasPMes(mes);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        System.out.println("Número total de compras: " + resultado);
                    }
                    catch(MesInvalidoException e){System.out.println("Mês inválido.");}
                    break;
                case 11:
                    System.out.println("Insira o mes:");
                    mes = input.nextInt();
                    try{
                        Crono.start();
                        resultadoDouble = meuHiper.faturacaoTotal(mes);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        System.out.println("Faturacao total: " + resultadoDouble);
                    }
                    catch(MesInvalidoException e){System.out.println("Mês inválido.");}
                    break;
                case 12:
                    System.out.println("Insira o mes:");
                    mes = input.nextInt();
                    try{
                        Crono.start();
                        resultado = meuHiper.nrClientesQCompraramNumMes(mes);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        System.out.println("Número de clientes: " + resultado);
                    }
                    catch(MesInvalidoException e){System.out.println("Mês inválido.");}
                    break;
                case 13:
                    System.out.println("Nome ficheiro:");
                    String nomeFicheiro = input.next();
                    try{
                        Crono.start();
                        resultado = meuHiper.nrRegistosComprasInvalidas(nomeFicheiro);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        System.out.println("Compras inválidas: " + resultado);
                    }
                    catch(IOException e){System.out.println("Erro.");}
                    break;
                case 0:
                    break;
            }
        }
    }
    private void menuInterativo(Hipermercado meuHiper){
        int escolha = -1, mes = -1, numero = -1, contador = 0;
        Scanner input = new Scanner(System.in);
        String codigoCliente = new String(), codigoProduto = new String();
        Iterator<String> it;
        ArrayList<String> strings;
        
        while(escolha != 0){
            System.out.println("------------Menu Interativo------------");
            System.out.println("1 -> Produtos nunca comprados e total");
            System.out.println("2 -> Clientes que nunca compraram nada");
            System.out.println("3 -> Dado um mês, número total de compras e o total de clientes que as realizaram");
            System.out.println("4 -> Dado um cliente, determinar quantas compras fez, produtos comprou, quanto gastou e total anual facturado");
            System.out.println("5 -> Dado um produto, determinar quantas vezes foi comprado, por quantos clientes diferentes e o total facturado");
            System.out.println("6 -> Dado um produto, determinar quantas vezes foi comprado em modo N e em modo P e respectivas facturações");
            System.out.println("7 -> Dado um cliente determinar os códigos de produtos que mais comprou");
            System.out.println("8 -> Produtos mais vendidos em todo o ano e número total de distintos clientes que ocompraram");
            System.out.println("9 -> Clientes que compraram um maior número de diferentes produtos");
            System.out.println("10 -> Dado um produto, determinar o conjunto dos X clientes que mais ocompraram e qual o valor gasto");
            System.out.println("0 -> Sair");
            escolha = input.nextInt();
            
            switch(escolha){
                case 1:
                    Crono.start();
                    Set<String> produtosNuncaComprados = meuHiper.produtosNuncaComprados();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    System.out.println("Existem " + produtosNuncaComprados.size() + " produtos que nunca foram comprados");
                    
                    strings = new ArrayList<String>();
                    it = produtosNuncaComprados.iterator();
                    while(it.hasNext()){
                        strings.add(it.next());
                    }
                    mostrarTreeSetStrings(strings);
                    break;
                case 2:
                    Crono.start();
                    Set<String> clientesNuncaCompraram = meuHiper.clientesQNuncaCompraram();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    
                    System.out.println("Existem " + clientesNuncaCompraram.size() + " clientes que nunca compraram nada.");
                    
                    strings = new ArrayList<String>();
                    it = clientesNuncaCompraram.iterator();
                    while(it.hasNext()){
                        strings.add(it.next());
                    }
                    mostrarTreeSetStrings(strings);
                    break;
                case 3:
                    System.out.println("Insira o mes:");
                    mes = input.nextInt();
                    try{
                        Crono.start();
                        ParCompsCli par = meuHiper.totalComprasEClientesDistintos(mes);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        System.out.println("Neste mes fora feitas " + par.getNrCompras() + " compras por " + par.getNrClientes() + " clientes");
                    }
                    catch(MesInvalidoException e){System.out.println("Mês inválido.");}
                    break;  
                case 4:
                    System.out.println("Código de cliente:");
                    codigoCliente = input.next();
                    try{
                        Crono.start();
                        Map<Integer,TrioCompsEProdDistETotalGasto> resultado = meuHiper.nrComprasProdsDistintosTotalGasto(codigoCliente);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        for(Integer i: resultado.keySet()){
                            System.out.println("Mes: " + i.intValue() + "\nNr Compras : " + resultado.get(i).getNrCompras() + "\nProdutos diferentes comprados: " +
                            resultado.get(i).getNrProdutosDistintos()+ "\nGastado: " + resultado.get(i).getTotalGasto() + "\n");
                        }
                    }
                    catch(ClienteNaoExisteException e){System.out.println("Esse cliente nao existe.");}
                    catch(ClienteSemComprasException e){System.out.println("Esse cliente nao tem compras.");}
                    break;
                case 5:
                    System.out.println("Código de produto:");
                    codigoProduto = input.next();
                    try{
                        Crono.start();
                        Map<Integer,TrioXsCompradoENrCliEFaturado> resultado = meuHiper.qtsXCompradoClientesDifFat(codigoProduto);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        for(Integer i: resultado.keySet()){
                            System.out.println("Mes: " + i.intValue() + "\nVezes comprado : " + resultado.get(i).getXsComprado() + "\nClientes que compraram: " +
                            resultado.get(i).getClientesQCompraram()+ "\nTotal faturado: " + resultado.get(i).getTotalFaturado() + "\n");
                        }
                    }
                    catch(ProdutoNaoExisteException e){System.out.println("Esse produto nao existe.");}
                    break;
                case 6:
                    System.out.println("Código de produto:");
                    codigoProduto = input.next();
                    try{
                        Crono.start();
                        Map<Integer,ParModoFat> resultado = meuHiper.xsCompradoEFatModoNeP(codigoProduto);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        for(Integer i: resultado.keySet()){
                            System.out.println("Mes: " + i.intValue() + "\nVezes comprado N : " + resultado.get(i).getXsCompraN()  + "\nVezes comprado P: " +
                            resultado.get(i).getXsCompraP() + "\nFaturado N: " + resultado.get(i).getFatN() + "\nFaturado P: " + resultado.get(i).getFatP()  + "\n");
                        }
                    }
                    catch(ProdutoNaoExisteException e){System.out.println("Esse produto nao existe.");}
                    break;
                case 7:
                    System.out.println("Código de cliente:");
                    codigoCliente = input.next();
                    try{
                        Crono.start();
                        Set<ParCodigoValor> resultado = meuHiper.produtosQMaisComprou(codigoCliente);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        Iterator<ParCodigoValor> itQ7 = resultado.iterator();
                        strings = new ArrayList<String>();
                        
                        while(itQ7.hasNext()){
                            strings.add(itQ7.next().linhaAMostrar());
                        }
                        mostrarTreeSetStrings(strings);
                    }
                    catch(ClienteNaoExisteException e){System.out.println("Esse cliente nao existe.");}
                    catch(ClienteSemComprasException e){System.out.println("Esse cliente nao tem compras.");}
                    break;
                case 8:
                    System.out.println("Indique o número de produtos mais vendidos:");
                    numero = input.nextInt();
                    
                    Crono.start();
                    Set<TrioProdutosUnidadesClientes> resultado = meuHiper.ProdutosMaisVendidos();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    Iterator<TrioProdutosUnidadesClientes> itQ8 = resultado.iterator();
                    strings = new ArrayList<String>();
                    
                    contador = 0;
                    while(itQ8.hasNext() && contador < numero){
                        strings.add(itQ8.next().linhaAMostrar());
                        contador++;
                    }
                    mostrarTreeSetStrings(strings);
                    break;
                case 9:
                    System.out.println("Indique o número de clientes que compraram mais variedade");
                    numero = input.nextInt();
                    
                    Crono.start();
                    Set<ParCodigoValor> res = meuHiper.xcliCompraramMaiorVariedadeProdutos();
                    Crono.stop();
                    System.out.println("Executado em " + Crono.print() + " segundos.");
                    Iterator<ParCodigoValor> itQ9 = res.iterator();
                    strings = new ArrayList<String>();
                    
                    contador = 0;
                    while(itQ9.hasNext() && contador < numero){
                        strings.add(itQ9.next().linhaAMostrar());
                        contador++;
                    }
                    mostrarTreeSetStrings(strings);
                    break;
                case 10:
                    System.out.println("Código de produto:");
                    codigoProduto = input.next();
                    try{
                        Crono.start();
                        Set<TrioCodigoQtdValor> maisCompradores = meuHiper.xCliQMaisCompraramProduto(codigoProduto);
                        Crono.stop();
                        System.out.println("Executado em " + Crono.print() + " segundos.");
                        Iterator<TrioCodigoQtdValor> itQ10 = maisCompradores.iterator();
                        strings = new ArrayList<String>();
                        
                        while(itQ10.hasNext()){
                            strings.add(itQ10.next().linhaAMostrar());
                        }
                        mostrarTreeSetStrings(strings);
                    }
                    catch(ProdutoNaoExisteException e){System.out.println("Esse produto nao existe.");}
                    break;
            }
        }
    }
    private void gravarObjeto(Hipermercado hiper, String nome){
        try{
            FileOutputStream fops = new FileOutputStream(nome);
            ObjectOutputStream oout = new ObjectOutputStream(fops); 
            System.out.println("A gravar o estado do programa...");
            oout.writeObject(hiper);
            oout.flush();
            oout.close();
            fops.close();
            System.out.println("Adeus");
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
     
    private void mostrarTreeSetStrings(ArrayList<String> dados){
        if(dados.size() == 0) return;
        
        int i = 0, nrPaginas =  dados.size()/20, pagina = 0, escolha = -1, stringsUltimaPagina = 0, escolhaDePagina = -1; 
        Scanner input = new Scanner(System.in);
        stringsUltimaPagina = dados.size()%20;
        
        if(dados.size()%20 > 0){
            nrPaginas = dados.size()/20+1;
        }
        else{
            nrPaginas = dados.size()/20;
        }
        
        // mostrar 1 pagina
        System.out.println("Página: " + (pagina+1) + " de " + (nrPaginas));
        if(nrPaginas == 1){
            mostrarStrings(dados,20*pagina, stringsUltimaPagina);
        }
        else{
            mostrarStrings(dados,20*pagina, 20);
        }
        
        while(escolha != 0){
            System.out.println("0 -> Sair\n1 -> Proxima página\n2 -> Página anterior\n3 -> Escolher página");
            escolha = input.nextInt();
            switch(escolha){
                case 1:
                    if(pagina < nrPaginas-1){
                        pagina++;
                        System.out.println("Página: " + (pagina+1) + " de " + nrPaginas);
                        if((pagina) == nrPaginas){ // vamos pra ultima
                            mostrarStrings(dados,20*pagina, stringsUltimaPagina);
                        }
                        else{
                            mostrarStrings(dados,20*pagina,20);
                        }
                    }
                    else{System.out.println("Nao existe mais paginas");}
                    break;
                case 2:
                    if(pagina == 0){System.out.println("Nao existe páginas para trás");}
                    else{
                        pagina--;
                        System.out.println("Página: " + (pagina +1)+ " de " + nrPaginas);
                        mostrarStrings(dados,20*pagina,20);
                    }
                    break;
                case 3:
                    System.out.println("Insira a página: ");
                    escolhaDePagina = input.nextInt();
                    if(escolhaDePagina > nrPaginas || escolhaDePagina < 1){ System.out.println("Página inválida.");break;}
                    pagina = escolhaDePagina-1;
                    
                    System.out.println("Página: " + (pagina +1)+ " de " + nrPaginas);
                    if(pagina == nrPaginas-1){
                        mostrarStrings(dados,20*pagina,stringsUltimaPagina);
                    }
                    else{
                        mostrarStrings(dados,20*pagina,20);
                    }
                    break;
            }
        }
    }
    private void mostrarStrings(ArrayList<String> strings, int posicaoInicial, int qtosMostrar){
        int i;
        for(i = (posicaoInicial); i < posicaoInicial + qtosMostrar; i++){
            System.out.println(strings.get(i));
        }
    }
    public void testarLeituraSemParse(){
        //compras normais
        try{
            System.out.println("BUFFERED READER");
            Hipermercado h1 = new Hipermercado();
            Crono.start();
            h1.lerComprasNoParseBufferedReader("../files/compras.txt");
            Crono.stop();
            System.out.println("Compras normais lidas em " + Crono.print() + " segundos.");
            //compras 1milhao
            Hipermercado h2 = new Hipermercado();
            Crono.start();
            h2.lerComprasNoParseBufferedReader("../files/compras1.txt");
            Crono.stop();
            System.out.println("Compras 1 milhao lidas em " + Crono.print() + " segundos.");
            //compras 3milhoes
            Hipermercado h3 = new Hipermercado();
            Crono.start();
            h3.lerComprasNoParseBufferedReader("../files/compras3.txt");
            Crono.stop();
            System.out.println("Compras 3 milhoes lidas em " + Crono.print() + " segundos.");
            
            System.out.println("SCANNER");
            Hipermercado h4 = new Hipermercado();
            Crono.start();
            h4.lerComprasNoParseScanner("../files/compras.txt");
            Crono.stop();
            System.out.println("Compras normais lidas em " + Crono.print() + " segundos.");
            //compras 1milhao
            Hipermercado h5 = new Hipermercado();
            Crono.start();
            h5.lerComprasNoParseScanner("../files/compras1.txt");
            Crono.stop();
            System.out.println("Compras 1 milhao lidas em " + Crono.print() + " segundos.");
            //compras 3milhoes
            Hipermercado h6 = new Hipermercado();
            Crono.start();
            h6.lerComprasNoParseScanner("../files/compras3.txt");
            Crono.stop();
            System.out.println("Compras 3 milhoes lidas em " + Crono.print() + " segundos.");
        }
        catch (FileNotFoundException e){
            
        }
        catch (IOException e){
            
        }
        catch (ProdutoNaoExisteException e){
            
        }
    }
    public void testarLeituraComParse(){
        //compras normais
        try{
            System.out.println("COM PARSING");
            System.out.println("BUFFERED READER");
            Hipermercado h1 = new Hipermercado();
            Crono.start();
            h1.lerComprasComParsingBufferedReader("../files/compras.txt");
            Crono.stop();
            System.out.println("Compras normais lidas em " + Crono.print() + " segundos.");
            //compras 1milhao
            Hipermercado h2 = new Hipermercado();
            Crono.start();
            h2.lerComprasComParsingBufferedReader("../files/compras1.txt");
            Crono.stop();
            System.out.println("Compras 1 milhao lidas em " + Crono.print() + " segundos.");
            //compras 3milhoes
            Hipermercado h3 = new Hipermercado();
            Crono.start();
            h3.lerComprasComParsingBufferedReader("../files/compras3.txt");
            Crono.stop();
            System.out.println("Compras 3 milhoes lidas em " + Crono.print() + " segundos.");
            
            System.out.println("SCANNER");
            Hipermercado h4 = new Hipermercado();
            Crono.start();
            h4.lerComprasComParsingScanner("../files/compras.txt");
            Crono.stop();
            System.out.println("Compras normais lidas em " + Crono.print() + " segundos.");
            //compras 1milhao
            Hipermercado h5 = new Hipermercado();
            Crono.start();
            h5.lerComprasComParsingScanner("../files/compras1.txt");
            Crono.stop();
            System.out.println("Compras 1 milhao lidas em " + Crono.print() + " segundos.");
            //compras 3milhoes
            Hipermercado h6 = new Hipermercado();
            Crono.start();
            h6.lerComprasComParsingScanner("../files/compras3.txt");
            Crono.stop();
            System.out.println("Compras 3 milhoes lidas em " + Crono.print() + " segundos.");
        }
        catch (FileNotFoundException e){
            
        }
        catch (IOException e){
            
        }
        catch (ProdutoNaoExisteException e){
            
        }
    }
    private void meuMain(){
        Hipermercado meuHiper = carregarDados();
        if(meuHiper == null) return;
        
        Scanner input = new Scanner(System.in);
        int escolha = -1;
        while(escolha != 0){
            System.out.println("------------Menu Principal------------");
            System.out.println("1 -> Carregar Dados");
            System.out.println("2 -> Consultas estatísticas");
            System.out.println("3 -> Consultas interativas");
            System.out.println("4 -> Gravar Hipermercado (ObjectStream)");
            System.out.println("0 -> Sair");
            escolha = input.nextInt();
            switch(escolha){
                case 1:
                    meuHiper = carregarDados();
                    break;
                case 2:
                    menuEstatisticas(meuHiper);
                    break;
                case 3:
                    menuInterativo(meuHiper);
                    break;
                case 4:
                    System.out.println("Nome do ficheiro: ");
                    String nomeFicheiro = input.next();
                    gravarObjeto(meuHiper,nomeFicheiro);
                    break;
                case 0:
                    break;    
            }
        }
        gravarObjeto(meuHiper,"hipermercado.obj");
    }
    
    public static void main (String args[]){
        HiperMercadoTestar novoHiper = new HiperMercadoTestar();
        novoHiper.meuMain();
    }
}
