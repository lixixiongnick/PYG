package com.pyg.portal.controller;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbContent;
import com.pyg.content.service.ContentService;

import com.pyg.utils.PageResult;
import com.pyg.utils.pygResult;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference
	private ContentService contentService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbContent> findAll(){			
		return contentService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage/{page}/{rows}")
	public PageResult  findPage(@PathVariable int page,@PathVariable int rows){			
		return contentService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param content
	 * @return
	 */
	@RequestMapping("/add")
	public pygResult add(@RequestBody TbContent content){
		try {
			contentService.add(content);
			return new pygResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new pygResult(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param content
	 * @return
	 */
	@RequestMapping("/update")
	public pygResult update(@RequestBody TbContent content){
		try {
			contentService.update(content);
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
	public TbContent findOne(@PathVariable Long id){
		return contentService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete/{ids}")
	public pygResult delete(@PathVariable Long [] ids){
		try {
			contentService.delete(ids);
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
	public PageResult search(@RequestBody TbContent content, int page, int rows  ){
		return contentService.findPage(content, page, rows);		
	}
	@RequestMapping("/findBycategoryId/{categoryId}")
    public List<TbContent> findBycategoryId(@PathVariable Long categoryId){
        List<TbContent> contentList = contentService.findBycategoryId(categoryId);
        return contentList;
    }
}
