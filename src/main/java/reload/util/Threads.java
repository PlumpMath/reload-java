package reload.util;

import static reload.util.Lambda.P;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Threads {

	private static final ExecutorService executorService = Executors.newCachedThreadPool();

	private Threads() {
	}

	public static void runStrong( final P proc ) {
		executorService.execute( proc::e );
	}

}
