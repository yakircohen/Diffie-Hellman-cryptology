import java.math.BigInteger;

public class MainClass {

	public static void main(String[] args) {
		EllipticCurve el = new EllipticCurve();
		BigInteger[] generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		Person alice = new Person(el.getP(),generator);

	}

}
