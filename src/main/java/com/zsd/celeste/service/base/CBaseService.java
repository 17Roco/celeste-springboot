package com.zsd.celeste.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.mapper.LinkMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface CBaseService<T> extends IService<T> {

    LinkMapper getLinkMapper();
    /**
    * 设置分页大小
    * */
    default int getPageSize(){return 20;}

    default void selectAfter(T entity){  }
    default boolean insertAfter(T entity){ return true; }
    default boolean updateAfter(T entity){ return true; }
    default boolean delAfter(Integer id){ return true; }


    /**
     * 不分页查询
     * 分页查询
     * 单查询
     * */
    default List<T> _select(Function<QueryWrapper<T>,QueryWrapper<T>> w){
        // wrapper
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (!Objects.isNull(w))w.apply(wrapper);
        // return
        List<T> list = list(wrapper);
        list.forEach(this::selectAfter);
        return list;
    }
    default IPage<T> _select(Function<QueryWrapper<T>,QueryWrapper<T>> w, int index){
        // page
        IPage<T> p = Page.of(index, getPageSize());
        // wrapper
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (!Objects.isNull(w))w.apply(wrapper);
        // return
        IPage<T> page = page(p, wrapper);
        page.getRecords().forEach(this::selectAfter);
        return page;
    }
    default T _selectOne(Function<QueryWrapper<T>,QueryWrapper<T>> w){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (!Objects.isNull(w))w.apply(wrapper);
        T one = getOne(wrapper);
        selectAfter(one);
        return one;
    }

    /**
     * 新增
     * 更新
     * 删除
     * */
    default boolean _insert(T entity){
        return save(entity) && insertAfter(entity);
    }
    default boolean _update(T entity){
        return updateById(entity) && updateAfter(entity);
    }
    default boolean _del(Integer id){
        return removeById(id) && delAfter(id);
    }










    /** X X X X X X X X X  X X X X  X XX X X X X X X XX X X X X X X  XX X X XX X X X XX X  X X X X X XX X X X X X X  X X XX X X X  X XX X  X   X X XX X X X X X X
     * 分页查询，单字段eq
     * */
    default IPage<T> selectEq(String column,Object val,int index) {
        return _select(wrapper -> wrapper.eq(column,val),index);
    }
    /**
     * 分页查询（无where）
     * */
    default IPage<T> page(int index){
        return _select(null,index);
    }




}
