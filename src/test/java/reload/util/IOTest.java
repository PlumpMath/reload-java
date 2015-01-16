package reload.util;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class IOTest {

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
