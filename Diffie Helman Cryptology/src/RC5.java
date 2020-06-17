import java.math.BigInteger;
import java.math.*;
public class RC5 {
	
	 public static void main(String[] args)
	{
		String ke;
		int length;
		int i=0;
		int j=0;
		int r=12;
		int t=2*(r+1);
		BigInteger A, B;
		BigInteger eight = BigInteger.valueOf(100000000);
		 
		BigInteger binarykey;
		BigInteger two = new BigInteger("2");
    	BigInteger key = two.pow(55);
    	System.out.println(key.bitLength());
		ke=key.toString(2);//change to base 2
		length = ke.length();// the  key's length in binary
		binarykey = new BigInteger(ke);//change string to biginteger
		
		BigInteger P = new BigInteger("47073"); //p(16 bits) = 0xB7E1
		BigInteger Q = new BigInteger("40503"); //q(16 bits) = 0x9E37
		//converting secret key from byte to words 
		int u = P.bitLength()/8;//u = w/8 (w is the bits we chose for P and Q - 16 in our case)
		int c = (length/8)/u; // c=b/u (b is the size of the key in bytes)
		
		BigInteger [] S = new BigInteger[t];
		BigInteger [] K = new BigInteger[length/8+1];
		BigInteger [] L = new BigInteger[c+1];///////
		
		for(i=0;i<c+1;i++)
			L[i]=BigInteger.ZERO;
		i=0;
		
		System.out.println("Number = " +binarykey);
		
		while (!binarykey.equals(BigInteger.ZERO))
		{
		   K[i]=binarykey.mod(eight);
		   binarykey = binarykey.divide(eight);
		   i++;
		}
		
		for(i=(int)Math.ceil(length/8.0)-1;i>0;i--)//array starts from 0
			L[i/u] = (L[i/u].shiftLeft(8)).add(K[i]);
		
		S[0]=P;
		for(i=1;i<t;i++)
			S[i]=S[i-1].add(Q);
		
		i=j=0;
		A = B = BigInteger.ZERO;
		BigInteger temp=new BigInteger("0");
		if(t>c)
			for(int x=0;x<3*t;x++)
			{
				A = S[i] = (((S[i].add(A)).add(B)).shiftLeft(3));
				temp = A.add(B);
				B = L[j] = (((L[j].add(A)).add(B)).shiftLeft(temp.intValue()));
				i = (i + 1) % t;
				j = (j + 1) % c;
				
			}
			else
				for(int x=0;x<3*c;x++)
				{
					A = S[i] = (((S[i].add(A)).add(B)).shiftLeft(3));
					temp = L[j].add(A).add(B);
					B = L[j] = ((L[j].add(A)).add(B)).shiftLeft(A.add(B).intValue());
					i = (i + 1) % t;
					j = (j + 1) % c;
					
				} 
	
		System.out.print("k= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(length/8))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(K[m.intValue()]+" ");
		System.out.print("\nl= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(c+1))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(L[m.intValue()]+" ");
		System.out.print("\ns= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(t))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(S[m.intValue()]+" ");
	//	System.out.println(A);
		//System.out.println(B);
	}
		private static BigInteger rotateLeft(BigInteger temp,int mylen,BigInteger myrot) {
		    BigInteger ret = temp.shiftLeft(1);
		    if (ret.testBit(32)) {
		        ret = ret.clearBit(32).setBit(0);
		    }
		    return ret;
		}
}
