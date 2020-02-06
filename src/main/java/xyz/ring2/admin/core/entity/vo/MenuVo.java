package xyz.ring2.admin.core.entity.vo;

import lombok.Data;
import xyz.ring2.admin.core.entity.Permission;

import java.util.List;

/**
 * @author :  weiquanquan
 * @date :   2020/2/4 20:05
 **/
@Data
public class MenuVo {
    private Integer id;
    private String name;
    private String uri;
    private String icon;
    private List<MenuVo> children;
}
