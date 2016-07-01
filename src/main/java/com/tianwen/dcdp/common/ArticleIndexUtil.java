package com.tianwen.dcdp.common;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;  
import org.apache.lucene.document.Field;  
import org.apache.lucene.index.IndexWriter;  
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory; 
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 创建索引工具类
 * @author yx
 *
 */
public class ArticleIndexUtil {
    private static Log log = LogFactory.getLog(ArticleIndexUtil.class);
   /**
    * 创建索引文件
    * @param indexDir 目标文件夹
    * @param article 循环遍历对象
    */
    @SuppressWarnings("deprecation")
	public static void createArticleIndex(String indexDir,Map<String,Object> map){
        Document doc1 = new Document(); 
        for (Entry<String, Object> entry : map.entrySet()) {
        	String value = "";
        	if(entry!=null && entry.getValue()!=null){
        		value = entry.getValue().toString();
        	}
        	doc1.add(new Field(entry.getKey(), value,Field.Store.YES,Field.Index.ANALYZED));
        }
        IndexWriter writer;
        try {
        	Directory dir = FSDirectory.open(new File(indexDir)); 
        	Analyzer analyzer = new IKAnalyzer();
        	IndexWriterConfig iwconf = new IndexWriterConfig(Version.LUCENE_40, analyzer);  
        	
        	iwconf.setMaxBufferedDocs(5000);
        	writer = new IndexWriter(dir, iwconf);
            writer.addDocument(doc1);
            writer.forceMerge(1);
            writer.close(); 
        } catch (IOException e) {
            log.info(e.getMessage());
        } 
      
    }
    

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean 
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    /**
     * 判断该文件夹是否为空
     * @param dir
     * @return
     */
    public static boolean isEmptyDir(File dir){
    	 if (dir.isDirectory()) {
    		 String[] children = dir.list();
    		 if(children.length>0){
    			return true; 
    		 }
    	 }
    	 return false;
    }
   
}
 
