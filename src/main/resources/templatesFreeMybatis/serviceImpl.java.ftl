package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import xyz.ring2.admin.common.RestResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ring2.admin.common.QueryParam;
import cn.hutool.core.bean.BeanUtil;
import ${package.Entity}.dto.${entity}DTO;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {


 @Override
 public RestResult create(${entity}DTO object) {
    ${entity} target = new ${entity}();
    BeanUtil.copyProperties(object,target);
    int i = this.baseMapper.insert(target);
    if (i > 0) {
        return RestResult.success();
    }
    return RestResult.failure();
 }

 @Override
 public RestResult delete(Integer id) {
    int i = this.baseMapper.deleteById(id);
    if (i > 0) {
    return RestResult.success();
    }
    return RestResult.failure();
 }

 @Override
 public RestResult update(${entity}DTO object) {
    ${entity} target = new ${entity}();
    BeanUtil.copyProperties(object,target);
    int i = this.baseMapper.updateById(target);
    if (i > 0) {
    return RestResult.success();
    }
    return RestResult.failure();
 }

 @Override
 public RestResult<Map> listByPage(QueryParam queryParam) {
    Map<String, Object> data = new HashMap<>(4);
    Page<${entity}> page = new Page<>();
    page.setCurrent(queryParam.getPageNum());
    page.setSize(queryParam.getPageSize());
    QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
    //queryWrapper wait to set something

    page = this.baseMapper.selectPage(page, queryWrapper);
    data.put("total", page.getTotal());
    data.put("list", page.getRecords());
    return RestResult.success(data);
 }
}
</#if>
