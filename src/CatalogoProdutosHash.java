import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.io.Serializable;
public class CatalogoProdutosHash implements Serializable
{
    private Set<String> produtos;

    public CatalogoProdutosHash()
    {
        this.produtos = new HashSet<String>();
    }
    public CatalogoProdutosHash(CatalogoProdutosHash c)
    {
        this.produtos = c.getProdutos();
    }
    
    // gets e set
    public Set<String> getProdutos(){
        Set<String> novo = new HashSet<String>();
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
    
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.getProdutos()});
    }
    
    // equals toString clone
    public CatalogoProdutosHash clone(){
        return new CatalogoProdutosHash(this);
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        CatalogoProdutosHash catProd = (CatalogoProdutosHash)o;
        
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


