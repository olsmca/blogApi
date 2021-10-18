package org.olsmca.blogapi.rest;

import java.util.List;
import javax.validation.Valid;
import org.olsmca.blogapi.model.BlogDTO;
import org.olsmca.blogapi.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogController {

    private final BlogService blogService;

    public BlogController(final BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable final Long id) {
        return ResponseEntity.ok(blogService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createBlog(@RequestBody @Valid final BlogDTO blogDTO) {
        return new ResponseEntity<>(blogService.create(blogDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBlog(@PathVariable final Long id,
            @RequestBody @Valid final BlogDTO blogDTO) {
        blogService.update(id, blogDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable final Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
