package com.tianwen.dcdp.common;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;


/**
 * 全文检索工具类
 * 
 * @author yx
 * 
 */
public class ArticleSearchUtil {

	private static Log log = LogFactory.getLog(ArticleSearchUtil.class);

	/**
	 * 根据key查找
	 * 
	 * @param indexDir
	 *            文件目录
	 * @param property
	 *            查找字段
	 * @param queryKey
	 *            查找关键字，与字段数组一一对应
	 * @param startIndex
	 *            分 页
	 * @param endIndex
	 *            分 页
	 *  @param list 返回的list对象
	 *  @param property  返回的字段名    ,将需要标红的放在第一位
	 * @return
	 */
	public static int searchByKey(String indexDir, String[] pros,
			String[] queryKey, int startIndex, int endIndex,List<Map<String,Object>> list,String[] property) {
		IndexSearcher searcher;
		TopDocs hits = null;
		try {
			DirectoryReader ireader = DirectoryReader.open(FSDirectory
					.open(new File(indexDir)));
			searcher = new IndexSearcher(ireader);
			Analyzer analyzer = new IKAnalyzer();
			BooleanQuery query=new BooleanQuery();
			for(int i=0;i<pros.length;i++){ //多字段组合查询
				try { 
					QueryParser parser2 = new QueryParser(Version.LUCENE_40,
							pros[i], analyzer);
					Query q2 = null;
					q2 = parser2.parse(queryKey[i]);
					query.add(q2, BooleanClause.Occur.SHOULD); //should并集   must为交集
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			Sort sort=new Sort(new SortField("contentId", SortField.Type.INT,true));//排序，true为降序排列，false为升序
			DuplicateFilter df = new DuplicateFilter("contentId"); //作为是否重复的判断依据field
			df.setKeepMode(DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
			df.setProcessingMode(DuplicateFilter.ProcessingMode.PM_FAST_INVALIDATION);
		    hits = searcher.search(query,df,1000,sort);  
		    ScoreDoc[] scoreDocs = hits.scoreDocs;
			for (int i = startIndex; i < endIndex && i < hits.totalHits; i++) {
				Document document = searcher.doc(scoreDocs[i].doc);
				Map<String,Object> map = new HashMap<String,Object>();
				for (int j=0;j<property.length;j++) {
		        	   map.put(property[j],document.get(property[j]));
		        }
				list.add(map);
			}
			for(int i=0;i<pros.length;i++){
				List<String> key = printAnalysisResult(analyzer, queryKey[i],pros[i]);// 打印分词结果
				ArticleSearchUtil.formatAticles(list,key,pros[i],property[0]); //关键字标红
			}
			ireader.close();
		} catch (IOException e) {
			log.info("printStackTrace in searchByKey  " + e.getMessage());
		} 
		if(hits != null && hits.totalHits >0){
			return hits.totalHits;
		}
		return 0;
	}

	/**
	 * 输出拆分后的关键字
	 * @param analyzer
	 * @param keyWord  关键字
	 * @param property 所查询的字段
	 * @return
	 */
	public static List<String> printAnalysisResult(Analyzer analyzer, String keyWord,String property){
		TokenStream tokenStream;
		List<String> key = new ArrayList<>();
		try {
			tokenStream = analyzer.tokenStream(property,new StringReader(keyWord));
			CharTermAttribute termAttribute = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();// 重新设置
			while (tokenStream.incrementToken()) {
				log.info(new String(termAttribute.buffer(), 0, termAttribute.length()) + "  ");
				key.add(new String(termAttribute.buffer(), 0, termAttribute.length()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * 格式化，标红关键字
	 * @param list 
	 * @param key  拆分后的关键字
	 * @param property  查询字段 redKey 标红字段名
	 * @return
	 */
	public static List<Map<String,Object>> formatAticles(List<Map<String,Object>> list,List<String> key,String property,String redKey) {
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map = list.get(i);
			String title = map.get(redKey).toString();
			for(String queryKey :key){
				if(property.equals(redKey)){ //若是字段为title
					if (title.contains(queryKey)) {
						title = title.replaceAll(queryKey, "<font color=\"red\"><b>"
								+ queryKey + "</b></font>");
					}
				}
			}
			map.put(redKey, title);
			list.set(i, map);
		}
		return list;
	}
}
