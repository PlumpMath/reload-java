package reload.util;

import java.util.HashSet;
import java.util.Set;

public class AggressiveClassLoader extends ClassLoader {

	protected final Set<String> clzLoaded = new HashSet<>();
	protected final Set<String> unavaiClz = new HashSet<>();

	private final ClassLoader parent = AggressiveClassLoader.class.getClassLoader();

	private final ClassBytesReader classReader;

	public AggressiveClassLoader( ClassBytesReader classReader ) {
		this.classReader = classReader;
	}

	@Override
	public Class<?> loadClass( String name ) throws ClassNotFoundException {
		Class<?> clz;
		if( clzLoaded.contains( name ) || unavaiClz.contains( name ) ) {
			clz = super.loadClass( name );
		} else {
			clz = loadClassBytes( classReader.readBytes( name ), name );
		}
		return clz;
	}

	private Class<?> loadClassBytes( byte[] bytes, String name ) throws ClassNotFoundException {
		Class<?> clz;
		if( bytes == null ) {
			unavaiClz.add( name );
			clz = parent.loadClass( name );
		} else {
			clzLoaded.add( name );
			clz = defineClass( bytes, name );
		}
		return clz;
	}

	private Class<?> defineClass( byte[] bytes, String name ) {
		Class<?> clz = defineClass( name, bytes, 0, bytes.length );
		if( clz != null ) {
			if( clz.getPackage() == null ) {
				definePackage( packageName( name ), null, null, null, null, null, null, null );
			}
			resolveClass( clz );
		}
		return clz;
	}

	private String packageName( String name ) {
		return name.replaceAll( "\\.\\w+$", "" );
	}

}
