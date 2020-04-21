package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import xyz.ring2.admin.common.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import ${package.Entity}.dto.${entity}DTO;
import xyz.ring2.admin.common.QueryParam;
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
@Slf4j
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} base${entity}Service;

   /**
    *  新增
    */
    @PostMapping
    public RestResult create(@RequestBody @Valid ${entity}DTO object) {
        return base${entity}Service.create(object);
    }

   /**
    *  删除
    */
    @DeleteMapping("/{id}")
    public RestResult delete(@PathVariable Integer id) {
        return base${entity}Service.delete(id);
    }

   /**
    *  修改
    */
    @PutMapping
    public RestResult update(@RequestBody @Valid ${entity}DTO object) {
        return base${entity}Service.update(object);
    }

   /**
    *  分页查询
    */
    @GetMapping("/list")
    public RestResult listByPage(@RequestBody QueryParam queryParam) {
        return base${entity}Service.listByPage(queryParam);
    }
}
</#if>
