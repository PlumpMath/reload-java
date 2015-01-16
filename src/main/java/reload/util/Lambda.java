package reload.util;

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

}
