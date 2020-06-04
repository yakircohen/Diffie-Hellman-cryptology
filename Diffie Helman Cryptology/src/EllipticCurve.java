import java.math.BigInteger;
import java.util.Random;


public class EllipticCurve {
       private BigInteger a;
       private BigInteger b;
       private BigInteger p;
       
       public EllipticCurve() {
		initCurve();
	}
       
    public BigInteger[] initCurve()
    {
    	BigInteger[] curveParams = new BigInteger[3];//curveParams[0] = a, curveParams[1] = b, curveParams[2] = p
    	Random randGen = new Random();
    	BigInteger two = new BigInteger("2");
    	BigInteger min = two.pow(255);
    	BigInteger max = two.pow(256);
    	BigInteger subtraction = max.subtract(min);
    	BigInteger checkSingularity,tempA,tempB;

    	BigInteger randNum = new BigInteger(max.bitLength(), randGen);
    	if(randNum.compareTo(min) < 0)
    		curveParams[2] = randNum.add(min);
    	if(randNum.compareTo(subtraction) >= 0)
    		curveParams[2] = randNum.mod(subtraction).add(min);
    	do {
    		curveParams[1] = new BigInteger(max.bitLength(), randGen);
    		curveParams[0] = new BigInteger(max.bitLength(), randGen);
        	tempA = (curveParams[0].pow(3)).multiply(BigInteger.valueOf(4));
        	tempB = (curveParams[1].pow(2)).multiply(BigInteger.valueOf(27));
        	checkSingularity = tempA.add(tempB);
    	}while(checkSingularity.mod(curveParams[2]) == BigInteger.ZERO);
    	
    	return curveParams;
    }


	public BigInteger getA() {
		return a;
	}

	public void setA(BigInteger a) {
		this.a = a;
	}

	public BigInteger getB() {
		return b;
	}

	public void setB(BigInteger b) {
		this.b = b;
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}
     
       
       
       
       
}
