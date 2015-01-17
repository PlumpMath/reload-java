package reload.util;

public final class Reflect {

	private Reflect() {
	}

	public static <A> A newInstance( Class<A> cla ) {
		return (A) newInstance4( cla );
	}

	private static Object newInstance4( Class clazz ) {

		try {
			return clazz.newInstance();
		} catch( InstantiationException e ) {
			Throwable cause = e.getCause();
			if( cause == null ) {
				cause = e;
			}
			throw new RuntimeException( cause );
		} catch( IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
	}
}
