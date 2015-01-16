package reload.util;

import static reload.util.Lambda.F1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class DynamicClassBytesReader implements ClassBytesReader {

	private final List<F1<String, byte[]>> loaders = new ArrayList<>();

	public DynamicClassBytesReader( String... paths ) {
		for( String path : paths ) {
			F1<String, byte[]> loader = fileLoader( new File( path ) );
			if( loader == null ) {
				throw new RuntimeException( "Path does not exist " + path );
			}
			loaders.add( loader );
		}
	}

	@Override
	public byte[] readBytes( String name ) {
		byte[] data = null;
		for( F1<String, byte[]> loader : loaders ) {
			data = loader.e( toFilePath( name ) );
			if( data != null ) {
				break;
			}
		}
		return data;
	}

	private String toFilePath( String name ) {
		return name.replaceAll( "\\.", "/" ) + ".class";
	}

	private static File findFile( String child, File parent ) {
		File file = new File( parent, child );
		return file.exists() ? file : null;
	}

	private static F1<String, byte[]> dirLoader( final File dir ) {
		return filePath -> {
			File file = findFile( filePath, dir );
			return file == null ? null : IO.read( file );
		};
	}

	private static F1<String, byte[]> jarLoader( final JarFile jar ) {
		return new F1<String, byte[]>() {
			@Override
			public byte[] e( String path ) {
				byte[] bytes = null;
				ZipEntry ze = jar.getJarEntry( path );
				if( ze != null ) {
					try {
						bytes = IO.read( jar.getInputStream( ze ) );
					} catch( IOException e ) {
						throw new RuntimeException( e );
					}
				}
				return bytes;
			}

			@Override
			protected void finalize() throws Throwable {
				IO.close( jar );
				super.finalize();
			}
		};
	}

	private static F1<String, byte[]> fileLoader( File file ) {
		F1<String, byte[]> loader;
		if( !file.exists() ) {
			loader = null;
		} else if( file.isDirectory() ) {
			loader = dirLoader( file );
		} else {
			try {
				loader = jarLoader( new JarFile( file ) );
			} catch( IOException e ) {
				throw new RuntimeException( e );
			}
		}
		return loader;
	}
}
