package com.codeplayer.dto;

import lombok.Data;

@Data
public class HotTagDTO implements Comparable {

    private String name;
    private Long priority;

    @Override
    public int compareTo(Object o) {
        return (int) (this.getPriority() - ((HotTagDTO)o).getPriority());
    }
}
