
package com.rest.blog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
public class Category {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int categoryId;

@NotBlank
@Column(name="title")
private String categoryTitle;

@NotBlank
@Column(name="description")
private String categoryDescription;


/**If a category is deleted, all the posts of that category will get deleted automatically.*/
@OneToMany(mappedBy ="category", cascade=CascadeType.ALL)
private List<Post> posts = new ArrayList<>();
}
}
