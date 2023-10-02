package com.poly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.poly.common.QueryParamemter;
import com.poly.dao.IVideoDAO;
import com.poly.entity.Video;
import com.poly.utils.JpaUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class VideoDAO implements IVideoDAO {

	@Override
	public Video insert(Video entity) {
		return JpaUtils.persist(Video.class, entity);
	}

	@Override
	public Video update(Video entity) {
		return JpaUtils.merge(Video.class, entity);
	}

	@Override
	public Video findById(String id) {
		return JpaUtils.findById(Video.class, id);
	}

	@Override
	public void delete(String[] ids) {
		String query = "DELETE FROM Video v WHERE v.id IN :ids";
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("ids", List.of(ids)));
		JpaUtils.excuteUpdate(query, queryParamemters);
	}

	@Override
	public List<Video> findAllWithPagination(int offset, int skip) {
		System.out.println(offset);
		return JpaUtils.findWithPagination("Video.findAll",null, offset, skip);
	}

	@Override
	public int count() {
		return JpaUtils.count("Video");
	}

	@Override
	public List<Video> randoms(int random) {
		return JpaUtils.findWithPagination("Video.random10", null, 0, random);
	}

	@Override
	public List<Video> findAll() {
		return JpaUtils.findAll("Video");
	}

	@Override
	public List<Video> findFavoriteVideoByUser(String userId, int offset, int size) {
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("id", userId));
		return JpaUtils.findWithPagination("Video.findFavoriteByUser", queryParamemters, offset, size);
	}

	@Override
	public List<Video> getJustWatchVideo(String[] ids) {
		List<String> videoIds = List.of(ids);
		List<QueryParamemter> queryParamemters = List.of(new QueryParamemter("ids", videoIds));
		return JpaUtils.excuteQuery("Video.getJustWatchVideo", queryParamemters, JpaUtils.NAME_QUERY);
	}

	@Override
	public int countFavorites(String videoid) {
		String jpql = "SELECT COUNT(o) FROM Favorite o WHERE o.video.id=:vid";
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			Query query = em.createQuery(jpql);
			query.setParameter("vid", videoid);
			Object result = query.getSingleResult();
			return Integer.parseInt(result.toString());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
		}
		return 0;
	}
	
	public List<Video> findWithShare(){
		return JpaUtils.excuteQuery("video.share", null, JpaUtils.NATIVE_QUERY);
	}
	
	public List<Video> reportShare(){
		return JpaUtils.excuteQuery("video.shareReport", null, JpaUtils.NATIVE_QUERY);
	}

}
