package xyz.ring2.admin.core.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import xyz.ring2.admin.core.entity.CourseCategory;

import java.util.List;

/**
 * @author :     weiquanquan
 * @date :       2020/2/15 19:22
 * description:  返回商品的树形结构VO
 **/
@Data
public class GoodsCategoryTree extends CourseCategory {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<GoodsCategoryTree> children;
}
