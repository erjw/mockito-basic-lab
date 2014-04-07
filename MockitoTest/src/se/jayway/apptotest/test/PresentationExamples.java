package se.jayway.apptotest.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;

import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.TooLittleActualInvocations;

import android.content.Context;
import android.test.AndroidTestCase;

public class PresentationExamples extends AndroidTestCase {
	@SuppressWarnings("deprecation")
	public void testPresentationExamples() {
		{
			Context ctx = mock(Context.class);
			assertNull(ctx.getResources());
			assertFalse(ctx.deleteFile(anyString()));
			assertEquals(0, ctx.getWallpaperDesiredMinimumHeight());
		}

		{
			Context ctx = mock(Context.class);
			when(ctx.getPackageName()).thenReturn("com.jayway.test");
			assertEquals("com.jayway.test", ctx.getPackageName());
		}

		{
			Context ctx = mock(Context.class);

			ctx.getResources();

			verify(ctx).getResources();

			verify(ctx, never()).getAssets();

			try {
				verify(ctx, times(2)).getResources();
				fail();
			} catch (TooLittleActualInvocations e) {
				//Expected.
			} catch (Exception e) {
				fail();
			}
		}
		
		{
			Context ctx = mock(Context.class);
			
			verifyZeroInteractions(ctx);
			
			ctx.getResources();
			
			try {
				verifyZeroInteractions(ctx);
			} catch(NoInteractionsWanted e) {
				// Expected.
			} catch (Exception e) {
				fail();
			}
		}
		
		{
			Context ctx = mock(Context.class);
			
			ctx.getResources();
			ctx.getCacheDir();
			ctx.getAssets();
			
			InOrder io = Mockito.inOrder(ctx);
			io.verify(ctx).getResources();
			io.verify(ctx).getAssets();
		}
		
		{
			Context ctx = mock(Context.class);
			
			try {
				when(ctx.openFileInput(anyString())).thenThrow(
						new RuntimeException());
			} catch (FileNotFoundException e) {
				fail();
			}			
			
			try {
				ctx.openFileInput("somefile");
			} catch(RuntimeException e) {
				// Expected
			} catch(Exception e) {
				fail();
			}
		}
	}
}
