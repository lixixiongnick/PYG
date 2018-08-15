package com.pyg.mapper;

import com.pyg.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandMapper {
    //查询全部
    List<TbBrand> findAll();
    //保存
    void save(TbBrand tbBrand);
    //根据id查询
    TbBrand findById(Long id);
    //根据id修改
    void updata(TbBrand tbBrand);
    //根据id删除
    void del(Long id);
    //查询品牌
    List<Map> findbrandList();
}
