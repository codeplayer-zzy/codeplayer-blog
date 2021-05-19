package com.codeplayer.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Data
@Component
public class TagsDTO implements Serializable {
    private static final long serialVersionUID = 4900730225411683682L;

    private String categoryName;
    private List<String> tagsName;

}
