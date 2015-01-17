package reload.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class IOTest {

	@Test
	public void constructorIsPrivate() throws Exception {

		Constructor<IO> constructor = IO.class.getDeclaredConstructor();
		assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
		constructor.setAccessible( true );
		constructor.newInstance();

	}

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

	@Test
	public void readInputStream() throws Exception {

		InputStream is = new ByteArrayInputStream( "data_read".getBytes() );

		byte[] read = IO.read( is );

		assertEquals( "bytes read", "data_read", new String( read ) );

	}

	@Test
	public void readInputStreamCloseThrowsIOException() throws Exception {

		InputStream is = mock( InputStream.class );

		doReturn( -1 ).when( is ).read( any(), anyInt(), anyInt() );
		doThrow( new IOException( "ERR" ) ).when( is ).close();

		try {
			IO.read( is );
			fail( "Read should fail if close throws IOException" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.io.IOException: ERR", e.getMessage() );
		}

	}

	@Test
	public void readInputStreamThrowsIOException() throws Exception {

		InputStream is = mock( InputStream.class );
		doThrow( new IOException( "ERR" ) ).when( is ).read( any(), anyInt(), anyInt() );

		try {
			IO.read( is );
			fail( "Read should fail if IOException is thrown" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.io.IOException: ERR", e.getMessage() );
		}

	}

	@Test
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
