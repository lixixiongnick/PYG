package com.pyg.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.maneger.service.GoodsService;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.pojo.TbGoodsDescExample.Criteria;
import com.pyg.utils.PageResult;
import com.pyg.vo.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private TbSellerMapper sellerMapper;
	@Autowired
	private TbBrandMapper brandMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private TbItemMapper itemMapper;
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
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
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
		goodsMapper.insertSelective(tbGoods);
        TbGoodsDesc goodsDesc = new TbGoodsDesc();
        goodsDesc.setGoodsId(tbGoods.getId());
		goodsDescMapper.insertSelective(goodsDesc);//插入商品扩展数据

		if ("1".equals(goods.getTbGoods().getIsEnableSpec())){
			for (TbItem tbItem:goods.getTbItemList()){
				String title = goods.getTbGoods().getGoodsName();
				//获取spec
				Map<String,String> specmap = (Map<String, String>) JSON.parse(tbItem.getSpec());
					for (String key : specmap.keySet()){
						title += " "+specmap.get(key);

				}
				tbItem.setTitle(title+tbGoods.getCaption());
					this.saveitem(goods,tbItem);
				}

			}else{

			//未启用规格
			//创建一个sku商品对象，保存商品对象
				TbItem tbItem = new TbItem();
				tbItem.setPrice(goods.getTbGoods().getPrice());
				tbItem.setTitle(tbGoods.getGoodsName()+tbGoods.getCaption());
				this.saveitem(goods,tbItem);

		}


	}

	private void saveitem(Goods goods,TbItem tbItem) {

		//商品图片
		String itemImages = goods.getTbGoodsDesc().getItemImages();
		List<Map> mapList = JSON.parseArray(itemImages, Map.class);
		if (mapList.size()>0) {
			String url = (String) mapList.get(0).get("url");
			tbItem.setImage(url);
		}
		//所属类目，叶子类目
		tbItem.setCategoryid(goods.getTbGoods().getCategory3Id());
		//创建时间
		Date  date = new Date();
		tbItem.setCreateTime(date);
		//修改时间
		tbItem.setUpdateTime(date);
		//spuid
		tbItem.setGoodsId(goods.getTbGoods().getId());
		//商家id
		tbItem.setSellerId(goods.getTbGoods().getSellerId());
		//商家名称
		TbSeller tbSeller = sellerMapper.selectByPrimaryKey(goods.getTbGoods().getSellerId());
		tbItem.setSeller(tbSeller.getNickName());
		//品牌
		TbBrand tbBrand = brandMapper.selectByPrimaryKey(goods.getTbGoods().getBrandId());
		tbItem.setBrand(tbBrand.getName());
		//分类名称
		TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(goods.getTbGoods().getCategory3Id());
		tbItem.setCategory(tbItemCat.getName());
		//插入数据
		itemMapper.insertSelective(tbItem);
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
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsDelete("0");
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}


		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbGoodsExample example = new TbGoodsExample();
			TbGoodsExample.Criteria criteria = example.createCriteria();
			criteria.andIsDeleteEqualTo("1");
			if (goods.getSellerId()!=null&&!"".equals(goods.getSellerId())) {
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}

			if (goods.getGoodsName()!=null&&goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if (goods.getAuditStatus()!=null&&!"".equals(goods.getAuditStatus())){
				criteria.andAuditStatusEqualTo(goods.getAuditStatus());
			}

			Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String Status) {
		for (Long id:ids){
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setAuditStatus(Status);
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	@Override
	public void updateMarketable(Long[] ids, String status) {
		for (Long id:ids){
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsMarketable(status);
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

}
