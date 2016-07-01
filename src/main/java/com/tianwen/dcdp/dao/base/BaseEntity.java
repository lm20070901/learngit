package com.tianwen.dcdp.dao.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tianwen.dcdp.common.ClassUtils;

/**
 * Created by jiangyx on 2015/4/2.
 */
public class BaseEntity implements Serializable {

    // 表明前缀
    private static final String TABLE_PREFIX = "";
    private static final String PRIMARY_KEY = "id";

    /**
     * 获取Entity对应的表名
     * 不需要Entity中的属性定义@Table(name)
     *
     * @return
     */
    public String gainTableName() {
        return TABLE_PREFIX + changeAttrToDatabase(ClassUtils.getClassName(this.getClass())).substring(1);
    }

    /**
     * 获取Entity对应的id名称
     *
     * @return
     */
    public String gainPrimaryKey() {
        return PRIMARY_KEY;
    }

    /**
     * 获取驼峰转成下划线属性名集合
     *
     * @return
     */
    public Map<String, String> gainAttributesToDatabaseMap() {

        Map<String, String> newAttrs = new HashMap<String, String>();

        for (String attrName : ClassUtils.getAttributesName(this.getClass()))
            newAttrs.put(attrName, changeAttrToDatabase(attrName));

        return newAttrs;
    }

    /**
     * 属性名转换成下划线写法
     *
     * @param attr
     * @return
     */
    public String changeAttrToDatabase(String attr) {

        StringBuffer newAttr = new StringBuffer();

        for (char c : attr.toCharArray())
            if (Character.isUpperCase(c))
                newAttr.append("_" + Character.toLowerCase(c));
            else
                newAttr.append(c);

        return newAttr.toString();
    }

    /**
     * 判断对象属性是否为空
     *
     * @param attrName
     * @return
     */
    public boolean isNull(String attrName) {
        Object value = ClassUtils.getAttributeValue(this, attrName);
        if (value == null)
            return true;
        else
            return false;
    }

    /**
     * 获取对象属性名集合
     *
     * @return
     */
    public Set<String> gainAttributesNameSet() {
        return ClassUtils.getAttributesName(this.getClass());
    }

    /**
     * 获取查询结果映射
     *
     * @return
     */
    public String gainAllAttributesMapperToDatabase() {
        // 获取属性名集合
        Set<String> attrSet = gainAttributesNameSet();
        Map<String, String> attrMap = gainAttributesToDatabaseMap();

        boolean isFirst = true;

        StringBuffer selectMapper = new StringBuffer();
        for (String attrName : attrSet)
            if (isFirst) {
                isFirst = false;
                selectMapper.append(attrMap.get(attrName)).append(" as ").append(attrName);
            } else {
                selectMapper.append(",").append(attrMap.get(attrName)).append(" as ").append(attrName);
            }

        return selectMapper.toString();
    }

}
