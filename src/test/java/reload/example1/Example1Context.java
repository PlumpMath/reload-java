package reload.example1;

public class Example1Context {

	SayHello hello = new SayHello();

}

class SayHello {

	public SayHello() {
		System.out.println( "Hello" );
	}

}
