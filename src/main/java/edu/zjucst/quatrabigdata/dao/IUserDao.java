package edu.zjucst.quatrabigdata.dao;

import edu.zjucst.quatrabigdata.model.User;

public interface IUserDao extends ICommonDao<User>{
	public final static String SERVICE_NAME="edu.zju.cst.quatrabigdata.dao.impl.UserDaoImpl";
}
