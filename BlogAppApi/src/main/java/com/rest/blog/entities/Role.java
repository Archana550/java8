
package com.rest.blog.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

   @Id
   
private int id;
private String name;
/**One role can have multiple user*/
@OneToMany(mappedBy="role",cascade= CascadeType.ALL)
private List<User> user;

}
