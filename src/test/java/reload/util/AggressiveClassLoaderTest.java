package reload.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class AggressiveClassLoaderTest {

	private ClassBytesReader reader;
	private AggressiveClassLoader classLoader;

	@Before
	public void setUp() {

		reader = mock( ClassBytesReader.class );
		classLoader = new AggressiveClassLoader( reader );

	}

	@Test
	public void loadClassFromParent() throws Exception {

		assertNotNull( "Class<?> loaded was null", classLoader.loadClass( "java.lang.Integer" ) );

		assertTrue( "class was loaded by " + classLoader.getClass().getSimpleName(),
				classLoader.unavaiClz.contains( "java.lang.Integer" ) );

	}

	@Test
	public void testLoadClass() throws Exception {

		doReturn( IO.read( getClass().getResourceAsStream( getClass().getSimpleName() + ".class" ) ) )
				.when( reader ).readBytes( getClass().getName() );

		assertNotNull( "Class<?> loaded was null", classLoader.loadClass( getClass().getName() ) );
		assertTrue( "class not loaded by " + classLoader.getClass().getSimpleName(),
				classLoader.clzLoaded.contains( getClass().getName() ) );

	}

}
