import java.math.BigInteger;
import java.math.*;
public class RC5 
{
	
	private int r;
	private int t;
	private BigInteger [] S;
	private BigInteger P;
	private BigInteger Q;
	
	
	public RC5 ()
	{
		
		this.P = new BigInteger("47073"); //p(16 bits) = 0xB7E1
		this.Q = new BigInteger("40503"); //q(16 bits) = 0x9E37
		this.r = 12;
		this.t = 2*(r+1);
		this.S = new BigInteger[t];
	}
	
	 //The function makes a left shift in a circular fashion.
	 public static BigInteger rotateLeft(BigInteger numToRotate,int mylen,BigInteger howManyToRotate)
		{
			BigInteger temprot;
			BigInteger rotationlen=new BigInteger(Integer.toString(mylen));
			temprot=howManyToRotate.mod(rotationlen);//Optimize rotation
			BigInteger ret=numToRotate;
			for(BigInteger i = BigInteger.ZERO; i.compareTo(temprot)<0; i = i.add(BigInteger.valueOf(1))){
				ret= ret.shiftLeft(1);
			    if (ret.testBit(mylen)) {//do we need to put 1 on beginning?
			        ret = ret.clearBit(mylen).setBit(0);// so delete left 1 on end and put 1 on start
			    }
			}
		    return ret;
		}
	 //The function makes a right shift in a circular fashion.
	 public static BigInteger rotateRight(BigInteger numToRotate,int mylen,BigInteger howManyToRotate)
		{
			BigInteger temprot;
			BigInteger rotationlen=new BigInteger(Integer.toString(mylen));
			temprot=howManyToRotate.mod(rotationlen);//Optimize rotation
			BigInteger ret=numToRotate;
			for(BigInteger i = BigInteger.ZERO; i.compareTo(temprot)<0; i = i.add(BigInteger.valueOf(1)))
				if (ret.testBit(0)) 
			    {
			    	ret= ret.shiftRight(1);
			    	ret = ret.setBit(mylen-1);
			    }
			    else
			    	ret= ret.shiftRight(1);
			
			return ret;
		}
		    
		//Expanding the key according to rc5 algorithm.
	public void keyExp(BigInteger key)
	{
		String ke;
		int length;
		int i=0;
		int j=0;
		
		
		
		BigInteger A, B;
		BigInteger eightBit = BigInteger.valueOf(256);
		BigInteger binarykey;
		BigInteger two = new BigInteger("2");
    	

		ke=key.toString(2);//change to base 2
		length = ke.length();// the  key's length in binary
		binarykey = new BigInteger(ke,2);//change string to biginteger

		//converting secret key from byte to words 
		int u = P.bitLength()/8;//u = w/8 (w is the bits we chose for P and Q - 16 in our case)
		int c = (int) Math.ceil((length/8.0)/u); // c=b/u (b is the size of the key in bytes)
		
		
		BigInteger [] K = new BigInteger[(int) Math.ceil(length/8.0)];
		BigInteger [] L = new BigInteger[c];
		
		for(i=0;i<c;i++)
			L[i]=BigInteger.ZERO;
		i=0;
		
		
		//Divide the key the size of a byte in each cell in array k.
		while (!binarykey.equals(BigInteger.ZERO))
		{
		   K[i]=binarykey.mod(eightBit);
		   binarykey = binarykey.divide(eightBit);
		   i++;
		}
		
		for(i=(int)Math.ceil(length/8.0)-1;i>0;i--)//array starts from 0
			L[i/u] = (L[i/u].shiftLeft(8)).add(K[i]).mod(BigInteger.valueOf(2).pow(P.bitLength()));//addition in relation to mod 2^w
		
		S[0]=P;
		for(i=1;i<t;i++)
			S[i]=S[i-1].add(Q).mod(BigInteger.valueOf(2).pow(P.bitLength()));//addition in relation to mod 2^w
		
		
		//Initializes the array s and l.
		i=j=0;
		A = B = BigInteger.ZERO;
		BigInteger three=new BigInteger("3");	
		int max = Math.max(t,c);
		for(int x=0;x<3*max;x++)
		{

			A = S[i] = rotateLeft((S[i].add(A).add(B)).mod(two.pow(P.bitLength())),P.bitLength(),three);
			B = L[j] = rotateLeft((L[j].add(A).add(B)).mod(two.pow(P.bitLength())),P.bitLength(),A.add(B).mod(BigInteger.valueOf(2).pow(16))); 
			i = (i + 1) % t;
			j = (j + 1) % c;	
		}
		
		System.out.print("k= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(length/8))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(K[m.intValue()].toString(16)+" ");
		System.out.print("\nl= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(c))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(L[m.intValue()].toString(16)+" ");
		System.out.print("\ns= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(t))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(S[m.intValue()].toString(16)+" ");
		System.out.println("");
	}
	
	// function that encrypts by rc5 algorithm.
	public BigInteger[] enc(BigInteger plainText)
	{
		BigInteger[] encText = new BigInteger[2];
		BigInteger A, B;
		A = plainText.shiftRight(P.bitLength());//left 16 bits
		B = plainText.and(BigInteger.valueOf(65535));//right 16 bits
		A = A.add(S[0]).mod(BigInteger.TWO.pow(P.bitLength()));
		B = B.add(S[1]).mod(BigInteger.TWO.pow(P.bitLength()));
		for(int i = 1; i <= r;i++)
		{
			A = (rotateLeft(A.xor(B),P.bitLength(),B).add(S[2*i])).mod(BigInteger.TWO.pow(P.bitLength()));
			B = (rotateLeft(B.xor(A),P.bitLength(),A).add(S[2*i+1])).mod(BigInteger.TWO.pow(P.bitLength()));
		}
		encText[0] = A;
		encText[1] = B;
		return encText;
	}
	
	// function that decrypt by rc5 algorithm.
	public BigInteger[] dec(BigInteger encText)
	{
		BigInteger[] plainText = new BigInteger[2];
		BigInteger A, B;
		A = encText.shiftRight(P.bitLength());//left 16 bits
		B = encText.and(BigInteger.valueOf(65535));//right 16 bit
		for(int i = r; i >= 1;i--)
		{
			B = rotateRight(B.subtract(S[2*i+1]).mod(BigInteger.TWO.pow(P.bitLength())),P.bitLength(), A).xor(A);	
			A = rotateRight(A.subtract(S[2*i]).mod(BigInteger.TWO.pow(P.bitLength())),P.bitLength(), B).xor(B);
			
		}
		B=B.subtract(S[1]).mod(BigInteger.TWO.pow(P.bitLength()));
		A=A.subtract(S[0]).mod(BigInteger.TWO.pow(P.bitLength()));
		plainText[0]=A;
		plainText[1]=B;
		
		return plainText;
		
	}
}