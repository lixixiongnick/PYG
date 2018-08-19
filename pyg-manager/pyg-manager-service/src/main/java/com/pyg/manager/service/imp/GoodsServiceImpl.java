package com.pyg.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.maneger.service.GoodsDescService;
import com.pyg.maneger.service.GoodsService;
import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbGoodsDescExample;
import com.pyg.pojo.TbGoodsDescExample.Criteria;
import com.pyg.utils.PageResult;
import com.pyg.vo.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	@Autowired
    private TbGoodsMapper goodsMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoodsDesc> findAll() {
		return goodsDescMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbGoodsDesc> page=   (Page<TbGoodsDesc>) goodsDescMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
	    //设置未申请状态
        goods.getTbGoods().setAuditStatus("0");
        TbGoods tbGoods = goods.getTbGoods();
        goodsMapper.insert(tbGoods);
        goods.getTbGoodsDesc().setGoodsId(tbGoods.getId());//设置id
        goodsDescMapper.insert(goods.getTbGoodsDesc());//插入商品扩展数据
	}


	/**
	 * 修改
	 */
	@Override
	public void update(TbGoodsDesc goodsDesc){
		goodsDescMapper.updateByPrimaryKey(goodsDesc);
	}

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoodsDesc findOne(Long id){
		return goodsDescMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsDescMapper.deleteByPrimaryKey(id);
		}
	}


		@Override
	public PageResult findPage(TbGoodsDesc goodsDesc, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbGoodsDescExample example=new TbGoodsDescExample();
		Criteria criteria = example.createCriteria();

		if(goodsDesc!=null){

		}

		Page<TbGoodsDesc> page= (Page<TbGoodsDesc>)goodsDescMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
