import java.math.BigInteger;

public class RC5 {
	
	 public static void main(String[] args)
	{
		String ke;
		int length;
		int i=0;
		int r=12;
		int t=2*(r+1);
		BigInteger eight = BigInteger.valueOf(100000000);
		 
		BigInteger binarykey;
		BigInteger two = new BigInteger("3");
    	BigInteger key = two.pow(56);
    	
		ke=key.toString(2);//change to base 2
		length = ke.length();// the  key's length in binary
		binarykey = new BigInteger(ke);//change string to biginteger
		
		BigInteger P = new BigInteger("47073"); //p(16 bits) = 0xB7E1
		BigInteger Q = new BigInteger("40503"); //q(16 bits) = 0x9E37
		//converting secret key from byte to words 
		int u = P.bitLength()/8;//u = w/8 (w is the bits we chose for P and Q - 16 in our case)
		int c = (length/8)/u; // c=b/u (b is the size of the key in bytes)
		
		BigInteger [] s = new BigInteger[t];//
		BigInteger [] k = new BigInteger[length/8+1];
		BigInteger [] l = new BigInteger[c];
		
		for(i=0;i<c;i++)
			l[i]=BigInteger.ZERO;
		i=0;
		
		System.out.println("Number = " +binarykey);
		
		while (!binarykey.equals(BigInteger.ZERO))
		{
		   k[i]=binarykey.mod(eight);
		   binarykey = binarykey.divide(eight);
		   i++;
		}
		
		for(i=length/8-2;i>0;i--)
			l[i/u] = (l[u/i].shiftLeft(8)).add(k[i]);
		
		
		System.out.println("k= ");
		for(BigInteger j = BigInteger.ZERO; j.compareTo(BigInteger.valueOf(length/8+1))<0; j = j.add(BigInteger.valueOf(1)))
		System.out.println(k[j.intValue()]);
		System.out.println("\nl= ");
		for(BigInteger j = BigInteger.ZERO; j.compareTo(BigInteger.valueOf(c))<0; j = j.add(BigInteger.valueOf(1)))
			System.out.println(l[j.intValue()]+"  ");
	}

}
