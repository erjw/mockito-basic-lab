package se.jayway.apptotest.softwareupdate;

import java.io.File;

/**
 * Used to verify the integrity of signed files. 
 */
public interface SignatureVerifier {
	
	/**
	 * Verifies the signature of the supplied file.
	 * 
	 * @param file
	 *            A handle to the file that is to be verified.
	 * @return TRUE if the signature is OK, FALSE otherwise.
	 */
	boolean verifySignature(File file);
}
