import java.math.BigInteger;
import java.util.Random;

public class Person {
	private BigInteger privateKey;
	private BigInteger pubKeyX;
	private BigInteger pubKeyY;
	
	public Person(){
		Random randGen = new Random();
		this.privateKey  = new BigInteger(256, randGen);
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
