package reload;

import reload.example1.Example1Context;
import reload.util.Sys;

public class ReloadExample1 {

	public static void main( String[] args ) {
		final ReloadContext rc = new ReloadContext( Example1Context.class.getName(),
				"build/classes/test" );

		Sys.onReturn( line -> {
			switch( line ) {
			case "exit":
				rc.close();
				System.exit( 0 );
				break;
			case "r":
				rc.reload();
				break;
			default:
				System.out.println( "unknown command" );
			}

		}, System.in );
	}

}
