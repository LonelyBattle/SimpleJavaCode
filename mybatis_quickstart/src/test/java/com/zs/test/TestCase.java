package com.zs.test;

import com.zs.entity.Husband;
import com.zs.entity.Monster;
import com.zs.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestCase {
    private SqlSession sqlSession;
    private MonsterMapper monsterMapper;
    private MonsterAnnotation monsterAnnotation;

    @Before
    public void init() {
        sqlSession = MybatisUtils.getSqlSession();
        monsterMapper = sqlSession.getMapper(MonsterMapper.class);
        System.out.println("sqlSession.getClass():" + sqlSession.getClass());
        System.out.println("monsterMapper.getClass():" + monsterMapper.getClass());
    }

    @Test
    public void t1() {

    }

    @Test
    public void addMonster() {
        for (int i = 0; i < 2; i++) {
            Monster monster = new Monster();
            monster.setAge(10 + i * 5);
            monster.setName("怪物" + i);
            monsterMapper.addMonster(monster);
        }
        if (null != sqlSession) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("保存成功");
    }

    @Test
    public void selectOne() {
        Monster monster = monsterMapper.selectOne(3);
        System.out.println("monster:" + monster);
        monster = monsterMapper.selectOne(3);
        if (null != sqlSession) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("sqlSession:"+sqlSession);
        System.out.println("查询成功");
    }

    @Test
    public void selectAll() {
        MonsterAnnotation mapper = sqlSession.getMapper(MonsterAnnotation.class);
        List<Monster> monsters = mapper.selectAll();
        for (Monster monster : monsters) {
            System.out.println("monster:" + monster);
        }
        if (null != sqlSession) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("查询成功");
    }

    @Test
    public void getHusband() {
        HusbandMapper mapper = sqlSession.getMapper(HusbandMapper.class);
        Husband husband = mapper.selectOne(1);
        System.out.println("husband:" + husband);
    }
}
