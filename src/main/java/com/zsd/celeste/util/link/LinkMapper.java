package com.zsd.celeste.util.link;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LinkMapper {

//    @Select("select `${linkField}` from `${tableName}` where `${field}` = ${id}")
//    List<Integer> getLinkField(String field,Integer id,String tableName,String linkField);
//    @Select("select count(`${field}`) from `${tableName}` group by `${field}` having `${field}`= ${id}")
//    List<Integer> getLinkFieldCount(String field,Integer id,String tableName,String linkField);
//    @Insert("insert.sql into `${tableName}` (`${field}`,`${linkField}`) values (${id},${linkId})")
//    Integer addLink(String field,Integer id,String tableName,String linkField,Integer linkId);
//    @Delete("delete from `${tableName}` where `${field}` = ${id} and `${linkField}` = ${linkId};")
//    Integer delLink(String field,Integer id,String tableName,String linkField,Integer linkId);
    // B
    @Select("select `${config.b}` from `${config.tableName}` where `${config.a}` = ${a}")
    List<Integer> getB(LinkConfig config, Integer a);
    @Select("select count(`${config.a}`) from `${config.tableName}` group by `${config.a}` having `${config.a}`= ${a}")
    List<Integer> getBCount(LinkConfig config, Integer a);
    // A
    @Select("select `${config.a}` from `${config.tableName}` where `${config.b}` = ${b}")
    List<Integer> getA(LinkConfig config, Integer b);
    @Select("select count(`${config.b}`) from `${config.tableName}` group by `${config.b}` having `${config.b}`= ${b}")
    List<Integer> getACount(LinkConfig config, Integer b);
    // insert del
    @Insert("insert into `${config.tableName}` (`${config.a}`,`${config.b}`) values (${a},${b})")
    Integer insert(LinkConfig config,Integer a,Integer b);
    @Delete("delete from `${config.tableName}` where `${config.a}` = ${a} and `${config.b}` = ${b}")
    Integer del(LinkConfig config, Integer a, Integer b);
    // exits
    @Select("select count(*) from `${config.tableName}` where `${config.a}`= ${a} and `${config.b}`= ${b}")
    Boolean exits(LinkConfig config, Integer a, Integer b);



    default Boolean addLink(LinkConfig config,Integer a,Integer b){
        return !exits(config, a, b) && insert(config, a, b) == 1;
    }
    default Boolean delLink(LinkConfig config,Integer a,Integer b){
        return exits(config, a, b) && del(config, a, b) == 1;
    }

}
