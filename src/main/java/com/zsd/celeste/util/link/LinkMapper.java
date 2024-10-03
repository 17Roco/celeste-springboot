package com.zsd.celeste.util.link;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LinkMapper {


    // B
    @Select("select `${config.b}` from `${config.tableName}` where `${config.a}` = ${a}")
    List<Integer> get(LinkConfig config, Integer a);
    @Select("select count(`${config.a}`) from `${config.tableName}` group by `${config.a}` having `${config.a}`= ${a}")
    List<Integer> getCount(LinkConfig config, Integer a);


    // insert
    @Insert("insert into `${config.tableName}` (`${config.a}`,`${config.b}`) values (${a},${b})")
    Integer insert(LinkConfig config,Integer a,Integer b);
    Integer inserts(LinkConfig config,Integer a,List<Integer> b);

    // del
    @Delete("delete from `${config.tableName}` where `${config.a}` = ${a} and `${config.b}` = ${b}")
    Integer delete(LinkConfig config, Integer a, Integer b);
    Integer deletes(LinkConfig config, Integer a, List<Integer> b);


    // exits
    @Select("select count(*) from `${config.tableName}` where `${config.a}`= ${a} and `${config.b}`= ${b}")
    Boolean exits(LinkConfig config, Integer a, Integer b);





    default Boolean addLink(LinkConfig config,Integer a,Integer b){
        return !exits(config, a, b) && insert(config, a, b) == 1;
    }
    default Boolean addLink(LinkConfig config,Integer a,List<Integer> b){
        return inserts(config,a,b) == b.size();
    }
    default Boolean delLink(LinkConfig config,Integer a,Integer b){
        return exits(config, a, b) && delete(config, a, b) == 1;
    }
    default Boolean delLink(LinkConfig config,Integer a,List<Integer> b){
        return deletes(config,a,b) == b.size();
    }

}
