package se.jayway.apptotest.softwareupdate;

import java.io.File;

import android.net.NetworkInfo;
import android.os.SystemClock;

public class SoftwareUpdater {
	
	public enum UpdateResult {
		// The software is already up to date.
		UPDATE_NOT_NEEDED,		
		
		// The software was successfully updated.
		UPDATE_COMPLETED,
		
		// The signature of the available update was bad.
		UPDATE_ABORTED_INVALID_SIGNATURE,
		
		// The device is roaming on the current network.
		UPDATE_ABORTED_ROAMING,
		
		// There was no network connection.
		UPDATE_ABORTED_NO_NETWORK,
		
		// There was an unspecified error during update.
		UPDATE_ABORTED_ERROR
	}

	/**
	 * Updates the software given that there is a valid update available and
	 * that the required network conditions are met.
	 * 
	 * @param network
	 *            The current network info.
	 * @param currentVersion
	 *            The currently installed software version.
	 * @param repo
	 *            Keeps new software versions.
	 * @param verifier
	 *            Verifies the signature of downloaded software.
	 * @return A result code indicating the result of the update request. See
	 *         documentation about the UpdateResult enum for details about the
	 *         possible return values.
	 */
	public UpdateResult update(NetworkInfo network,
			int currentVersion, RemoteSoftwareRepo repo,
			SignatureVerifier verifier) {
		try {
			if (network == null || !network.isConnected()) {
				return UpdateResult.UPDATE_ABORTED_NO_NETWORK;
			}

			if (network.isRoaming()) {
				return UpdateResult.UPDATE_ABORTED_ROAMING;
			}

			if (currentVersion >= repo.getLatestVersion()) {
				return UpdateResult.UPDATE_NOT_NEEDED;
			}

			File file = repo.downloadLatestVersion();

			if (!verifier.verifySignature(file)) {
				return UpdateResult.UPDATE_ABORTED_INVALID_SIGNATURE;
			}
			
			boolean successfulInstall = install(file);

			return successfulInstall ? UpdateResult.UPDATE_COMPLETED
					: UpdateResult.UPDATE_ABORTED_ERROR;
		} catch (Exception e) {
			return UpdateResult.UPDATE_ABORTED_ERROR;
		}
	}
	
	/**
	 * Installs a software update.
	 * 
	 * @param update
	 *            A handle to the update file.
	 * @return TRUE if the installation was successful, FALSE otherwise.
	 */
	private boolean install(File update) {
		// Fake installation flow.
		SystemClock.sleep(15*1000);
		return true;
	}
}
