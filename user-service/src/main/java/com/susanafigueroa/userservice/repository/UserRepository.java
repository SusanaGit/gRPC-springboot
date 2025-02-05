package com.susanafigueroa.userservice.repository;

import com.susanafigueroa.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // the CrudRepository already contains few methods for me

}
