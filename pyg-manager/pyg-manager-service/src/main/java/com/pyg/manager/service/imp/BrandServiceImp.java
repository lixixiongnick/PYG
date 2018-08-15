package com.pyg.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.maneger.service.BrandService;
import com.pyg.mapper.BrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImp implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
       PageHelper.startPage(pageNum, pageSize);
       //分页查询
      Page<TbBrand>  page = (Page<TbBrand>) brandMapper.findAll();

        return  new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void save(TbBrand tbBrand) {
        brandMapper.save(tbBrand);
    }

    @Override
    public TbBrand findById(Long id) {
        return brandMapper.findById(id);
    }

    @Override
    public void updata(TbBrand tbBrand) {
        brandMapper.updata(tbBrand);
    }

    @Override
    public void del(Long[] ids) {
       for (Long id:ids){
           brandMapper.del(id);
       }
    }

    @Override
    public List<Map> findbrandList() {
        return brandMapper.findbrandList();
    }
}
