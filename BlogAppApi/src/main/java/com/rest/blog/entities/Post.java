
package com.rest.blog.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class Post {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Integer postId;

@NotBlank
@Column(name="post_title",length=100, nullable=false)
private String title;

@NotBlank
@Column(length =10000)
private String content;



@CreationTimestamp
private Date createdAt;

@ManyToOne
@JoinColumn(name="category_id")
private Category category;

@ManyToOne
private User user;

}
