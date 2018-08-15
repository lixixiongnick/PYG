package com.pyg.maneger.service;

import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/10.
 */
public interface BrandService {
    /**
     * 需求：查询所有品牌数据
     */
    public List<TbBrand> findAll();

    //分页查询
    PageResult findPage(int pageNum, int pageSize);
    //保存品牌
    void save(TbBrand tbBrand);
    //根据id查询
    TbBrand findById(Long id);
    //根据id修改
    void updata(TbBrand tbBrand);
    //根据id删除
    void del(Long [] ids);
    //查询品牌
    List<Map> findbrandList();
}
