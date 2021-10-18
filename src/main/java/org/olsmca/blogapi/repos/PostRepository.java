package org.olsmca.blogapi.repos;

import org.olsmca.blogapi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
}
