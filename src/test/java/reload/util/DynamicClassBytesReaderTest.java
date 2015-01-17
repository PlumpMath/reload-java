package reload.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
}
