package org.olsmca.blogapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.olsmca.blogapi.domain.Tag;
import org.olsmca.blogapi.model.TagDTO;
import org.olsmca.blogapi.repos.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> findAll() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .collect(Collectors.toList());
    }

    public TagDTO get(final Long id) {
        return tagRepository.findById(id)
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final TagDTO tagDTO) {
        final Tag tag = new Tag();
        mapToEntity(tagDTO, tag);
        return tagRepository.save(tag).getId();
    }

    public void update(final Long id, final TagDTO tagDTO) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(tagDTO, tag);
        tagRepository.save(tag);
    }

    public void delete(final Long id) {
        tagRepository.deleteById(id);
    }

    private TagDTO mapToDTO(final Tag tag, final TagDTO tagDTO) {
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    private Tag mapToEntity(final TagDTO tagDTO, final Tag tag) {
        tag.setName(tagDTO.getName());
        return tag;
    }

}
