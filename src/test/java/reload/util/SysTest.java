package reload.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class SysTest {

	@Test
	public void constructorIsPrivate() throws Exception {

		Constructor<Sys> constructor = Sys.class.getDeclaredConstructor();
		assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
		constructor.setAccessible( true );
		constructor.newInstance();

	}

	@Test
	public void onReturn() throws Exception {

		String line = "LINE1";
		final String[] linesRead = new String[] { null };

		Sys.onReturn( l -> {
			linesRead[0] = l;
			throw new RuntimeException( "End test" );
		}, new ByteArrayInputStream( line.getBytes() ) );

		Thread.sleep( 3 ); // give the thread some time

		assertEquals( "Line read", line, linesRead[0] );

	}

}
