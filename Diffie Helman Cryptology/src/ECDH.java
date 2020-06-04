                          import java.security.KeyPairGenerator;
 import java.security.spec.ECGenParameterSpec; /**
 * File: ECHD.java
 *
* This file simulates the Elliptic Curve
* Diffie-Hellman key exchange between two
 * ECDH users, Alice and Bob.
 *
 * @author Stephen Morse
 */
 public class ECDH {

 public static void main(String[] args) throws Exception {       

 // The first step in using ECDH in Java is to create a
 // KeyPairGenerator. This lets us make private keys and
 // public keys in ECDH. To do this, we may use the static
 // getInstance() method.
 KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC","SunEC");
 
 // Initialize the EC using "sect233r1" curve specifications.
 // This just defines a set of parameters where the key size
 // is 233 bits and the parameters are chosen (somewhat)
 // randomly (hence the r1).
 ECGenParameterSpec ecsp = new ECGenParameterSpec("sect233r1");
 kpg.initialize(ecsp);

 // We create two ECDH users, Alice and Bob. Their public keys
 // will print uponing running this code.
 ECDHUser alice = new ECDHUser("Alice", kpg);
 System.out.println(alice.getName() + "’s public key is: " +
 alice.getPubKey().toString());
 ECDHUser bob = new ECDHUser("Bob", kpg);
 System.out.println(bob.getName() + "’s public key is: " +
 bob.getPubKey().toString());

 // The users exchange a private key using ECDH protocol.
 // The values that they each receive will be printed.
 alice.sendValueTo(bob);
 bob.sendValueTo(alice);
 }

 }
