package reload.util;

public class AClass {
	/** used by ReloadContextTest */

	public boolean beforeCalled = false;
	public int beforeCalledCount = 0;
	public boolean afterCalled = false;
	public int afterCalledCount = 0;

	public void before( String val ) {
		beforeCalled = true;
		beforeCalledCount++;
	}

	public void after( String val ) {
		afterCalled = true;
		afterCalledCount++;
	}
}
