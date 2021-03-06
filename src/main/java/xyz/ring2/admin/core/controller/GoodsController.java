package xyz.ring2.admin.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.CourseCategory;
import xyz.ring2.admin.core.service.GoodsCategoryService;
import xyz.ring2.admin.utils.FieldCheckUtils;

import java.util.Map;

/**
 * @author :     weiquanquan
 * @date :       2020/2/14 21:13
 * description:  商品管理接口
 **/
@RestController
@Slf4j
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsCategoryService categoryService;

    @GetMapping("/categories")
    public RestResult<Map<String, Object>> getGoodsCategories(Integer type, Long pageNum, Long pageSize) {
        Map<String, Object> map;
        Page<CourseCategory> page = null;
        if (pageNum != null && pageSize != null) {
            page = new Page<>(pageNum, pageSize);
        }
        map = categoryService.selGoodsCategoryByPage(page, type);
        if (map != null) {
            return RestResult.success(map);
        }
        return RestResult.failureOfQuery();
    }

    @DeleteMapping()
    public RestResult delGoodsCategory(CourseCategory goodsCate) {
        if (goodsCate != null) {
            goodsCate.setCateDeleted(true);
            boolean b = categoryService.updateById(goodsCate);
            if (b) {
                return RestResult.success();
            }
            return RestResult.failure();
        }
        return RestResult.failure();
    }

    @PostMapping
    public RestResult saveGoodsCate(@RequestBody CourseCategory courseCategory) {
        boolean fieldsNotEmpty = FieldCheckUtils.isFieldsNotEmpty(courseCategory, CourseCategory::getCatePid, CourseCategory::getCateName, CourseCategory::getCateLevel);
        if (fieldsNotEmpty) {
            if (categoryService.save(courseCategory)) {
                return RestResult.success();
            }
            return RestResult.failure();
        }
        return RestResult.failureOfParam();
    }

    @PutMapping
    public RestResult updateGoodsCateName(@RequestBody CourseCategory courseCategory) {
        boolean fieldsNotEmpty = FieldCheckUtils.isFieldsNotEmpty(courseCategory, CourseCategory::getId, CourseCategory::getCateName);
        if (fieldsNotEmpty) {
            if (categoryService.updateById(courseCategory)) {
                return RestResult.success();
            }
            return RestResult.failure();
        }
        return RestResult.failureOfParam();
    }
}
