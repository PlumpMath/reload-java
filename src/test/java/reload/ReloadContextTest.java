package reload;

import static org.junit.Assert.*;

import org.junit.Test;

import reload.util.AClass;

public class ReloadContextTest {

	@Test
	public void reloadClassNotFoundInClasspath() {

		ReloadContext rc = new ReloadContext("my.package.NotInClass", "build/classes/test");

		try {
			rc.reload();
			fail("Reload should fail if class is not in classpath");
		} catch (RuntimeException e) {
			assertEquals("Error message", "java.lang.ClassNotFoundException: my.package.NotInClass",
					e.getMessage());
		}

	}

	@Test
	public void reloadClass() {

		ReloadContext rc = new ReloadContext(AClass.class.getName(), "build/classes/test");

		rc.reload();

		assertNotNull("Context is null", rc.context);

	}

	@Test
	public void reloadClassReplaceContext() {

		ReloadContext rc = new ReloadContext(AClass.class.getName(), "build/classes/test");

		rc.reload();
		Object oldContext = rc.context;
		rc.reload();

		assertNotSame("Context not replaced", oldContext, rc.context);

	}

	@Test
	public void closeIsSafe() {

		ReloadContext rc = new ReloadContext(AClass.class.getName(), "build/classes/test");

		rc.close();

	}

	@Test
	public void closeClearsContext() {

		ReloadContext rc = new ReloadContext(AClass.class.getName(), "build/classes/test");

		rc.reload();
		rc.close();

		assertNull("Context should be cleared", rc.context);

	}
}
