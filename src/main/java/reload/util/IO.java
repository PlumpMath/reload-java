package reload.util;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class IO {

	private IO() {
	}

	public static byte[] read( File file ) {
		try (InputStream fis = new FileInputStream( file )) {
			return read( fis );
		} catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public static byte[] read( InputStream is ) {
		try (BufferedInputStream bis = new BufferedInputStream( is )) {
			return readAll( bis );
		} catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	private static byte[] readAll( BufferedInputStream bis ) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int c;
			byte[] buff = new byte[8192];
			while( (c = bis.read( buff, 0, buff.length )) > -1 ) {
				baos.write( buff, 0, c );
			}
		} catch( IOException e ) {
			throw new RuntimeException( e );
		}
		return baos.toByteArray();
	}

	public static void close( Closeable closeable ) {
		if( closeable != null ) {
			try {
				if( closeable instanceof Flushable ) {
					((Flushable) closeable).flush();
				}
				closeable.close();
			} catch( IOException e ) {
				Logger.getAnonymousLogger().log( Level.FINE, "Ignore closing stream " + closeable, e );
			}
		}
	}

}
