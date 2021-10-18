package org.olsmca.blogapi.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.olsmca.blogapi.domain.Blog;
import org.olsmca.blogapi.domain.Post;
import org.olsmca.blogapi.domain.Tag;
import org.olsmca.blogapi.model.PostDTO;
import org.olsmca.blogapi.repos.BlogRepository;
import org.olsmca.blogapi.repos.PostRepository;
import org.olsmca.blogapi.repos.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;

    public PostService(final PostRepository postRepository, final BlogRepository blogRepository,
            final TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.blogRepository = blogRepository;
        this.tagRepository = tagRepository;
    }

    public List<PostDTO> findAll() {
        return postRepository.findAll()
                .stream()
                .map(post -> mapToDTO(post, new PostDTO()))
                .collect(Collectors.toList());
    }

    public PostDTO get(final Long id) {
        return postRepository.findById(id)
                .map(post -> mapToDTO(post, new PostDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final PostDTO postDTO) {
        final Post post = new Post();
        mapToEntity(postDTO, post);
        return postRepository.save(post).getId();
    }

    public void update(final Long id, final PostDTO postDTO) {
        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(postDTO, post);
        postRepository.save(post);
    }

    public void delete(final Long id) {
        postRepository.deleteById(id);
    }

    private PostDTO mapToDTO(final Post post, final PostDTO postDTO) {
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setDate(post.getDate());
        postDTO.setName(post.getName() == null ? null : post.getName().getId());
        postDTO.setTags(post.getTags() == null ? null : post.getTags().stream()
                .map(tag -> tag.getId())
                .collect(Collectors.toList()));
        return postDTO;
    }

    private Post mapToEntity(final PostDTO postDTO, final Post post) {
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDate(postDTO.getDate());
        if (postDTO.getName() != null && (post.getName() == null || !post.getName().getId().equals(postDTO.getName()))) {
            final Blog name = blogRepository.findById(postDTO.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "name not found"));
            post.setName(name);
        }
        if (postDTO.getTags() != null) {
            final List<Tag> tags = tagRepository.findAllById(postDTO.getTags());
            if (tags.size() != postDTO.getTags().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of tags not found");
            }
            post.setTags(tags.stream().collect(Collectors.toSet()));
        }
        return post;
    }

}
