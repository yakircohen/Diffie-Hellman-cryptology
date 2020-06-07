import java.math.BigInteger;
import java.math.BigDecimal;

public class MainClass {

	public static void main(String[] args) {
		 
		EllipticCurve el = new EllipticCurve();
		BigInteger[] generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		Person alice = new Person(el.getP(),generator,el.getA());

	}

}
