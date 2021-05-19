package com.codeplayer.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String email;
    private String avatarUrl;
}
