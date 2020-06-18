
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
	private BigInteger b;
	private BigInteger sharedKey[];

	public Person(BigInteger p,BigInteger[] generator,BigInteger a,BigInteger b){ 
		this.a = a;
		this.p = p;
		this.b = b;
		Random randPrivKey = new Random();
		do {
			this.privateKey= new BigInteger(p.bitLength(), randPrivKey); 
		}while(p.compareTo(privateKey) <= 0 || privateKey.compareTo(BigInteger.ONE) < 1);
		BigInteger[] pubKey = new BigInteger[2];
		pubKey=findPubKey(this.privateKey,generator);
		System.out.print("pubkeyX: "+pubKey[0]);
		System.out.println("; pubkeyY: "+pubKey[1]);
		this.pubKeyX = pubKey[0];
		this.pubKeyY = pubKey[1];
	}
	
	public BigInteger[] findPubKey(BigInteger privateKey,BigInteger[] generator)
	{
		BigInteger m,numerator = null,denom = null,inverse = null,x3,y3;
		
		BigInteger[] pubKey = new BigInteger[2];
		pubKey[0]=generator[0];//x1
		pubKey[1]=generator[1];//y1
		//System.out.println("privateKey: "+privateKey);
		
		for(BigInteger i = BigInteger.ONE; i.compareTo(privateKey) < 0; i = i.add(BigInteger.valueOf(1)))
		{
			
			int flag = 1;
			if(pubKey[0].compareTo(BigInteger.ZERO)==0) {
				pubKey[0]=generator[0];
				pubKey[1]=generator[1];
				flag=0;
				
			}else if(generator[0].compareTo(pubKey[0]) != 0)
			{
				numerator=generator[1].subtract(pubKey[1]);//y2-y1
				denom=generator[0].subtract(pubKey[0]);//x2-x1
				numerator=numerator.mod(p);
				denom=denom.mod(p);
	
			}
			
			else if(generator[0].compareTo(pubKey[0]) == 0 && generator[1].compareTo(pubKey[1]) == 0)
			{
				//m = (3*x1^2+a)/2*y1
				m=(pubKey[0].pow(2)).multiply(BigInteger.valueOf(3)).mod(p);// m=3*x1^2
				numerator= m.add(a).mod(p);//m=3*x1^2+a
				denom=pubKey[1].multiply(BigInteger.valueOf(2)).mod(p);//2*y1
	
			}
			else {
				//pubKey[0]=pubKey[0] ;
				pubKey[1] = BigInteger.ZERO;
				flag = 0;
			}
			if(flag == 1)
			{
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
			
		}
		
		return pubKey;
	}
	
	public  void sharedKeyGen(BigInteger x, BigInteger y)
	{
		BigInteger[] generateKey = new BigInteger[2];
		generateKey[0] = x;
		generateKey[1] = y;
		sharedKey = findPubKey(this.privateKey, generateKey);
		System.out.println(sharedKey[0] + "," + sharedKey[1]);
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
                                                        