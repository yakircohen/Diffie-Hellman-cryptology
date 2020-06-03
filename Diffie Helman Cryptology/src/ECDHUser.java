
import java.math.BigInteger;
		
		 import java.security.KeyPair;
		 import java.security.KeyPairGenerator;
		 import java.security.PrivateKey;
		 import java.security.PublicKey;
		
		 import javax.crypto.KeyAgreement;

		
		 /**
		 * File: ECHDUser.java
		 *
		 * This class represents a user of the Elliptic
		 * Curve Diffie-Hellman system. It has a sendValueTo
		 * method that can be used for sharing a value with
		 * another ECDH user.
		 */
		 class ECDHUser {
		 /** This ECDH implementer’s private key. */
		 private PrivateKey privKey;
		
		 /** This ECDH implementer’s public key. */
		 private PublicKey pubKey;
		
		 /** The name of this ECDH User. */
		 private String name;
		
		 /**
		 * A Constructor.
		 *
		 * @param name - The name of the user.
		 *
		 * @param kpg
		 */
		 public ECDHUser(String name, KeyPairGenerator kpg) {
		 this.name = name;
		 KeyPair kpU = kpg.genKeyPair();
		 this.privKey = kpU.getPrivate();
		 this.pubKey = kpU.getPublic();
		 }
		
		 public void sendValueTo(ECDHUser receiver) throws Exception {
		 // Get A key agreement controller for ECDH
		 KeyAgreement ecdh = KeyAgreement.getInstance("ECDH");
		
		 // Initialize it so it knows the private key (a for Alice, b for Bob)
		 ecdh.init(this.privKey);
		
		 // DoPhase actually computes the shared value A^b = B^a
		 // The second parameter lets the ECDH instance know that we are
		 // all done using this instance
		 ecdh.doPhase(receiver.getPubKey(), true);
		
		// To get the shared value, we have to user our private ecdh
		 // object to call ecdh.generateSecret(). Here we print so
		 // that we can see we have the same value.
		 System.out.println("Secret computed by " + receiver.getName() +
		 ": 0x" + (new BigInteger(1, ecdh.generateSecret()
		 ).toString(16)).toUpperCase());
		 }
		
	 /**
		 * @return the pubKey
		 */
		 public PublicKey getPubKey() {
		 return pubKey;
		 }
		
		 /**
		 * @return the name
		 */
		 public String getName() {
		 return name;
		 }
		
		 }
		
		
		
		
		
		
		
		
		
	
	
	


