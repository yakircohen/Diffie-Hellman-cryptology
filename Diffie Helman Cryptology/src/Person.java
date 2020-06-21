
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
	private BigInteger sharedKey[];
	private RC5 rc5;
	
	
	
	public Person(BigInteger p,BigInteger[] generator,BigInteger a)
	{ 
		this.a = a;
		this.p = p;
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
	
	
	private BigInteger[] findPubKey(BigInteger timetomul,BigInteger[] p1) {
		BigInteger[]  res=new BigInteger[2];
		BigInteger[]  splittotwo=new BigInteger[2];
		BigInteger[]  splittotwoAdded=new BigInteger[2];
		res[0] =p1[0];
		res[1] =p1[1];
		if(timetomul.compareTo(BigInteger.ZERO)==0) {
			res[1]=null;
			return res;
		}
		if(timetomul.compareTo(BigInteger.ONE)==0) {
			return p1;
		}
		splittotwo=findPubKey(timetomul.divide(BigInteger.TWO),p1);
		splittotwoAdded=pointAddition(splittotwo,splittotwo);
		if(timetomul.mod(BigInteger.TWO).compareTo(BigInteger.ONE)==0) {
			res=pointAddition(splittotwoAdded,p1);
		}
		return res;
	}
	
	
	private BigInteger[] pointAddition(BigInteger[] p1,BigInteger[] p2) {
		BigInteger[] res=new BigInteger[2];
		res[0] =p1[0];
		res[1] =p1[1];
		BigInteger m,numerator = null,denom = null,inverse = null,x3,y3;
				
		if(p1[1]==null)
			return p2;
		if(p2[1]==null)
			return p1;
		if((p1[0].compareTo(p2[0])==0 ) && p1[1].compareTo(p2[1]) != 0)//reverse?
		{
			res[1]=null;
			return res;
		}
		
		if((p1[0].compareTo(p2[0])!=0 )) {//x1 != x2
			numerator=p1[1].subtract(p2[1]);//y2-y1
			denom=p1[0].subtract(p2[0]);//x2-x1
			numerator=numerator.mod(p);
			denom=denom.mod(p);
		}
		else {
			//m = (3*x1^2+a)/2*y1
			m=(p2[0].pow(2)).multiply(BigInteger.valueOf(3)).mod(p);// m=3*x1^2
			numerator= m.add(a).mod(p);//m=3*x1^2+a
			denom=p2[1].multiply(BigInteger.valueOf(2)).mod(p);//2*y1

		}
		inverse=denom.modInverse(p);
		m=numerator.multiply(inverse);
		x3=m.pow(2);//m^2
		x3=(x3.subtract(p2[0])).subtract(p1[0]);//x3 = m^2 - x1 - x2 
		x3 =x3.mod(p);//x3 = x3 mod(p)
		y3 = p2[0].subtract(x3);//y3 = x1-x3
		y3= m.multiply(y3);//y3 = m*(x1-x3)
		y3 = y3.subtract(p2[1]);
		y3 = y3.mod(p);//y3 = (m*(x1-x3)-y1)mod p
		res[0]=x3;//x3
		res[1]=y3;//y3
		
		return res;
	}
	
	
	
	public  void sharedKeyGen(BigInteger x, BigInteger y)
	{
		BigInteger[] generateKey = new BigInteger[2];
		generateKey[0] = x;
		generateKey[1] = y;
		sharedKey = findPubKey(this.privateKey, generateKey);
		//System.out.println(sharedKey[0] + "," + sharedKey[1]);
		if(sharedKey[0]==null)
			sharedKey[0]=BigInteger.ZERO;
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

	
	
	public void GenerateRC5() {
		 rc5 = new RC5();
		 String sharedK=String.format("%16s%16s", sharedKey[0].toString(2),sharedKey[1].toString(2)).replace(" ", "0");
		 rc5.keyExp(new BigInteger(sharedK,2));
	}
	
	
	public String encRC5(String toenc) {
		String toenc32=transformString32(toenc);
		System.out.println("PreEnc:"+toenc32);
		StringBuffer sb=new StringBuffer();
		BigInteger[] maarah =new BigInteger[2];
		
		for(int i=0;i<toenc32.length();i=i+8) {

			String target=toenc32.substring(i,i+8);

			BigInteger toenc4inhex=new BigInteger(target,16);
			maarah=rc5.enc(toenc4inhex);
			toenc4inhex=maarah[0].multiply(BigInteger.valueOf(65536)).add(maarah[1]);

			sb.append(String.format("%8s",toenc4inhex.toString(16)).replace(" ", "0"));

			
		}
		String res = sb.toString();
		return res;
	}
	
	public String decRC5(String todec) {
		
		StringBuffer sb=new StringBuffer();
		BigInteger[] maarah =new BigInteger[2];
		for(int i=0;i<todec.length();i=i+8) {
			String target=todec.substring(i,i+8);

			BigInteger todec4inhex=new BigInteger(target,16);
			maarah=rc5.dec(todec4inhex);
			todec4inhex=maarah[0].multiply(BigInteger.valueOf(65536)).add(maarah[1]);

			sb.append(String.format("%8s",todec4inhex.toString(16)).replace(" ", "0"));
			
			
		}
		
		
		String res = sb.toString();
		System.out.println("AfterDec Pre resassemble:"+res);
		res=untransformString32(res);
		return res;
	}

	
	
	
	private String transformString32(String totrans) {
		StringBuffer sb=new StringBuffer();;
		char ch[] = totrans.toCharArray();
	    for(int i = 0; i < ch.length; i++) {
	       String hexString = Integer.toHexString(ch[i]);
	       sb.append(hexString);
	    }
	    String beforesend = sb.toString();
		
		String testedTextSend=hexstuffingWithPad(beforesend,32/4);
		 return testedTextSend;
	}
	
	private String untransformString32(String tountrans) {
		//reverse
		String testedTextRec=revethexstuffingWithPad(tountrans);
	      String result = new String();
	      char[] charArray = testedTextRec.toCharArray();
	      for(int i = 0; i < charArray.length; i=i+2) {
	         String st = ""+charArray[i]+""+charArray[i+1];
 
	         result = result + (char)Integer.parseInt(st, 16);
	      }	
	      return result;
	}
	
	
	public static String hexstuffingWithPad(String tostuff,int moddd) {
		String ret=new String();
		
		tostuff = 'f' + tostuff + 'f'; 
        for (int i = 0; i < tostuff.length(); i++) { 

            // Stuff with 'E' if 'F' is found in the data to be sent 
            if (tostuff.charAt(i) == 'f' && i != 0 && i != (tostuff.length() - 1)) 
            	ret = ret + 'e' + tostuff.charAt(i); 

            // Stuff with 'E' if 'E' is found in the data to be sent 
            else if (tostuff.charAt(i) == 'e') 
            	ret = ret + 'e' + tostuff.charAt(i); 
            else
            	ret = ret + tostuff.charAt(i); 
        } 
        int beforpadlen=ret.length();
        for(int i=beforpadlen;i<moddd-(beforpadlen%moddd) +beforpadlen;i++) {
        	ret=ret+'f';
        }


		return ret;
	}
	
	
	
	public static String revethexstuffingWithPad(String tounstuff) {
		String ret=new String();
		

        for (int i = 1; i < tounstuff.length() - 1; i++) { 
        	
        	if(tounstuff.charAt(i)=='f')
        		break;
           // If data contains a 'D' or 'F' do not unstuff it 
            if (tounstuff.charAt(i) != 'f' && tounstuff.charAt(i) != 'e') 
            	ret = ret + tounstuff.charAt(i); 

            // If data contains 'E' followed by 'E', de-stuff the former 'E' 
            else if (tounstuff.charAt(i) == 'e' && tounstuff.charAt(i + 1) == 'e') { 
            	ret = ret + 'e'; 
                i++; //extra jump
            }
            //if it is E followd by F de-stuff  'E' 
            else if (tounstuff.charAt(i) == 'e' && tounstuff.charAt(i + 1) == 'f') { 
            	ret = ret + 'f'; 
                i++; //extra jump
            }
          
              
        } 


		return ret;
	}

}
                                                        