package xyz.ring2.admin.utils;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import xyz.ring2.admin.core.entity.User;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author :     ring2
 * @date :       2020/2/6 13:19
 * description:  判断实体类中某些属性是否为空的工具类，已弃用
 **/
@Deprecated
public class FieldCheckUtils {

    public static Map<Class, SerializedLambda> cacheClass = new ConcurrentHashMap<>();

    /**
     * @param obj    实体类
     * @param fields 实体类中不能为null的字段名
     * @return
     */
    public static <T> boolean isFieldsNotNull(Object obj, IGetter<T>... fields) {
        if (obj == null) {
            return false;
        }
        for (IGetter<T> field : fields) {
            String filedName = getFieldName(field);
            Object valueByFieldName = getValueByFieldName(obj, filedName);
            if (valueByFieldName == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param obj    实体类
     * @param fields 不能为null且String类型的字段值不能为空的字段名
     */
    public static <T> boolean isFieldsNotEmpty(Object obj, IGetter<T>... fields) {
        if (obj == null) {
            return false;
        }
        for (IGetter<T> field : fields) {
            String filedName = getFieldName(field);
            Object valueByFieldName = getValueByFieldName(obj, filedName);
            // 如果该属性的值为null或者它是String类型且值为空也返回false
            if (valueByFieldName == null || isStringType(obj, filedName) && "".equals(valueByFieldName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据属性名获取属性值
     *
     */
    private static Object getValueByFieldName(Object obj, String fieldName) {
        Object value = null;
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String methodName = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = obj.getClass().getMethod(methodName, new Class[]{});
            value = method.invoke(obj, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 判断该属性名所对应的类型是否是String 类型
     *
     */
    private static boolean isStringType(Object object, String fieldName) {
        boolean flag = false;
        try {
            Type genericType = object.getClass().getDeclaredField(fieldName).getGenericType();
            flag = genericType.getTypeName().equals(String.class.getTypeName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return flag;
    }
    //获取属性名
    public static <T> String getFieldName(IGetter<T> g) {
        SerializedLambda serializedLambda = getSerializedLambda(g);
        String implMethodName = serializedLambda.getImplMethodName();
        String prefix = null;
        if (implMethodName.startsWith("get")){
            prefix = "get";
        }else if (implMethodName.startsWith("is")){
            prefix = "is";
        }
        return covertFirstWordToLower(implMethodName,prefix);
    }
    //example:  getUsername  =>  username
    private static String covertFirstWordToLower(String originName,String replace){
        String replace1 = originName.replace(replace, "");
        String s = replace1.substring(0, 1).toLowerCase();
        return s + replace1.substring(1);
    }

    //获取SerializedLambda实例
    public static SerializedLambda getSerializedLambda(Serializable s) {
        SerializedLambda serializedLambda = cacheClass.get(s.getClass());
        if (serializedLambda == null) {
            try {
                Method method = s.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                serializedLambda = (SerializedLambda)method.invoke(s);
                cacheClass.put(s.getClass(),serializedLambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return serializedLambda;
    }


}
