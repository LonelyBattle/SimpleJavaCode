package com.zs.test;

import com.zs.entity.Monster;

public interface MonsterMapper {

    public void addMonster(Monster monster);

    public Monster selectOne(Integer id);
}
