package edu.zjucst.quatrabigdata.service;

import edu.zjucst.quatrabigdata.model.User;

public interface IUserService {
	public final static String SERVICE_NAME = "edu.zju.cst.quatrabigdata.service.UserServiceImpl";

	void add(User user);

}
