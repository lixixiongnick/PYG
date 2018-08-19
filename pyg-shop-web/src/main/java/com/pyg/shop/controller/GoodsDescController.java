package com.pyg.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.maneger.service.GoodsDescService;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.utils.PageResult;
import com.pyg.utils.pygResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

	@Reference
	private GoodsDescService goodsDescService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoodsDesc> findAll(){			
		return goodsDescService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage/{page}/{rows}")
	public PageResult  findPage(@PathVariable int page,@PathVariable int rows){			
		return goodsDescService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goodsDesc
	 * @return
	 */
	@RequestMapping("/add")
	public pygResult add(@RequestBody TbGoodsDesc goodsDesc){
		try {
			goodsDescService.add(goodsDesc);
			return new pygResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new pygResult(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goodsDesc
	 * @return
	 */
	@RequestMapping("/update")
	public pygResult update(@RequestBody TbGoodsDesc goodsDesc){
		try {
			goodsDescService.update(goodsDesc);
			return new pygResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new pygResult(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	public TbGoodsDesc findOne(@PathVariable Long id){
		return goodsDescService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete/{ids}")
	public pygResult delete(@PathVariable Long [] ids){
		try {
			goodsDescService.delete(ids);
			return new pygResult(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new pygResult(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoodsDesc goodsDesc, int page, int rows  ){
		return goodsDescService.findPage(goodsDesc, page, rows);		
	}
	
}
