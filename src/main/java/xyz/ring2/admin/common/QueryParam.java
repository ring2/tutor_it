package xyz.ring2.admin.common;

import lombok.Data;

/**
 * @author :     ring2
 * @date :       2020/4/19 20:14
 * description:
 **/
@Data
public abstract class QueryParam {
    private Integer pageNum;
    private Integer pageSize;
}
