import java.math.BigInteger;


public class MainClass {

	public static void main(String[] args) {
		//alice wants to send message to bob "alice wants to kill you bob" 
		//generate public elliptic curve
		EllipticCurve el = new EllipticCurve();
		//generate public generator
		BigInteger[] generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		while(generator[0]==null||generator[1]==null)
		{
			el.initCurve();
			generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		}
		System.out.println("public "
				+ "generator:["+generator[0]+","+generator[1]+"]");
		
		//Alice and Bob each generate their own Public key and Private key 
		System.out.println("Alice:");
		Person alice = new Person(el.getP(),generator,el.getA(),el.getB());
		System.out.println("Bob:");
		Person bob = new Person(el.getP(),generator,el.getA(),el.getB());
		BigInteger a1=alice.getPubKeyX();
		BigInteger a2=alice.getPubKeyY();
		
		alice.sharedKeyGen(bob.getPubKeyX(),bob.getPubKeyY());
		bob.sharedKeyGen(a1,a2);
	}

}
