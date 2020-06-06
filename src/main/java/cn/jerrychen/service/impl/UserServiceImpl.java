package cn.jerrychen.service.impl;

import cn.jerrychen.annotation.Autowired;
import cn.jerrychen.annotation.Service;
import cn.jerrychen.dao.UserDao;
import cn.jerrychen.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void del(int id) {
        System.out.println("UserService running……");
        userDao.del(id);
    }
}
