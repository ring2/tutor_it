package xyz.ring2.admin.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ring2.admin.core.entity.GoodsCategory;

import java.util.Map;

/**
 * @author :     weiquanquan
 * @date :       2020/2/14 21:10
 * description:
 **/
public interface GoodsCategoryService extends IService<GoodsCategory> {

    Map<String,Object> selGoodsCategoryByPage(Page<GoodsCategory> page,Integer type);
    void test();
}
