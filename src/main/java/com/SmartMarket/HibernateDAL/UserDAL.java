package com.SmartMarket.HibernateDAL;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SmartMarket.Entity.Users;

public interface UserDAL extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

}
