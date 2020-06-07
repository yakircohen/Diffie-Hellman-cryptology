
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
		}while(p.compareTo(privateKey) <= 0);
		
		BigInteger[] pubKey = new BigInteger[2];
		pubKey=findPubKey(this.privateKey,generator,this.a,this.p);
		this.pubKeyX=pubKey[0];
		this.pubKeyY=pubKey[1];
		System.out.println(pubKeyX);
		System.out.println(pubKeyY);
	}
	
	public BigInteger[] findPubKey(BigInteger privateKey,BigInteger[] generator,BigInteger a,BigInteger p)
	{
		BigInteger numerator,denom;
		
		BigInteger[] pubKey = new BigInteger[2];
		pubKey[0]=generator[0];//x3
		pubKey[1]=generator[1];//y3
		
		
		for(BigInteger i = BigInteger.ZERO; i.compareTo(privateKey) < 0; i = i.add(BigInteger.valueOf(1)))
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
				
				
				
				
				
			}
			
		}
		
		return pubKey;
	}
	static BigInteger modInverse(BigInteger a,BigInteger  m) 
    { 
        a = a.mod(m); 
        
        for (BigInteger x = BigInteger.ONE; x.compareTo(m) < 0; x = x.add(BigInteger.valueOf(1))) 
           if (a.multiply(x).mod(m).compareTo(BigInteger.ONE)== 0) 
              return x; 
        return BigInteger.ONE; 
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
                                                        