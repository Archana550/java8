
package com.rest.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.rest.blog.entities.User;
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

User findByEmail(String email);
}
