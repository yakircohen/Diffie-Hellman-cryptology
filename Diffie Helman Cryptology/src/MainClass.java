import java.math.BigInteger;


public class MainClass {

	public static void main(String[] args) {
		BigInteger five = new BigInteger("10");
		BigInteger[] res = five.divideAndRemainder(new BigInteger("8"));
		 
		EllipticCurve el = new EllipticCurve();
		BigInteger[] generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		Person alice = new Person(el.getP(),generator,el.getA());
			
	}

}
