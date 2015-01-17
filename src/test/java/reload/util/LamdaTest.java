package reload.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collections;

import org.junit.Test;

public class LamdaTest {

	@Test
	public void invokeAll() {

		final int[] counter = new int[] { 0 };
		Lambda.P1<Object> proc = ( number ) -> {
			counter[0]++;
		};

		Lambda.invokeAll( Collections.nCopies( 10, proc ), "" );

		assertEquals( "invoke count", 10, counter[0] );

	}

}
