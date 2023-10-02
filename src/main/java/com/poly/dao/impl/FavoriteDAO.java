package com.poly.dao.impl;

import java.util.List;

import com.poly.common.QueryParamemter;
import com.poly.dao.IFavoriteDAO;
import com.poly.dtos.FavoriteUserDto;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.utils.JpaUtils;

public class FavoriteDAO implements IFavoriteDAO {

	@Override
	public Favorite save(Favorite entity) {
		return JpaUtils.persist(Favorite.class, entity);
	}


	@Override
	public void deleteByVideoId(String videoId) {
		String query = "DELETE FROM Favorite f WHERE f.video.id = :vid";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", videoId));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

	@Override
	public void deleteByUserId(String userId) {
		String query = "DELETE FROM Favorite f WHERE f.user.id = :uid";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("uid", userId));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}


	@Override
	public void deleteByUserAndVideo(User user, Video video) {
		String query = "DELETE FROM Favorite f WHERE f.user.id = :uid AND f.video.id = :vid";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("uid", user.getId()),new QueryParamemter("vid", video.getId()));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

	@Override
	public int countFavoriteVideoByUser(String userId) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("id", userId));
		List<Favorite> list = JpaUtils.excuteQuery("Favorite.countFavoriteVideoByUser", queryParamemters, JpaUtils.NAME_QUERY);
		return list.size();
	}

}
