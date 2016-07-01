package com.tianwen.dcdp.common;

import java.util.HashMap;
import java.util.Map;

import com.tianwen.dcdp.pojo.Page;


/**   
 *    
 * 类名：ResponseResult   
 * 类描述：   controller与web层的通讯接口公共的返回值类型
 * 修改备注：   
 * @version 1.0.0   
 *    
 */
public class ResponseResult
{
    
	/**
     * 结果集
     */
    private Map<String, Object> resultMap = new HashMap<String, Object>(5);
    
    
    /**
     * 设置自定义返回值
     * @param key 客户端自定义键
     * @param object 值，自定义键的值
     */
    public void setReturnObject(String key, Object object)
    {
        this.resultMap.put(key, object);
    }
    
    /**
     * 获取自定义返回值
     * @param key 自定义键
     * @return 自定义键对应的值
     */
    public Object getReturnObject(String key)
    {
        return this.resultMap.get(key);
    }
    
    /**
     * 设置执行成功标志
     */
    private void setSuccess()
    {
        this.resultMap.put("result", Constants.SUCCESS);
        this.resultMap.put("msg", Constants.OPERATE_SUCCESS_MSG);
    }
    
    /**
     * 设置执行失败标志
     * @param result
     * @param msg
     */
    private void setFailure(String result, String msg) {
    	this.resultMap.put("result", result);
        this.resultMap.put("msg", msg);
    }
    /**
     * 設置分頁信息
     * @param page
     */
    public void setPageInfo(Page page){
    	this.resultMap.put("pages", page);
    }
    
    public Page getPageInfo(){
    	return (Page)this.resultMap.get("pages");
    }
    /**
     * 获取最后返回值
     * @return  结果集， Map数据类型-->JSON
     */
    public Map<String, Object> returnSuccess()
    {
    	setSuccess();
        return this.resultMap;
    }
    
    public Map<String, Object> returnNeedParams() {
    	setFailure(Constants.NULL_PARM, Constants.NULL_PARM_MSG);
    	return this.resultMap;
    }
    
    public Map<String, Object> returnError(String result, String msg)
    {
    	setFailure(result, msg);
        return this.resultMap;
    }
}
