package reload.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicClassBytesReaderTest {

	@Test
	public void readBytesNoPathsSpecified() {

		DynamicClassBytesReader reader = new DynamicClassBytesReader();

		assertNull( "no bytes expected if reader has no paths",
				reader.readBytes( getClass().getName() ) );

	}

	@Test
	public void readBytes() {

		DynamicClassBytesReader reader = new DynamicClassBytesReader( "build/classes/test" );

		assertTrue( "No class bytes read", 0 < reader.readBytes( getClass().getName() ).length );

	}

	@Test
	public void constructInvalidClassPath() {

		try {
			new DynamicClassBytesReader( "NOT_A_PATH" );
			fail( "Should fail if class path is invalid" );
		} catch( Exception e ) {
			assertEquals( "Error message", "Path does not exist NOT_A_PATH", e.getMessage() );
		}

	}
}
