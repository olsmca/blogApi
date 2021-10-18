package org.olsmca.blogapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.olsmca.blogapi.domain.Blog;
import org.olsmca.blogapi.domain.User;
import org.olsmca.blogapi.model.BlogDTO;
import org.olsmca.blogapi.repos.BlogRepository;
import org.olsmca.blogapi.repos.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(final BlogRepository blogRepository, final UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public List<BlogDTO> findAll() {
        return blogRepository.findAll()
                .stream()
                .map(blog -> mapToDTO(blog, new BlogDTO()))
                .collect(Collectors.toList());
    }

    public BlogDTO get(final Long id) {
        return blogRepository.findById(id)
                .map(blog -> mapToDTO(blog, new BlogDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final BlogDTO blogDTO) {
        final Blog blog = new Blog();
        mapToEntity(blogDTO, blog);
        return blogRepository.save(blog).getId();
    }

    public void update(final Long id, final BlogDTO blogDTO) {
        final Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(blogDTO, blog);
        blogRepository.save(blog);
    }

    public void delete(final Long id) {
        blogRepository.deleteById(id);
    }

    private BlogDTO mapToDTO(final Blog blog, final BlogDTO blogDTO) {
        blogDTO.setId(blog.getId());
        blogDTO.setName(blog.getName());
        blogDTO.setHandle(blog.getHandle());
        blogDTO.setDescription(blog.getDescription());
        blogDTO.setUser(blog.getUser() == null ? null : blog.getUser().getId());
        return blogDTO;
    }

    private Blog mapToEntity(final BlogDTO blogDTO, final Blog blog) {
        blog.setName(blogDTO.getName());
        blog.setHandle(blogDTO.getHandle());
        blog.setDescription(blogDTO.getDescription());
        if (blogDTO.getUser() != null && (blog.getUser() == null || !blog.getUser().getId().equals(blogDTO.getUser()))) {
            final User user = userRepository.findById(blogDTO.getUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
            blog.setUser(user);
        }
        return blog;
    }

}
