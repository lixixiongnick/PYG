package com.pyg.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.maneger.service.specificationService;
import com.pyg.mapper.TbSpecificationMapper;
import com.pyg.mapper.TbSpecificationOptionMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.vo.Specification;
import com.pyg.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class specificationServiceImp implements specificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;


    @Override
    public PageResult findPage(int pageNum, int pageSize) {
       PageHelper.startPage(pageNum, pageSize);
       //分页查询
      Page<TbSpecification>  page = (Page<TbSpecification>) specificationMapper.selectByExample(null);

        return  new PageResult(page.getTotal(),page.getResult());
    }


    public void save(Specification specification) {
        //保存规格数据，规格数据保存成功返回主键，把主键设置规格选项的外键字段上面
        //返回主键：配置映射文件 <selectKey> select last_insert_id()
        //返回主键：useGeneratedKeys="true" keyProperty="id"
        //获取规格对象
        TbSpecification tbSpecification = specification.getTbSpecification();
        //保存
        specificationMapper.insert(tbSpecification);

        //获取规格选项集合对象
        List<TbSpecificationOption> specificationOptionList = specification.getTbSpecificationOptionList();
        //循环遍历规格选项，把每一个规格选项对象都保存
        for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
            //设置外键
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            //保存
            specificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    @Override
    public Specification findById(Long id) {
        //先查询规格数据
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        //根据规格主键，查询规格选项，规格主键是规格选项的外键
        //创建example对象
        //select * from tb_speciftication_option where spec_id = #{specId}
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        //设查询参数，根据外键查询
        criteria.andSpecIdEqualTo(tbSpecification.getId());

        //执行查询
        List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);

        //创建包装类对象
        Specification specification = new Specification();
        //添加规格数据
        specification.setTbSpecification(tbSpecification);
        //添加规格选项
        specification.setTbSpecificationOptionList(specificationOptionList);

        return specification;
    }

    @Override
    public void updata(Specification specification) {
        TbSpecification tbSpecification = specification.getTbSpecification();
        //保存修改的规格
        specificationMapper.updateByPrimaryKeySelective(tbSpecification);
        //删除原有的规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        //设查询参数，根据外键查询
        criteria.andSpecIdEqualTo(tbSpecification.getId());
                specificationOptionMapper.deleteByExample(example);
                //添加新的规格选项
        List<TbSpecificationOption> specificationOptionList = specification.getTbSpecificationOptionList();
        for (TbSpecificationOption specificationOption:specificationOptionList){
            //设置外键
                specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insertSelective(specificationOption);
        }

    }

    @Override
    public void del(Long[] ids) {
       for (Long id:ids){
           specificationMapper.deleteByPrimaryKey(id);
           //根据外键删除规格选项
           TbSpecificationOptionExample example = new TbSpecificationOptionExample();
           //创建criteria对象
           TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
                criteria.andIdEqualTo(id);
                specificationOptionMapper.deleteByExample(example);
       }
    }
}
