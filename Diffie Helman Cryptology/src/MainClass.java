
public class MainClass {

	public static void main(String[] args) {
		EllipticCurve el = new EllipticCurve();
		System.out.println(el.getP());
		Person alice = new Person(el.getP());

	}

}
