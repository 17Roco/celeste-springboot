package com.zsd.celeste.mapper;

import com.zsd.celeste.util.LinkConfig;
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

    @Select("select `${config.linkField}` from `${config.tableName}` where `${config.idField}` = ${id}")
    List<Integer> getLinkField(LinkConfig config, Integer id);
    @Select("select count(`${config.idField}`) from `${config.tableName}` group by `${config.idField}` having `${config.idField}`= ${id}")
    List<Integer> getLinkFieldCount(LinkConfig config,Integer id);
    @Insert("insert into `${config.tableName}` (`${config.idField}`,`${config.linkField}`) values (${id},${linkId})")
    Integer addLink(LinkConfig config,Integer id,Integer linkId);
    @Delete("delete from `${config.tableName}` where `${config.idField}` = ${id} and `${config.linkField}` = ${linkId}")
    Integer delLink(LinkConfig config,Integer id,Integer linkId);









    default List<Integer> getTidByAid(Integer aid){
        return null;
    }
    default List<Integer> getAidByTid(Integer tid){
        return null;
    }
}
