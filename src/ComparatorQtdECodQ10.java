import java.util.Comparator;
import java.io.Serializable;

public class ComparatorQtdECodQ10 implements Comparator<TrioCodigoQtdValor>, Serializable
{
    public int compare(TrioCodigoQtdValor p1, TrioCodigoQtdValor p2) {
        if(p1.getQuantidade()<p2.getQuantidade()) return 1;
        else if(p1.getQuantidade()>p2.getQuantidade()) return -1;
        else return (p1.getCodigo()).compareTo(p2.getCodigo());
    }
}
