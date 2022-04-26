import java.io.Serializable;
public class ParModoFat implements Serializable
{
    private double fatN,fatP;
    private int xsCompradoN, xsCompradoP;

    /* construtores */
    public ParModoFat(){
        this.fatN = 0;
        this.fatP = 0;
        this.xsCompradoN = 0;
        this.xsCompradoP =  0;
    }
    public ParModoFat(double fatN,double fatP, int xsCompradoN, int xsCompradoP)
    {
        this.fatN = fatN;
        this.fatP =  fatP;
        this.xsCompradoN = xsCompradoN;
        this.xsCompradoP =  xsCompradoP;
    }
    public ParModoFat(ParModoFat pmf){
        this.fatN = pmf.getFatN();
        this.fatP = pmf.getFatP();
        this.xsCompradoN = pmf.getXsCompraN();
        this.xsCompradoP =  pmf.getXsCompraP();
    }
    
    //gets e sets//
    public int getXsCompraN(){return this.xsCompradoN;}
    public int getXsCompraP(){return this.xsCompradoP;}
    public double getFatN(){return this.fatN;}
    public double getFatP(){return this.fatP;}
    
    public void setXsCompraN(int xsCompradoN){ this.xsCompradoN += xsCompradoN;}
    public void setXsCompraP(int xsCompradoP){this.xsCompradoP += xsCompradoP;}
    public void setFatN(double fatN){ this.fatN += fatN;}
    public void setFatP(double fatP){this.fatP += fatP;}
    //clone, toString e equals
    
    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())){
            return false;
        }
        ParModoFat pmf = (ParModoFat)o;
        if(this.fatN != pmf.getFatN()) return false;
        if(this.fatP != pmf.getFatP()) return false;
        if(this.xsCompradoN != pmf.getXsCompraN()) return false;
        if(this.xsCompradoP != pmf.getXsCompraP()) return false;
        return true;
    }
    
    public ParModoFat clone(){
        return new ParModoFat(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Faturacao em modo N: " + fatN);
        sb.append("\nFaturação em modo P: " + fatP);
        sb.append("\nVezes comprado em modo N: " + xsCompradoN);
        sb.append("\nVezes comprado em modo P: " + xsCompradoP);
        return sb.toString();
    }
}
