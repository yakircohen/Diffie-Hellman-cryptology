import java.math.BigInteger;

public class RC5 {
	
	public BigInteger keyExp(BigInteger key)
	{
		BigInteger res = null;
		BigInteger P = new BigInteger("47073"); //p(16 bits) = 0xB7E1
		BigInteger Q = new BigInteger("40503"); //q(16 bits) = 0x9E37
		//converting secret key from byte to words 
		int u = P.bitLength()/8;//u = w/8 (w is the bits we chose for P and Q - 16 in our case)
		int c = (key.bitCount()/8)/u; // c=b/u (b is the size of the key in bytes)
		return res;
	}

}
