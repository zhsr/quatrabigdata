package edu.zjucst.quatrabigdata.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.zjucst.quatrabigdata.dao.IUserDao;
import edu.zjucst.quatrabigdata.model.User;
import edu.zjucst.quatrabigdata.service.IUserService;

@SuppressWarnings("restriction")
@Transactional(readOnly = true)
@Service(IUserService.SERVICE_NAME)
public class UserServiceImpl implements IUserService {

	@Resource(name = IUserDao.SERVICE_NAME)
	private IUserDao userDao;

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void add(User user) {
		userDao.save(user);
	}

}
