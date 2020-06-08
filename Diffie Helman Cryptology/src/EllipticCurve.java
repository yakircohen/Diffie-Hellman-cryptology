import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

import com.google.common.math.BigIntegerMath;


public class EllipticCurve {
       private BigInteger a;
       private BigInteger b;
       private BigInteger p;

       
       public EllipticCurve() {
		initCurve();
		
	}
       
    public void initCurve()
    {
   
    	Random randGen = new Random();
    	BigInteger two = new BigInteger("2");
    	BigInteger min = two.pow(1);
    	BigInteger max = two.pow(2);
    	BigInteger subtraction = max.subtract(min);
    	BigInteger checkSingularity,tempA,tempB;
    	do {
    		BigInteger randNum = new BigInteger(max.bitLength(), randGen);
        	if(randNum.compareTo(min) < 0)
        		p = randNum.add(min);
        	if(randNum.compareTo(subtraction) >= 0)
        		p = randNum.mod(subtraction).add(min);
 
    	}while(p.isProbablePrime(1) == false);

    	do {
    		b = new BigInteger(max.bitLength(), randGen);
    		a = new BigInteger(max.bitLength(), randGen);
        	tempA = (a.pow(3)).multiply(BigInteger.valueOf(4));//tempA = 4*a^3
        	tempB = (b.pow(2)).multiply(BigInteger.valueOf(27));//tempB = 27*b^2
        	checkSingularity = tempA.add(tempB);
        	
    	}while(checkSingularity.mod(p) == BigInteger.ZERO || a.compareTo(BigInteger.ZERO) <= 0 || b.compareTo(BigInteger.ZERO) <=0 );
    }
    	
    public BigInteger[] findGenerator(BigInteger a,BigInteger b, BigInteger p)
    {
    	BigInteger coordinateY, tempXPow,tempA,afterMod;
    	BigInteger[] generator = new BigInteger[2];//generator[0] = coordinateX , generator[1] = coordinateY
    	for(BigInteger i = BigInteger.ZERO; i.compareTo(p) <0;i = i.add(BigInteger.valueOf(1)))
    	{
    		
    		tempXPow = i.pow(3); //x^3
    		tempA = a.multiply(i); //a*x
    		coordinateY = tempXPow.add(tempA.add(b));//x^3 + a*x + b
    		BigInteger sqrtRes[] = coordinateY.sqrtAndRemainder();
    		if(sqrtRes[1].compareTo(BigInteger.ZERO) == 0 && sqrtRes[0].compareTo(BigInteger.ZERO)!=0)
    		{
    			generator[0] = i;//generator[0] = x
    			generator[1] = sqrtRes[0];//generator[1] = y;
    			break;
    		}
    		afterMod = coordinateY.mod(p);
    		sqrtRes = afterMod.sqrtAndRemainder();
    		if(sqrtRes[1].compareTo(BigInteger.ZERO) == 0 && sqrtRes[0].compareTo(BigInteger.ZERO)!=0)
    		{
    			generator[0] = i;//generator[0] = x
    			generator[1] = sqrtRes[0];//generator[1] = y;
    			break;
    		}
    	}
    	return generator;
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
