package com.zs.test;

import com.zs.entity.Monster;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MonsterAnnotation {
    @Select(value="select * from `monster`")
    public List<Monster> selectAll();
}
