package reload.util;

import java.util.Collection;

public interface Lambda {

	interface F<T> {
		T e();
	}

	interface F1<A, T> {
		T e( A a );
	}

	interface P {
		void e();
	}

	interface P1<A> {
		void e( A a );
	}

	static void invokeAll( Collection<P1<Object>> procs, Object param ) {
		procs.stream().forEach( proc -> proc.e( param ) );
	}

}
