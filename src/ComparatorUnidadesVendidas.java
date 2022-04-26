import java.util.Comparator;
import java.io.Serializable;

public class ComparatorUnidadesVendidas implements Comparator<TrioProdutosUnidadesClientes>, Serializable
{
    public int compare(TrioProdutosUnidadesClientes p1, TrioProdutosUnidadesClientes p2) {
        if(p1.getUnidadesVendidas()<p2.getUnidadesVendidas()) return 1;
        else if(p1.getUnidadesVendidas()>p2.getUnidadesVendidas()) return -1;
        else return (p1.getCodigo()).compareTo(p2.getCodigo());
    }
}
