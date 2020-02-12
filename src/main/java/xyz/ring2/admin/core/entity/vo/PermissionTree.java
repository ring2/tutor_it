package xyz.ring2.admin.core.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author :  weiquanquan
 * @date :   2020/2/4 20:05
 **/
@Data
public class PermissionTree {
    private Integer id;
    private String name;
    private String uri;
    private String icon;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PermissionTree> children;
}
