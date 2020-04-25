package com.project.list.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.project.list.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{

}
