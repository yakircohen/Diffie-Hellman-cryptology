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
		BigInteger eightBit = BigInteger.valueOf(100000000);
		 
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
		   K[i]=binarykey.mod(eightBit);
		   binarykey = binarykey.divide(eightBit);
		   i++;
		}
		
		for(i=(int)Math.ceil(length/8.0)-1;i>0;i--)//array starts from 0
			L[i/u] = (L[i/u].shiftLeft(8)).add(K[i]).mod(BigInteger.valueOf(2).pow(16));
		
		S[0]=P;
		for(i=1;i<t;i++)
			S[i]=S[i-1].add(Q).mod(BigInteger.valueOf(2).pow(16));
		
		i=j=0;
		A = B = BigInteger.ZERO;
		BigInteger temp=new BigInteger("0");
		BigInteger temp3=new BigInteger("3");	

		if(t>c)
			for(int x=0;x<3*t;x++)
			{

				A = S[i] = rotateLeft(twoComplAddition3(S[i],A,B,16),16,temp3);
				temp = A.add(B).mod(BigInteger.valueOf(2).pow(16));
				B = L[j] = rotateLeft(twoComplAddition3(L[j],A,B,16),16,temp); 
				i = (i + 1) % t;
				j = (j + 1) % c;
				
			}
			else
				for(int x=0;x<3*c;x++)
				{
					A = S[i] = rotateLeft(twoComplAddition3(S[i],A,B,16),16,temp3);
					temp = A.add(B).mod(BigInteger.valueOf(2).pow(16));
					B = L[j] = rotateLeft(twoComplAddition3(L[j],A,B,16),16,temp); 
					i = (i + 1) % t;
					j = (j + 1) % c;
					
				} 
	
		System.out.print("k= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(length/8))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(K[m.intValue()].toString(16)+" ");
		System.out.print("\nl= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(c+1))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(L[m.intValue()].toString(16)+" ");
		System.out.print("\ns= ");
		for(BigInteger m = BigInteger.ZERO; m.compareTo(BigInteger.valueOf(t))<0; m = m.add(BigInteger.valueOf(1)))
			System.out.print(S[m.intValue()].toString(16)+" ");
	//	System.out.println(A);
		//System.out.println(B);
	}
	 
		public static BigInteger rotateLeft(BigInteger numToRotate,int mylen,BigInteger howManyToRotate) {
		//	System.out.println("temp= "+temp.toString()+", mylen="+mylen+", myrot="+myrot.toString());
			BigInteger temprot;
			BigInteger rotationlen=new BigInteger(Integer.toString(mylen));
			temprot=howManyToRotate.mod(rotationlen);//Optimize rotation
			BigInteger ret=numToRotate;
			for(BigInteger i = BigInteger.ZERO; i.compareTo(temprot)<0; i = i.add(BigInteger.valueOf(1))){
				ret= ret.shiftLeft(1);
			    if (ret.testBit(mylen)) {//do we need to put 1 on beginning?
			        ret = ret.clearBit(mylen).setBit(0);// so del left 1 on end and put 1 on start
			    }
			}
		    return ret;
		}
		
		public static BigInteger twoComplAddition3(BigInteger num1,BigInteger num2,BigInteger num3,int relevantbits) {
			BigInteger two = new BigInteger("2");
			BigInteger relevantbitsB=new BigInteger(Integer.toString(relevantbits));
			BigInteger toret=new BigInteger("0");
			toret=toret.add(num1);
			toret=toret.add(num2);
			toret=toret.add(num3);
			
			toret=toret.mod(two.pow(relevantbits));
			return toret;
		}
}
