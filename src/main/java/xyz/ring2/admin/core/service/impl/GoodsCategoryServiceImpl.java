package xyz.ring2.admin.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ring2.admin.core.entity.GoodsCategory;
import xyz.ring2.admin.core.entity.vo.GoodsCategoryTree;
import xyz.ring2.admin.core.mapper.GoodsCategoryMapper;
import xyz.ring2.admin.core.service.GoodsCategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :     weiquanquan
 * @date :       2020/2/14 21:10
 * description:  商品分类相关业务
 **/
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {

    /**
     * 需求：首先判断page对象是否为空=》是否进行分页查询
     * 其次根据 type 值的不同查询不同的结构
     * case 1: 只查询第一层结构
     * case 2： 查询前两层结构
     * case 3/null： 获取所有层结构
     * 最后以Map 形式返回树形结构
     *
     * @param page 分页参数
     * @param type 类型[1,2,3]   1：只获取1级分类  2：只获取1、2级分类 3或者null：三级分类全部获取
     * @return 返回树形结构
     */
    @Override
    public Map<String, Object> selGoodsCategoryByPage(Page<GoodsCategory> page, Integer type) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        // 获取所有一级分类并以id进行升序排序
        queryWrapper.eq("cate_level", 0).orderByAsc("id");
        //获取到分页后的一级分类 如果分页对象为null 则不进行分页
        if (page == null) {
            List<GoodsCategory> goodsCategories = this.baseMapper.selectList(queryWrapper);
            map.put("data", getGoodsCategoryTree(goodsCategories, type));
        } else {
            IPage<GoodsCategory> e = this.baseMapper.selectPage(page, queryWrapper);
            List<GoodsCategory> records = e.getRecords();
            // 如果type=1 则无需获取子节点
            if (type != null && type.equals(1)) {
                map.put("data", e.getRecords());
            } else {
                //调用获取Tree结构的分类结果
                map.put("data", getGoodsCategoryTree(records, type));
            }
            map.put("total", e.getTotal());
            map.put("pageSize", e.getSize());
            map.put("pageNum", e.getCurrent());
            return map;
        }
        return map;
    }

    /**
     * @param originGoodsCate 上一级分类
     * @return 通过遍历每一级分类通过其id获取其子集再递归返回
     */
    private List<GoodsCategoryTree> getGoodsCategoryTree(List<GoodsCategory> originGoodsCate, Integer type) {
        List<GoodsCategoryTree> list = new ArrayList<>();
        if (originGoodsCate.size() > 0) {        //递归调用终结点
            originGoodsCate.forEach(
                    goodsCategory -> {  // 对每个分类进行遍历
                        //获取到该分类的所有子分类
                        List<GoodsCategory> children;
                        // 判断type是否为2，如果为2则至获取前两级的分类即可
                        if (type != null && type.equals(2)) {
                            children = lambdaQuery().eq(GoodsCategory::getCatePid, goodsCategory.getId()).le(GoodsCategory::getCateLevel, 1).list();
                        } else { // 如果type=3 或则为null则获取所有的子节点
                            children = lambdaQuery().eq(GoodsCategory::getCatePid, goodsCategory.getId()).list();
                        }
                        // 每个子分类再进行递归调用获取其子分类
                        List<GoodsCategoryTree> goodsCategoryTree = getGoodsCategoryTree(children, type);
                        GoodsCategoryTree categoryTree = new GoodsCategoryTree();
                        // 通过工具类进行值拷贝
                        BeanUtil.copyProperties(goodsCategory, categoryTree);
                        // 将获取到子分类设置为树形children
                        categoryTree.setChildren(goodsCategoryTree);
                        list.add(categoryTree);
                    }
            );
        }
        return list;
    }

}
