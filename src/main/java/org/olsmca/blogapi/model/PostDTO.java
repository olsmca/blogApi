package org.olsmca.blogapi.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 255)
    private String content;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Long name;

    private List<Long> tags;

}
