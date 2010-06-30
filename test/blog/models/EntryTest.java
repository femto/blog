package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;
import com.scooterframework.orm.activerecord.ActiveRecordUtil;
import com.scooterframework.test.ApplicationTest;

/**
 * EntryTest class contains tests for entry.
 *
 */
public class EntryTest extends ApplicationTest {

	protected ActiveRecord entryHome = null;
	
	protected void setUp() {
		super.setUp();
		if (entryHome == null) entryHome = ActiveRecordUtil.getHomeInstance(Entry.class);
	}
	
	/**
	 * This is a sample test method.
	 * 
	 * Tests record retrieval by findBy method.
	 */
	//public void test_findByLastNameAndFirstName() {
	//	String[] names = {"Stevens", "Henry"};
	//	ActiveRecord vet5 = vetHome.findBy("last_name_and_first_name", names);
	//	assertEquals("#5 Stevens's id", "5", ""+vet5.getField("id"));
	//}
}