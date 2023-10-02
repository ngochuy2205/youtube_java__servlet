package com.poly.entity;

import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NamedQueries({ @NamedQuery(name = "Video.findByKeyword", query = "Select o From Video o Where o.title Like :keyword"),
		@NamedQuery(name = "Video.findFavoriteByUser", query = "SELECT o.video FROM Favorite o WHERE o.user.id=:id"),
		@NamedQuery(name = "Video.findAll", query = "SELECT o FROM Video o"),
		@NamedQuery(name="Video.getJustWatchVideo", query="SELECT o FROM Video o WHERE o.id IN :ids"),
		@NamedQuery(name = "Video.random10", query = "SELECT o FROM Video o ORDER BY newid()"),
})

@NamedNativeQueries({
	@NamedNativeQuery(name="video.share", query="select videos.* from videos inner join favorites on videos.id = favorites.videoid\r\n"
			+ "group by videos.id, videos.active, videos.description, videos.shortDescription, videos.poster, videos.title, videos.views\r\n"
			+ "having count(videos.id)= (select min(countTable.total) from (select count(*) as total from favorites group by favorites.videoid) as countTable);", resultClass = Video.class),
})



public class Video {
	@Id
	String id;
	
	@Column(name = "title")
	String title;
	
	@Column(name = "poster")
	String poster;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "shortDescription")
	String shortDescription;
	
	@Column(name = "views")
	int views = 0;
	
	@Column(name = "active")
	boolean active = true;
	
	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	List<Favorite> favorites;
	
	@JsonBackReference(value = "vi-sh")
	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	List<Share> shares;
	
}
