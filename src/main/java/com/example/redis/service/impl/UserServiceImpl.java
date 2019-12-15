package com.example.redis.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.redis.bean.User;
import com.example.redis.dao.UserDao;
import com.example.redis.service.UserService;
import com.example.redis.util.JsonUtils;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	/**
	 * Redis
	 */
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Override
	public User findUserByName(String userName) {
		User user = null;
		String key = "user_key_" + userName;
		ValueOperations<String,String> ops = redisTemplate.opsForValue();
		Boolean flag = redisTemplate.hasKey(key);
		// Redisで存在するかどうか
		if (flag) {
			Object object = ops.get(key);
			user = JsonUtils.json2Object(object.toString(), User.class);
		} else {
			// Redisで存在しなければ、Redisに入ります。
			user = userDao.findByUserName(userName);
			if (null != user) {
				ops.set(key, JsonUtils.object2Json(user));
			}
		}
		return user;
	}

	/**
	 * Redisで存在すれば、Redisから取得する。存在しなければ、DBから取得する。
	 */
	@Override
	public User findUserById(Long id) {
		User user = null;
		String key = "user_key_" + id;
		ValueOperations<String,String> ops = redisTemplate.opsForValue();
		Boolean flag = redisTemplate.hasKey(key);
		// Redisで存在するかどうか
		if (flag) {
			Object object = ops.get(key);
			user = JsonUtils.json2Object(object.toString(), User.class);
		} else {
			// Redisで存在しなければ、Redisに入ります。
			Optional<User> optionalUser = userDao.findById(id);
			if (null != optionalUser) {
				user = optionalUser.get();
				ops.set(key, JsonUtils.object2Json(user));
			}
		}
		return user;
	}


	@Override
	public User saveUser(User user) {
		return userDao.save(user);
	}


	@Override
	public void updateUser(User user) {
		userDao.updateNameById(user.getId(),user.getAge());
		String key = "user_key_" + user.getId();
		// Redisで存在する場合
		boolean haskey = redisTemplate.hasKey(key);
		if (haskey) {
			redisTemplate.delete(key);
			logger.info("UserServiceImpl.updateUser() : Redisから削除　>> " + user.toString());
		}
	}
	@Override
	public void deleteUser(Long id) {
		userDao.deleteById(id);
		// Redisで存在する場合
		String key = "user_key_" + id;
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			redisTemplate.delete(key);
			logger.info("UserServiceImpl.deleteUser() : Redisから削除　>> " + id);
		}
	}
}
