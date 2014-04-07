package se.jayway.apptotest.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import se.jayway.apptotest.softwareupdate.RemoteSoftwareRepo;
import se.jayway.apptotest.softwareupdate.SignatureVerifier;
import se.jayway.apptotest.softwareupdate.SoftwareUpdater;
import android.net.NetworkInfo;
import android.test.AndroidTestCase;

public class Tests extends AndroidTestCase {

	public void testNoUpdateWhenRoaming() {
		RemoteSoftwareRepo repo = mock(RemoteSoftwareRepo.class);
		SignatureVerifier verifier = mock(SignatureVerifier.class);

		SoftwareUpdater.UpdateResult result = new SoftwareUpdater().update(
				createNetworkMock(true, true), // Connected and roaming.
				1, repo, verifier);

		assertEquals(SoftwareUpdater.UpdateResult.UPDATE_ABORTED_ROAMING,
				result);
		verifyZeroInteractions(repo);
		verifyZeroInteractions(verifier);
	}

	public void testNoUpdateWhenAlreadyUpToDate() {
		RemoteSoftwareRepo repo = mock(RemoteSoftwareRepo.class);
		when(repo.getLatestVersion()).thenReturn(1);

		SignatureVerifier verifier = mock(SignatureVerifier.class);

		SoftwareUpdater.UpdateResult result = new SoftwareUpdater().update(
				createNetworkMock(true, false), // Connected and not roaming.
				1, repo, verifier);

		assertEquals(SoftwareUpdater.UpdateResult.UPDATE_NOT_NEEDED, result);
		verify(repo, never()).downloadLatestVersion();
		verifyZeroInteractions(verifier);
	}

	private NetworkInfo createNetworkMock(boolean connected, boolean roaming) {
		NetworkInfo network = mock(NetworkInfo.class);
		when(network.isConnected()).thenReturn(connected);
		when(network.isRoaming()).thenReturn(roaming);
		return network;
	}
}
