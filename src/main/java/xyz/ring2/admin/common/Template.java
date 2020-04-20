package xyz.ring2.admin.common;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author :     ring2
 * @date :       2020/4/19 19:58
 * description:
 **/
public class Template {


    @Autowired
    Object object;

    /**
     *  新增
     */
    @PostMapping
    public RestResult create(@RequestBody @Valid Object object) {
        return RestResult.success();
    }

    /**
     *  删除
     */
    @DeleteMapping("/{id}")
    public RestResult delete(@PathVariable Integer id) {
        return RestResult.success();
    }


    /**
     *  修改
     */
    @PutMapping
    public RestResult udpate(@RequestBody @Valid Object object) {
        return RestResult.success();
    }


    /**
     *  分页查询
     */
    @GetMapping("/list")
    public  RestResult listByPage(@RequestBody QueryParam queryParam) {
        return RestResult.success();
    }

    public static void main(String[] args) {

    }
}
