package se.jayway.apptotest.softwareupdate;

import java.io.File;

/**
 * A repository that keeps software updates. 
 */
public interface RemoteSoftwareRepo {

	/**
	 * Gets the version code of the latest available version.
	 * 
	 * @return The version code of the latest available version.
	 */
	int getLatestVersion();
	
	/**
	 * Downloads the latest available version.
	 * 
	 * @return A file handle to the downloaded update.
	 */
	File downloadLatestVersion();
}
