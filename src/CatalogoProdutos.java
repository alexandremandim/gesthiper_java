import java.util.TreeSet;
import java.util.Set;
import java.io.Serializable;
public class CatalogoProdutos implements Serializable
{
    private Set<String> produtos;

    public CatalogoProdutos()
    {
        this.produtos = new TreeSet<String>();
    }
    public CatalogoProdutos(CatalogoProdutos c)
    {
        this.produtos = c.getProdutos();
    }
    
    // gets e set
    public Set<String> getProdutos(){
        Set<String> novo = new TreeSet<String>();
        for(String s: this.produtos){
            novo.add(s);
        }
        return novo;
    }
    public void addProdutos(String produto){
        this.produtos.add(produto);
    }
    public int totalProdutos(){//1.1
        return this.produtos.size();
    }
    public boolean contains(String codigoProduto){
        return this.produtos.contains(codigoProduto);
    }
    // equals toString clone
    public CatalogoProdutos clone(){
        return new CatalogoProdutos(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        CatalogoProdutos catProd = (CatalogoProdutos)o;
        
        if(this.totalProdutos() != catProd.totalProdutos()){ return false;}
        for(String s: this.getProdutos()){
            if(!(catProd.contains(s))) return false;}
        return true;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Este catálogo de produtos tem:\n");
        sb.append(this.totalProdutos() + " produtos.\n");
        return sb.toString();
    }
}

