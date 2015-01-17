package reload.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class ReflectTest {

	@Test
	public void constructorIsPrivate() throws Exception {

		Constructor<Reflect> constructor = Reflect.class.getDeclaredConstructor();
		assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
		constructor.setAccessible( true );
		constructor.newInstance();

	}

	@Test
	public void newInstance() {

		assertNotNull( "Class loaded was null", Reflect.newInstance( GodClass.class ) );

	}

	@Test
	public void newIntanceFails() {

		try {
			Reflect.newInstance( BadClass.class );
			fail( "failure expected" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "I'm bad", e.getMessage() );
		}

	}

	@Test
	public void newInstancePrivateConstructor() {

		try {
			Reflect.newInstance( PrivateClass.class );
			fail( "failure expected" );
		} catch( RuntimeException e ) {
			assertTrue( "Error prefix,",
					e.getMessage().startsWith( "java.lang.IllegalAccessException:" ) );
		}

	}

	@Test
	public void setFieldUnknownField() {

		ClassExample ce = new ClassExample();

		try {
			Reflect.setField( "VALUE", "unknownField", ce );
			fail( "Should fail to set unknownField" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.lang.NoSuchFieldException: unknownField",
					e.getMessage() );
		}

	}

	@Test
	public void setField() {

		ClassExample ce = new ClassExample();

		Reflect.setField( "VALUE", "myfield", ce );

		assertEquals( "myfield value", "VALUE", ce.myfield );

	}

	@Test
	public void setFieldPrivate() {

		ClassExample ce = new ClassExample();

		try {
			Reflect.setField( "VALUE", "privateField", ce );
			fail( "Should fail to set privateField field" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.lang.IllegalAccessException: "
					+ "Class reload.util.Reflect can not access a member of "
					+ "class reload.util.ClassExample with modifiers \"private\"", e.getMessage() );
		}

	}

	@Test
	public void getFieldValueUknown() {

		ClassExample ce = new ClassExample();

		try {
			Reflect.getFieldValue( "unknownField", ce );
			fail( "Should fail to get unknownField field" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.lang.NoSuchFieldException: unknownField",
					e.getMessage() );
		}

	}

	@Test
	public void getFieldValueSuper() {

		ChildClass ce = new ChildClass();
		ce.myfield = "myvalue";

		assertEquals( "fieldValue", "myvalue", Reflect.getFieldValue( "myfield", ce ) );

	}

	@Test
	public void invokeUnknownMethod() {

		ClassExample ce = new ClassExample();

		try {
			Reflect.invoke( "unknownMethod", ce, "HELP" );
			fail( "Should fail to invoke unknown Method" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.lang.NoSuchMethodException: unknownMethod",
					e.getMessage() );
		}

	}

	@Test
	public void invokePrivateMethod() {

		ClassExample ce = new ClassExample();

		try {
			Reflect.invoke( "sayPrivate", ce, "HELP" );
			fail( "Should fail to invoke private Method" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.lang.NoSuchMethodException: sayPrivate",
					e.getMessage() );
		}

	}

	@Test
	public void invokeMethod() {

		ClassExample ce = new ClassExample();

		assertEquals( "Result from invoke 'say'", "hello", Reflect.invoke( "say", ce, "hello" ) );

	}

	@Test
	public void invokeMethodThowsException() {

		ClassExample ce = new ClassExample();

		try {
			Reflect.invoke( "sayThrowException", ce, "HELP" );
			fail( "Should fail to invoke method throwing exception" );
		} catch( RuntimeException e ) {
			assertEquals( "Error message", "java.lang.IllegalArgumentException: HELP", e.getMessage() );
		}
	}

}

class GodClass {

}

class BadClass {
	BadClass() {
		throw new IllegalArgumentException( "I'm bad" );
	}
}

class PrivateClass {
	private PrivateClass() {
	}
}

class ChildClass extends ClassExample {
}

class ClassExample {
	String myfield;
	private String privateField;

	public String say( String msg ) {
		return sayPrivate( msg );
	}

	public String sayThrowException( String msg ) {
		throw new IllegalArgumentException( msg );
	}

	private String sayPrivate( String msg ) {
		return msg;
	}
}
