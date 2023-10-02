package com.poly.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "favorites", 
uniqueConstraints = { @UniqueConstraint(columnNames = { "videoid", "userid" }) })
@Getter
@Setter
@NamedQueries({
	@NamedQuery(name = "Favorite.countFavoriteVideoByUser", query = "SELECT o FROM Favorite o WHERE o.user.id=:id"),
})
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	@JoinColumn(name = "userid")
	@JsonIgnore
	User user;
	@ManyToOne
	@JoinColumn(name = "videoid")
	@JsonIgnore
	Video video;
	@Temporal(TemporalType.DATE)
	@Column(name = "likedate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	Date likeDate = new Date();
}
