package cn.jerrychen.dao.impl;

import cn.jerrychen.annotation.Repository;
import cn.jerrychen.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void del(int id) {
        System.out.println("UserDao running……");
    }
}
