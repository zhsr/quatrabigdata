package edu.zjucst.quatrabigdata.dao.impl;

import org.springframework.stereotype.Repository;

import edu.zjucst.quatrabigdata.dao.IUserDao;
import edu.zjucst.quatrabigdata.model.User;
@Repository(IUserDao.SERVICE_NAME)
public class UserDaoImpl extends CommonDaoImpl<User> implements IUserDao{
}
