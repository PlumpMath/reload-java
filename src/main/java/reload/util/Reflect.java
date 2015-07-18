package reload.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Reflect {

	private Reflect() {
	}

	@SuppressWarnings("unchecked")
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

	public static void setField( Object value, String field, Object obj ) {
		try {
			setFieldValue( value, obj.getClass().getDeclaredField( field ), obj );
		} catch( NoSuchFieldException e ) {
			throw new RuntimeException( e );
		}

	}

	private static void setFieldValue( Object value, Field field, Object obj ) {
		try {
			field.set( obj, value );
		} catch( IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
	}

	public static <A> A getFieldValue( String field, Object obj ) {
		Field fieldFound = getField( field, obj.getClass() );
		if( fieldFound == null ) {
			throw new RuntimeException( new NoSuchFieldException( field ) );
		}
		return getFieldValue( getField( field, obj.getClass() ), obj );
	}

	@SuppressWarnings("unchecked")
	private static <A> A getFieldValue( Field field, Object obj ) {
		try {
			return (A) field.get( obj );
		} catch( IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
	}

	private static Field getField( String name, Class<?> clazz ) {
		try {
			return clazz.getDeclaredField( name );
		} catch( NoSuchFieldException e ) {
			Class<?> superClass = clazz.getSuperclass();
			if( Object.class.equals( superClass ) ) {
				return null;
			} else {
				return getField( name, superClass );
			}
		}
	}

	public static Object invoke( String method, Object o, Object... params ) {
		Method methodFound = getMethod( method, o.getClass() );
		if( methodFound == null ) {
			throw new RuntimeException( new NoSuchMethodException( method ) );
		}
		return invoke( methodFound, o, params );
	}

	@SuppressWarnings("unchecked")
	private static <T> T invoke( Method method, Object o, Object... params ) {

		try {
			return (T) method.invoke( o, params );
		} catch( IllegalAccessException e ) {
			throw new RuntimeException( e );
		} catch( InvocationTargetException e ) {
			throw new RuntimeException( e.getCause() );
		}
	}

	private static Method getMethod( String methodName, Class clazz ) {
		for( Method method : clazz.getMethods() ) {
			if( method.getName().equals( methodName ) ) {
				return method;
			}
		}
		if( !clazz.equals( Object.class ) ) {
			Class superclass = clazz.getSuperclass();
			if( superclass != null ) {
				return getMethod( methodName, superclass );
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
