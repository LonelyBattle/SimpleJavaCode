package com.zs.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Husband {
    private Integer id;
    private String name;
    private Wife wife;
}
