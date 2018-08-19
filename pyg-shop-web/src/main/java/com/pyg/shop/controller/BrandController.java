package com.pyg.shop.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.maneger.service.BrandService;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import com.pyg.utils.pygResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    @RequestMapping("findAll")
    public List<TbBrand> findAll(){
        List<TbBrand> tbBrandList = brandService.findAll();
        return tbBrandList;
    }
    @RequestMapping("findPage/{pageNum}/{pageSize}")
    public PageResult findPage(@PathVariable int pageNum,@PathVariable int pageSize ){
        //调用brandService
        PageResult page = brandService.findPage(pageNum, pageSize);
        return page;
    }
    @RequestMapping("/save")
    public pygResult save(@RequestBody TbBrand tbBrand){
        try {
            brandService.save(tbBrand);
           return new pygResult(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
           return new pygResult(false,"保存失败");

        }
    }
    @RequestMapping("/findById/{id}")
    public TbBrand findById(@PathVariable Long id){
        TbBrand tbBrand = brandService.findById(id);
        return tbBrand;
    }
    @RequestMapping("/updata")
    public pygResult updata(@RequestBody TbBrand tbBrand){
        try {
            brandService.updata(tbBrand);
            return new pygResult(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new pygResult(false,"保存失败");
        }
    }
    @RequestMapping("/del/{ids}")
    public pygResult del(@PathVariable Long [] ids){
        try {
            brandService.del(ids);
            return new pygResult(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
           return new pygResult(false,"删除失败");
        }
    }
    @RequestMapping("findbrandList")
    public List<Map> findbrandList(){
      return   brandService.findbrandList();
    }
}
