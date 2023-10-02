package com.poly.dao.impl;

import java.util.List;

import com.poly.common.QueryParamemter;
import com.poly.dao.IUserDAO;
import com.poly.dtos.ShareUserDto;
import com.poly.dtos.FavoriteUserDto;
import com.poly.entity.User;
import com.poly.utils.JpaUtils;

public class UserDAO implements IUserDAO {

	@Override
	public User insert(User entity) {
		return JpaUtils.persist(User.class, entity);
	}
	

	@Override
	public User update(User entity) {
		return JpaUtils.merge(User.class, entity);
	}
	@Override
	public void updateInfomation(User entity) {
		String jpql ="UPDATE User o SET o.fullname=:fullname, o.email=:email WHERE o.id=:uid";
		List<QueryParamemter> queryParamemters = List.of(
				new QueryParamemter("fullname", entity.getFullname()),
				new QueryParamemter("email", entity.getEmail()),
				new QueryParamemter("uid", entity.getId())
				);
		JpaUtils.excuteUpdate(jpql, queryParamemters);
	}

	@Override
	public User findById(String id) {
		return JpaUtils.findById(User.class, id);
	}

	@Override
	public void delete(String[] ids) {
		String query = "DELETE FROM User o WHERE o.id IN :ids";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("ids", List.of(ids)));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

	@Override
	public List<User> findAllWithPagination(int offset, int skip) {
		return JpaUtils.findWithPagination("User.findAll",null, offset, skip);
	}

	@Override
	public int count() {
		return JpaUtils.count("User");
	}

	@Override
	public boolean isLikedVideo(String userid, String vid) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", vid),
				new QueryParamemter("uid", userid));
		List<User> users = JpaUtils.excuteQuery("User.findUserLikedVideo", queryParamemters, JpaUtils.NAME_QUERY);
		return users.size() > 0;
	}

	@Override
	public List<FavoriteUserDto> findAllFavoriteUser(String videoid) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", videoid));
		return JpaUtils.excuteQuery("User.findAllFavoriteUser", queryParamemters, JpaUtils.NAME_QUERY);
	}

	@Override
	public List<ShareUserDto> findAllShareUser(String videoid) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", videoid));
		return JpaUtils.excuteQuery("User.findAllShareUser", queryParamemters, JpaUtils.NAME_QUERY);
	}

	@Override
	public User findByEmail(String email) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("email", email));
		List<User> users = JpaUtils.excuteQuery("User.findByEmail", queryParamemters, JpaUtils.NAME_QUERY);
		return users.size() == 0 ? null : users.get(0);
	}

	public boolean isShareVideo(String userid, String vid) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", vid),
				new QueryParamemter("uid", userid));
		List<User> users = JpaUtils.excuteQuery("User.findUserSharedVideo", queryParamemters, JpaUtils.NAME_QUERY);
		return users.size() > 0;
	}
	
}
