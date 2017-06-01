package com.yihaomen.mybatis.test;

import com.yihaomen.mybatis.inter.IUserOperation;
import com.yihaomen.mybatis.model.Article;
import com.yihaomen.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * Created by pro on 17-6-1.
 */
public class Test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("Configure.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }

    public static void main(String[] args) {
//        deleteUser(7);
        getUserArticles(1);
    }

    public static void select() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
//            type 1
//            User user = (User) session.selectOne("com.yihaomen.mybatis.model.User.selectUserByID", 1);
//            System.out.println(user.getUserAddress());
//            System.out.println(user.getUserName());

//            type 2
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            User user = userOperation.selectUserByID(1);
//            System.out.println(user.getUserAddress());
//            System.out.println(user.getUserName());

            IUserOperation userOperation = session.getMapper(IUserOperation.class);
            List<User> users = userOperation.selectUsers("%m%");
            for (User user : users) {
                System.out.println(user.getId() + "\t" + user.getUserName() + "\t" + user.getUserAddress());
            }
        } finally {
            session.close();
        }
    }

    public static void addUser() {
        User user = new User();
        user.setUserAddress("useraddress");
        user.setUserName("username");
        user.setUserAge("80");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session.getMapper(IUserOperation.class);
            userOperation.addUser(user);
            session.commit();
            System.out.println("当前增加的用户 id为:" + user.getId());
        } finally {
            session.close();
        }
    }

    public static void updateUser() {
        //先得到用户,然后修改，提交。
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session.getMapper(IUserOperation.class);
            User user = userOperation.selectUserByID(7);
            user.setUserAddress("useraddress1");
            userOperation.updateUser(user);
            session.commit();

        } finally {
            session.close();
        }
    }

    public static void deleteUser(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session.getMapper(IUserOperation.class);
            userOperation.deleteUser(id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public static void getUserArticles(int userid) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session.getMapper(IUserOperation.class);
            List<Article> articles = userOperation.getUserArticles(userid);
            for (Article article : articles) {
                System.out.println(article.getTitle() + ":" + article.getContent() +
                        ":作者是:" + article.getUser().getUserName() + ":地址:" +
                        article.getUser().getUserAddress());
            }
        } finally {
            session.close();
        }
    }
}
