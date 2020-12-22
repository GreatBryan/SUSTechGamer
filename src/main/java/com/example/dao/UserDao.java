package com.example.dao;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserDao extends JpaRepository<User,Integer> {
    List<User> findUserByUid(int uid);


    //login
    List<User> findUsersByNameAndPassword(String name,String password);

    //findAll
    List<User> findAll();

    List<User> findByName(String name);

    List<User> findAllByName(String name);

    List<User> findAllByUid(int uid);

    //sign in
    @Override
    User saveAndFlush(User user);

    @Modifying
    @Query("update User u set u.password = ?2 where u.uid= ?1")
    void updatePasswordByID(Integer id, String password);

    @Modifying
    @Query("update User u set u.balance = ?2 where u.uid= ?1")
    void updateBalanceByID(Integer id, double balance);

    @Modifying
    @Query("update User u set u.name = ?1 where u.uid = ?2")
    void updateNameByID(String name, Integer id);

    @Modifying
    @Query("update User u set u.sex = ?1 where u.uid = ?2")
    void updateSexByID(char sex, Integer id);

    @Modifying
    @Query("update User u set u.role = ?1 where u.uid = ?2")
    void updateRoleByID(Integer role, Integer id);

}
