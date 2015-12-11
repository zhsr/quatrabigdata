package edu.zjucst.quatrabigdata.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.User;
import edu.zjucst.quatrabigdata.service.IUserService;

@SuppressWarnings("serial")
@Namespace("/user")
@Results({
	@Result(name = "saveuser", location = "/index.jsp", type = "dispatcher")
})
public class UserAction extends BaseAction{
	IUserService userservice=(IUserService) ServiceProvider.getService(IUserService.SERVICE_NAME);
	@Action("save")
	public String saveUser(){
		User user=new User("123","123");
		userservice.add(user);
		return "saveuser";
		
	}


}
