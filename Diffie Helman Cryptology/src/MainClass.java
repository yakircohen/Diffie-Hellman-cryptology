import java.math.BigInteger;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		//Alice wants to send message to Bob "Alice wants to kill you bob" 
		//generate public elliptic curve
		EllipticCurve el = new EllipticCurve();
		//generate public generator
		BigInteger[] generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		//If the function findGenerator does not find a point then run again for other values
		while(generator[0]==null||generator[1]==null)
		{
			el.initCurve();
			generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		}
		System.out.println("public "
				+ "generator:["+generator[0]+","+generator[1]+"]");
		
		//Alice and Bob each generate their own Public key and Private key 
		System.out.println("Alice:");
		Person alice = new Person(el.getP(),generator,el.getA());
		System.out.println("Bob:");
		Person bob = new Person(el.getP(),generator,el.getA());
		
		//generate Private shared keys
		alice.sharedKeyGen(bob.getPubKeyX(),bob.getPubKeyY());
		bob.sharedKeyGen(alice.getPubKeyX(),alice.getPubKeyY());
		
		//Establish RC5
		alice.GenerateRC5();
		bob.GenerateRC5();
		System.out.println("");
		System.out.println("start:");
		Scanner s = new Scanner(System.in);
	    System.out.print("Enter text = ");
	    String toencmsg =s.nextLine();
		System.out.println("to enc(Alice):"+toencmsg);
		String encryted=alice.encRC5(toencmsg);
		System.out.println("to Dec(Bob):"+encryted);
		String Decr=bob.decRC5(encryted);
		System.out.println("encrypted (Bob):"+Decr);
				
		
		
	}

}