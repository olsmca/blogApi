package org.olsmca.blogapi.repos;

import org.olsmca.blogapi.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Blog, Long> {
}
