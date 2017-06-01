package com.yihaomen.mybatis.inter;

import com.yihaomen.mybatis.model.Article;
import com.yihaomen.mybatis.model.User;

import java.util.List;

/**
 * Created by pro on 17-6-1.
 */
public interface IUserOperation {
    public User selectUserByID(int id);

    public List selectUsers(String userName);

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(int id);

    public List<Article> getUserArticles(int id);

}
