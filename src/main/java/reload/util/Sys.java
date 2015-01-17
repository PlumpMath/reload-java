package reload.util;

import static reload.util.Lambda.P1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Sys {

	private Sys() {
	}

	public static String readLine( BufferedReader br ) {
		try {
			return br.readLine();
		} catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public static void onReturn( final P1<String> proc, InputStream is ) {

		final BufferedReader br = new BufferedReader( new InputStreamReader( is ) );

		Threads.runStrong( ( ) -> {
			while( true ) {
				try {
					proc.e( readLine( br ) );
				} catch( Exception e ) {
					return;
				}
			}
		} );
	}

}
