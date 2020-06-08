
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;
import com.google.common.math.BigIntegerMath;




public class Person {
	private BigInteger privateKey;
	private BigInteger pubKeyX;
	private BigInteger pubKeyY;
	private BigInteger a;
	private BigInteger p;

	public Person(BigInteger p,BigInteger[] generator,BigInteger a){ 
		this.a = a;
		this.p = p;
		
		Random randPrivKey = new Random();
		do {
			this.privateKey= new BigInteger(p.bitLength(), randPrivKey); 
		}while(p.compareTo(privateKey) <= 0 || privateKey.compareTo(BigInteger.ONE) < 1);
		
		BigInteger[] pubKey = new BigInteger[2];
		pubKey=findPubKey(this.privateKey,generator,this.a,this.p);
		System.out.println(pubKey[0]);
		System.out.println(pubKey[1]);
	}
	
	public BigInteger[] findPubKey(BigInteger privateKey,BigInteger[] generator,BigInteger a,BigInteger p)
	{
		BigInteger m,numerator = null,denom = null,inverse = null,x3,y3;
		
		BigInteger[] pubKey = new BigInteger[2];
		pubKey[0]=generator[0];//x1
		pubKey[1]=generator[1];//y1

		
		for(BigInteger i = BigInteger.ONE; i.compareTo(privateKey) < 0; i = i.add(BigInteger.valueOf(1)))
		{
			
			if(generator[0].compareTo(pubKey[0]) != 0)
			{
				numerator=generator[1].subtract(pubKey[1]);//y2-y1
				denom=generator[0].subtract(pubKey[0]);//x2-x1
				numerator=numerator.mod(p);
				denom=denom.mod(p);
	
			}
			
			else if(generator[0].compareTo(pubKey[0]) == 0 && generator[1].compareTo(pubKey[1]) == 0)
			{
				//m = (3*x1^2+a)/2*y1
				m=(pubKey[0].pow(2)).multiply(BigInteger.valueOf(3));// m=3*x1^2
				numerator= m.add(a);//m=3*x1^2+a
				denom=pubKey[1].multiply(BigInteger.valueOf(2));//2*y1
	
			}
			inverse=denom.modInverse(p);
			m=numerator.multiply(inverse);
			x3=m.pow(2);//m^2
			x3=(x3.subtract(pubKey[0])).subtract(generator[0]);//x3 = m^2 - x1 - x2 
			x3 =x3.mod(p);//x3 = x3 mod(p)
			y3 = pubKey[0].subtract(x3);//y3 = x1-x3
			y3= m.multiply(y3);//y3 = m*(x1-x3)
			y3 = y3.subtract(pubKey[1]);
			y3 = y3.mod(p);//y3 = (m*(x1-x3)-y1)mod p
			pubKey[0]=x3;//x3
			pubKey[1]=y3;//y3
		}
		
		return pubKey;
	}
	
	
	public BigInteger getPubKeyX() {
		return pubKeyX;
	}
	public void setPubKeyX(BigInteger pubKeyX) {
		this.pubKeyX = pubKeyX;
	}
	public BigInteger getPubKeyY() {
		return pubKeyY;
	}
	public void setPubKeyY(BigInteger pubKeyY) {
		this.pubKeyY = pubKeyY;
	} 

}
                                                        