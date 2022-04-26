package HipermercadoExceptions;

public class ClienteNaoExisteException extends Exception
{
    public ClienteNaoExisteException(){ super();}
    public ClienteNaoExisteException(String msg){ super(msg); }
}
