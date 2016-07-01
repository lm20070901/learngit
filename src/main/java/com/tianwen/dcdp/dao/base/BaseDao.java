package com.tianwen.dcdp.dao.base;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 通用Mapper接口
 * Created by jiangyx on 2015/4/2.
 */
public interface BaseDao<T extends BaseEntity> {

//****************各数据库查询自增主键方法**************************
//    Cloudscape  VALUES IDENTITY_VAL_LOCAL()
//    DB2         VALUES IDENTITY_VAL_LOCAL()
//    Derby       VALUES IDENTITY_VAL_LOCAL()
//    HSQLDB      CALL IDENTITY()
//    MySql       SELECT LAST_INSERT_ID()
//    SqlServer   SELECT SCOPE_IDENTITY()
//    SYBASE      SELECT @@IDENTITY
//    ORACLE      SELECT CUSTOM_SQL.NEXTVAL AS ID FROM DUAL

    // MySQL主键查询语句
    public static final String SELECT_INSERT_PRIMARY_KEY = "SELECT LAST_INSERT_ID()";


    /**
     * 通用多条件删除操作
     * @param object
     * @return
     */
    @DeleteProvider(type = SQLTemplate.class, method = "deleteAnd")
    public Integer deleteAnd(T object);

    /**
     * 通用多条件or查询操作
     * @param object
     * @param page
     * @return
     */
    @SelectProvider(type = SQLTemplate.class, method = "selectOr")
    public PageList<T> selectOr(T object, PageBounds page);

    /**
     * 通用多条件and查询操作
     * @param object
     * @param page
     * @return
     */
    @SelectProvider(type = SQLTemplate.class, method = "selectAnd")
    public PageList<T> selectAnd(T object, PageBounds page);

    /**
     * 通用like多条件or查询操作
     * @param object
     * @param page
     * @return
     */
    @SelectProvider(type = SQLTemplate.class, method = "selectOrLike")
    public PageList<T> selectOrLike(T object, PageBounds page);

    /**
     * 通用like多条件and查询操作
     * @param object
     * @param page
     * @return
     */
    @SelectProvider(type = SQLTemplate.class, method = "selectAndLike")
    public PageList<T> selectAndLike(T object, PageBounds page);


}
