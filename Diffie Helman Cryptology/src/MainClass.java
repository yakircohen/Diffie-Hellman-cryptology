import java.math.BigInteger;


public class MainClass {

	public static void main(String[] args) {

		
		EllipticCurve el = new EllipticCurve();
		BigInteger[] generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		while(generator[0]==null||generator[1]==null)
		{
			el.initCurve();
			generator = el.findGenerator(el.getA(), el.getB(), el.getP());
		}
		
		Person alice = new Person(el.getP(),generator,el.getA(),el.getB());
			
	}

}
