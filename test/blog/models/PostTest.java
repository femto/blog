package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;
import com.scooterframework.orm.activerecord.ActiveRecordUtil;
import com.scooterframework.test.ApplicationTest;

/**
 * PostTest class contains tests for post.
 *
 */
public class PostTest extends ApplicationTest {

	protected ActiveRecord postHome = null;
	
	protected void setUp() {
		super.setUp();
		if (postHome == null) postHome = ActiveRecordUtil.getHomeInstance(Post.class);
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