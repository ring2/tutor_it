package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import xyz.ring2.admin.common.RestResult;
import ${package.Entity}.dto.${entity}DTO;
import xyz.ring2.admin.common.QueryParam;
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

 /**
  * 创建实体
  *
  * @param object 前端接收的实体DTO
  * @return
  */
  RestResult create(${entity}DTO object);

 /**
  * 删除实体
  *
  * @param id 需要删除的主键id
  * @return
  */
  RestResult delete(Integer id);

 /**
  * 修改实体
  *
  * @param object 前端接收的实体DTO
  * @return
  */
  RestResult update(${entity}DTO object);

 /**
   * 分页查询某实体
  * @param queryParam 分页条件及相关参数
  * @return
  */
  RestResult listByPage(QueryParam queryParam);
}
</#if>
