package quatrabigdata;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.User;
import edu.zjucst.quatrabigdata.service.IUserService;
public class TestUserService {
	private IUserService userService = (IUserService) ServiceProvider.getService(IUserService.SERVICE_NAME);

//	@Test
//	public void testUserAdd() {
//		User user = new User("test", "test");
//		userService.add(user);
//	}
}
