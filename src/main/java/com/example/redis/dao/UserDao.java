package com.example.redis.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.redis.bean.User;



/**
 * ユーザDAOインタフェース
 * @author lihuaguang
 *
 */
@Transactional
public interface UserDao extends JpaRepository<User, Long> {
	/**
	 * ユーザ名によって、ユーザを探す
	 * @param userName
	 * @return
	 */
	User findByUserName(String userName);


	@Modifying
	@Query(value = "update user set age = :age where id = :id",nativeQuery = true)
    void updateNameById(@Param("id") Long id, @Param("age") Integer age);

}
