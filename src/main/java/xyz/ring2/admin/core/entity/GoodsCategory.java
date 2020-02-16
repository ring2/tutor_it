package xyz.ring2.admin.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
    * @author :     ring2
    * @date :       2020/2/14 21:06
    * description:  
 **/
  
@Data
public class GoodsCategory implements Serializable{
    /**
    * 分类id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 分类名称
    */
    private String cateName;

    /**
    * 分类父id
    */
    private Long catePid;

    /**
    * 当前层级
    */
    private Integer cateLevel;


    /**
     * 是否删除 false 未删除  true 已删除
     */
    private Boolean cateDeleted;
}