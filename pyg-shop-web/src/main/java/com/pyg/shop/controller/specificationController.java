package com.pyg.shop.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.maneger.service.specificationService;
import com.pyg.utils.PageResult;
import com.pyg.utils.pygResult;
import com.pyg.vo.Specification;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/specification")
public class specificationController {
    @Reference
    private specificationService specificationService;

    @RequestMapping("findPage/{pageNum}/{pageSize}")
    public PageResult findPage(@PathVariable int pageNum,@PathVariable int pageSize ){
        //调用 specificationService
        PageResult page =  specificationService.findPage(pageNum, pageSize);
        return page;
    }
    @RequestMapping("/save")
    public pygResult save(@RequestBody Specification specification){
        try {
            specificationService.save(specification);
            return  new pygResult(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new pygResult(false,"保存失败");
        }
    }
    @RequestMapping("/findById/{id}")
    public Specification findById(@PathVariable Long id){
        Specification specification = specificationService.findById(id);

        return specification;
    }
    @RequestMapping("/updata")
    public pygResult updata(@RequestBody Specification specification){
        try {
             specificationService.updata(specification);
            return new pygResult(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new pygResult(false,"保存失败");
        }
    }
    @RequestMapping("/del/{ids}")
    public pygResult del(@PathVariable Long [] ids){
        try {
             specificationService.del(ids);
            return new pygResult(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
           return new pygResult(false,"删除失败");
        }
    }
    @RequestMapping("specification")
    public List<Map> findspecification(){
        return specificationService.findspecIdsList();
    }
}
