package reload.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ThreadsTest {

	@Test
	public void constructorIsPrivate() throws Exception {

		Constructor<Threads> constructor = Threads.class.getDeclaredConstructor();
		assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
		constructor.setAccessible( true );
		constructor.newInstance();

	}

	@Test
	public void runStrongCallProcedure() throws Exception {

		final List<Integer> counter = new ArrayList<>();

		Lambda.P proc = ( ) -> {
			counter.add( 1 );
		};

		for( int i = 0; i < 10; i++ ) {
			Threads.runStrong( proc );
		}

		assertFalse( "Procedure not called", counter.isEmpty() );

	}
}
