package reload.util;

import java.util.Collection;

public interface Lambda {

	static interface F<T> {
		T e();
	}

	static interface F1<A, T> {
		T e( A a );
	}

	static interface P {
		void e();
	}

	static interface P1<A> {
		void e( A a );
	}

	public static void invokeAll( Collection<P1<Object>> procs, Object parm ) {
		procs.stream().forEach( proc -> proc.e( parm ) );
	}

}
