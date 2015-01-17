package reload.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class ReflectTest {

	@Test
	public void constructorIsPrivate() throws Exception {

		Constructor<Reflect> constructor = Reflect.class.getDeclaredConstructor();
		assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
		constructor.setAccessible( true );
		constructor.newInstance();

	}

	@Test
	public void newInstance() {

		assertNotNull( "Class loaded was null", Reflect.newInstance( GodClass.class ) );

	}

	@Test
	public void newIntanceFails() {

		try {
			Reflect.newInstance( BadClass.class );
			fail( "failure expected" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "I'm bad", e.getMessage() );
		}

	}

	@Test
	public void newInstancePrivateConstructor() {

		try {
			Reflect.newInstance( PrivateClass.class );
			fail( "failure expected" );
		} catch( RuntimeException e ) {
			assertTrue( "Error prefix,",
					e.getMessage().startsWith( "java.lang.IllegalAccessException:" ) );
		}

	}
}

class GodClass {

}

class BadClass {
	BadClass() {
		throw new IllegalArgumentException( "I'm bad" );
	}
}

class PrivateClass {
	private PrivateClass() {
	}
}
