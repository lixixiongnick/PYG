package com.pyg.manager.service.imp;
import java.util.List;

import com.pyg.maneger.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbSellerMapper;
import com.pyg.pojo.TbSeller;
import com.pyg.pojo.TbSellerExample;
import com.pyg.pojo.TbSellerExample.Criteria;


import com.pyg.utils.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeller> page=   (Page<TbSeller>) sellerMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeller seller) {
		sellerMapper.insert(seller);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeller seller){
		sellerMapper.updateByPrimaryKey(seller);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeller findOne(String id){
		return sellerMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			sellerMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSellerExample example=new TbSellerExample();
		Criteria criteria = example.createCriteria();
		
		if(seller!=null){			
				
		}
		
		Page<TbSeller> page= (Page<TbSeller>)sellerMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(String id, String status) {
		TbSeller seller = sellerMapper.selectByPrimaryKey(id);
		seller.setStatus(status);
		sellerMapper.updateByPrimaryKey(seller);
	}

}
