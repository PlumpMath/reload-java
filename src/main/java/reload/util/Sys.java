package reload.util;

import static reload.util.Lambda.P1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sys {

	private static BufferedReader br;

	public static String readLine() {
		if( br == null ) {
			br = new BufferedReader( new InputStreamReader( System.in ) );
		}
		try {
			return br.readLine();
		} catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public static void onReturn( final P1<String> proc ) {
		Threads.runStrong( ( ) -> {
			while( true ) {
				try {
					proc.e( readLine() );
				} catch( Exception e ) {
					return;
				}
			}
		} );
	}

}
