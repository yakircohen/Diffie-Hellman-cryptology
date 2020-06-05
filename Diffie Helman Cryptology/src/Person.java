
import java.math.BigInteger;
import java.util.Random;
import com.google.common.math.BigIntegerMath;




public class Person {
	private BigInteger privateKey;
	private BigInteger pubKeyX;
	private BigInteger pubKeyY;
	
	public Person(BigInteger p){                                                                                  
		BigInteger y = BigInteger.valueOf((long)(Math.log(p.longValue()) / Math.log(2)));
		int x=y.intValue();
		this.privateKey= new BigInteger(x-1, new Random()); 
		 
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
                                                        