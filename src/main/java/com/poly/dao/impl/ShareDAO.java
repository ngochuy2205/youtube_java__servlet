package com.poly.dao.impl;

import java.util.List;

import com.poly.common.QueryParamemter;
import com.poly.dao.IShareDAO;
import com.poly.dtos.ShareUserDto;
import com.poly.entity.Share;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.utils.JpaUtils;

public class ShareDAO implements IShareDAO {

	@Override
	public Share insert(Share entity) {
		return JpaUtils.persist(Share.class, entity);
	}

	@Override
	public Share update(Share entity) {
		return JpaUtils.merge(Share.class, entity);
	}
	
	@Override
	public Share findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Share> findAllWithPagination(int page, int pagesize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByVideoId(String videoId) {
		String query = "DELETE FROM Share s WHERE s.video.id = :vid";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", videoId));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

	@Override
	public void deleteByUserId(String userId) {
		String query = "DELETE FROM Share s WHERE s.user.id = :vid";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", userId));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

	@Override
	public int count() {
		return 0;
	}

	@Override
	public Share findByUserAndVideo(String userid, String videoid) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("vid", videoid), new QueryParamemter("uid", userid));
		List<Share> shares = JpaUtils.excuteQuery("Share.findByUserAndVideo", queryParamemters, JpaUtils.NAME_QUERY);
		return shares.size() > 0 ? shares.get(0) : null;
	}


	public void deleteByUserAndVideo(String uid, String vid) {
		String query = "DELETE FROM Share f WHERE f.user.id = :uid AND f.video.id = :vid";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("uid", uid),new QueryParamemter("vid", vid));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

}
