package HipermercadoExceptions;
public class ProdutoNaoExisteException extends Exception
{
    public ProdutoNaoExisteException(){ super();}
    public ProdutoNaoExisteException(String msg){ super(msg); }
}