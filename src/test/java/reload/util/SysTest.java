package reload.util;

import static org.junit.Assert.assertTrue;

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

}
