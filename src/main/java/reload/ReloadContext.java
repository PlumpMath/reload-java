package reload;

import static reload.util.Lambda.*;

import java.util.LinkedList;
import java.util.List;

import reload.util.AggressiveClassLoader;
import reload.util.DynamicClassBytesReader;
import reload.util.Reflect;

public class ReloadContext {

	private String clzName;
	protected Object context;
	private final F<ClassLoader> clzLoader;
	private final List<P1<Object>> afterCreate = new LinkedList<>();
	private final List<P1<Object>> beforeClose = new LinkedList<>();

	public ReloadContext( String clzName, final String... clzPaths ) {
		this.clzName = clzName;
		this.context = context;
		this.clzLoader = ( ) -> new AggressiveClassLoader( new DynamicClassBytesReader( clzPaths ) );
	}

	public void reload() {
		close();
		context = createContext();
		invokeAll( afterCreate, context );
	}

	public void close() {
		if( context != null ) {
			invokeAll( beforeClose, context );
			context = null;
		}
	}

	private Object createContext() {
		try {
			Class<?> context = clzLoader.e().loadClass( clzName );
			return Reflect.newInstance( context );
		} catch( ClassNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}

}
