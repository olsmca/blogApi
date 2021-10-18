package org.olsmca.blogapi.repos;

import org.olsmca.blogapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
