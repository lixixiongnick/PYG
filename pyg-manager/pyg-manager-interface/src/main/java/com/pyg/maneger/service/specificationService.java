package com.pyg.maneger.service;

import com.pyg.pojo.TbSpecification;
import com.pyg.vo.Specification;
import com.pyg.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/10.
 */
public interface specificationService {


    //分页查询
    PageResult findPage(int pageNum, int pageSize);
    //保存品牌
   void save(Specification specification);
    //根据id查询
    Specification findById(Long id);
    //根据id修改
    void updata(Specification specification);
    //根据id删除
    void del(Long[] ids);
    //新增
    List<Map> findspecIdsList();

}
