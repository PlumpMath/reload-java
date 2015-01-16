package reload.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;

import org.junit.Test;

public class IOTest {

	@Test
	public void readFileNotFound() {

		try {
			IO.read( new File( "file_does_not_exist" ) );
			fail( "Read should fail if file does not exist" );
		} catch( RuntimeException e ) {
			assertEquals( "Error", "java.io.FileNotFoundException: "
					+ "file_does_not_exist (No such file or directory)", e.getMessage() );
		}

	}

	@Test
	public void readFile() throws IOException {

		byte[] data = IO.read( new File( getClass().getResource(
				getClass().getSimpleName() + ".class" ).getFile() ) );

		assertTrue( "no data read from file", data.length > 0 );

	}

	public void closeNull() throws Exception {

		IO.close( null );

	}

	@Test
	public void closeFlushable() throws Exception {

		OutputStream out = mock( OutputStream.class );

		IO.close( out );

		verify( out ).flush();
		verify( out ).close();

	}

	@Test
	public void closeNoneFlushable() throws Exception {

		InputStream in = mock( InputStream.class );

		IO.close( in );

		verify( in ).close();

	}

	@Test
	public void closeIgnoreIOException() throws Exception {

		InputStream in = mock( InputStream.class );

		doThrow( new IOException( "ignore it" ) ).when( in ).close();

		IO.close( in );

	}

}
