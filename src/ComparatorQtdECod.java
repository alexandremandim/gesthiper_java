import java.util.Comparator;
import java.io.Serializable;

public class ComparatorQtdECod implements Comparator<ParCodigoValor>, Serializable
{
    public int compare(ParCodigoValor p1, ParCodigoValor p2) {
        if(p1.getValor()<p2.getValor()) return 1;
        else if(p1.getValor()>p2.getValor()) return -1;
        else return (p1.getCodigo()).compareTo(p2.getCodigo());
    }
}
