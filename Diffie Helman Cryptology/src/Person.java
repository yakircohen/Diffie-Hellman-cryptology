
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;
import com.google.common.math.BigIntegerMath;
import java.math.BigDecimal;



public class Person {
	private BigInteger privateKey;
	private BigDecimal pubKeyX;
	private BigDecimal pubKeyY;
	private BigDecimal a;

	public Person(BigInteger p,BigInteger[] generator,BigInteger a){ 
		this.a = new BigDecimal(a);
		Random randPrivKey = new Random();
		do {
			this.privateKey= new BigInteger(p.bitLength(), randPrivKey); 
		}while(p.compareTo(privateKey) <= 0);
		
		BigDecimal[] pubKey = new BigDecimal[2];
		pubKey=findPubKey(this.privateKey,generator,this.a);
		this.pubKeyX=pubKey[0];
		this.pubKeyY=pubKey[1];
		System.out.println(pubKeyX);
		System.out.println(pubKeyY);
	}
	
	public BigDecimal[] findPubKey(BigInteger privateKey,BigInteger[] generator,BigDecimal a)
	{
		BigDecimal up,down,m;
		BigDecimal[] x3y3 = new BigDecimal[2];
		BigInteger[] pubKey = new BigInteger[2];
		BigDecimal[] temp = new BigDecimal[2];
		pubKey[0]=generator[0].add(BigInteger.ZERO);
		pubKey[1]=generator[1].add(BigInteger.ZERO);
		BigDecimal x = new BigDecimal(pubKey[0]);//x2
		BigDecimal y = new BigDecimal(pubKey[1]);//y2
		temp[0] = x.add(BigDecimal.ZERO);//x1
		temp[1] = y.add(BigDecimal.ZERO);//y1
		for(BigInteger i = BigInteger.ZERO; i.compareTo(privateKey) <0;i = i.add(BigInteger.valueOf(1)))
		{
			if(x.compareTo(temp[0]) != 0)
			{
				 up = y.subtract(temp[1]); // y2-y1
				 down = x.subtract(temp[0]);// x2-x1 
				 m = up.divide(down, 50, RoundingMode.CEILING);// up/down
				 x3y3[0] = m.pow(2);//m*2
				 x3y3[0] = x3y3[0].subtract(temp[0]);
				 x3y3[0] = x3y3[0].subtract(x);
				 x3y3[1] = temp[0].subtract(x3y3[0]);//x1 – x3
				 x3y3[1] = m.multiply(x3y3[1]);//m(x1 – x3) 
				 x3y3[1] = x3y3[1].subtract(temp[1]);//m(x1 – x3) - y1
				 temp[0] = x3y3[0].add(BigDecimal.ZERO);//x3
				 temp[1] = x3y3[1].add(BigDecimal.ZERO);//y3
			}
			
			if(x.compareTo(temp[0]) == 0 && y.compareTo(temp[1]) == 0)
			{
				up=(temp[0].pow(2)).multiply(BigDecimal.valueOf(3));//3*x1^2
				up=up.add(a);//3*x1^2 + a
				down = temp[1].multiply(BigDecimal.valueOf(3)); 
				m = up.divide(down, 50, RoundingMode.CEILING);// up/down
				x3y3[0] = m.pow(2);//m*2
				 x3y3[0] = x3y3[0].subtract(temp[0]);
				 x3y3[0] = x3y3[0].subtract(x);
				 x3y3[1] = temp[0].subtract(x3y3[0]);//x1 – x3
				 x3y3[1] = m.multiply(x3y3[1]);//m(x1 – x3) 
				 x3y3[1] = x3y3[1].subtract(temp[1]);//m(x1 – x3) - y1
				 temp[0] = x3y3[0].add(BigDecimal.ZERO);//x3
				 temp[1] = x3y3[1].add(BigDecimal.ZERO);//y3		
			}
			
		}
		
		return temp;
	}
	
	
	public BigDecimal getPubKeyX() {
		return pubKeyX;
	}
	public void setPubKeyX(BigDecimal pubKeyX) {
		this.pubKeyX = pubKeyX;
	}
	public BigDecimal getPubKeyY() {
		return pubKeyY;
	}
	public void setPubKeyY(BigDecimal pubKeyY) {
		this.pubKeyY = pubKeyY;
	} 

}
                                                        