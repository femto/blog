package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;
import com.scooterframework.orm.activerecord.ActiveRecordUtil;
import com.scooterframework.test.ApplicationTest;

/**
 * CommentTest class contains tests for comment.
 *
 */
public class CommentTest extends ApplicationTest {

	protected ActiveRecord commentHome = null;
	
	protected void setUp() {
		super.setUp();
		if (commentHome == null) commentHome = ActiveRecordUtil.getHomeInstance(Comment.class);
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