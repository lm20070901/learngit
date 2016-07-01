package com.tianwen.collector.pojo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tianwen.dcdp.pojo.CrawlerConfig;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public abstract class CommonCrawler<T> extends BreadthCrawler {
	private static Logger logger = LoggerFactory.getLogger(CommonCrawler.class);
	// 配置信息
	protected CrawlerConfig config;
	// 收集的数据
	protected List<T> collectData = new ArrayList<T>();
	// 字段的配置信息
	protected String fieldConfig;
	// 保存异步请求的数据
	protected String reponseText;
	private HashMap<String, String> responseMap = new HashMap<String,String>();
	// 错误信息记录
	protected Set<String> errorInfo = new HashSet<String>();
	//记录空置率信息
	public Hashtable<String ,Long> statistics = new Hashtable<String,Long>();
	// 代理对象
	protected Proxys proxys;
	protected String collectTime;//收集数据的时间
	public Set<String> getErrorInfo() {
		return errorInfo;
	}
	private String url;
	private final static String CRAWLERPATH = "temp";
	private final static Boolean AUTOPARSE = true;

	public List<T> getCollectData() {
		return collectData;
	}

	public CommonCrawler(CrawlerConfig config,String collectTime) {
		super(CRAWLERPATH+"/"+config.getId(), AUTOPARSE);
		this.config = config;
		this.collectTime = collectTime;
		this.fieldConfig = replaceHtml(config.getFieldConfig());
		// 初始化代理
		String isUseAgent = config.getIsUseAgent() == null ? "" : config.getIsUseAgent();
		if (isUseAgent.equals("true")) {
			initProxys();
		}
	}
	//初始化代理
	private void initProxys()  {
		proxys = new Proxys();
		for (int n = 1; n <= 10; n++) {
			Connection conn = Jsoup.connect("http://www.kuaidaili.com/free/inha/"+n+"/").userAgent( "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
			conn.timeout(5000);
			Elements els;
			try {
				conn.ignoreContentType(true);
				els = conn.get().select("table.table-striped>tbody>tr");
				for (int i = 0; i < els.size(); i++) {
					String ip = els.get(i).select("td").get(0).text();
					int port = Integer.parseInt(els.get(i).select("td").get(1).text());
					proxys.add(ip, port);
				}
			} catch (IOException e) {
				e.printStackTrace();
				this.errorInfo.add("获取代理出错！" + "\n");
			}
			
		}
	}
	//获取格式化的系统时间
	public String getStrOfSystemDate(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	}
	public HttpRequest setRequest(HttpRequest request) {
		String isUseAgent = config.getIsUseAgent() == null ? "" : config.getIsUseAgent();
		String headOptions = config.getHeadOptions() == null ? "" : config.getHeadOptions();
		if (isUseAgent.equals("true")) {
			request.setProxy(proxys.nextRandom());
		}
		//初始化头文件
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		request.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		if (headOptions != null && !headOptions.trim().equals("")) {
			headOptions = "{"+replaceHtml(headOptions)+"}";
			try{
				JSONObject headJson = new JSONObject(headOptions);
				Iterator<String> keys = headJson.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					String value = headJson.getString(key);
					request.addHeader(key, value);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return request;
	}
	/**
	 * 执行爬虫数据数据
	 */
	public void doCollect() throws Exception {
		// 添加种子
		this.addMySeed();
		// 添加规则
		this.addMyRegex();
		// 设置线程数
		int threadNumber = config.getThreadNumber();
		this.setThreads(threadNumber);
		/// 设置爬取的时间间隔
		long collectInterval = config.getCollectInterval();
		this.setVisitInterval(collectInterval);
		// 重新请求的时间间隔
		this.setRetryInterval(1000);
		int depth = config.getDepth();
		this.start(depth);
	}
	/**
	 * 添加种子
	 */
	public void addMySeed() {
		String isPaging = config.getIsPaging() == null ? "" : config.getIsPaging();
		String pagingRules = replaceAmp(config.getPagingRules() == null ? "" : config.getPagingRules());
		String websiteAddress = replaceAmp(config.getWebsiteAdress() == null ? "" : config.getWebsiteAdress());
		if (isPaging.equals("true")) {
			String[] rulesArray =  null;
			if(pagingRules!=null){
				rulesArray = pagingRules.split(";");
			}
			if(rulesArray!=null&&rulesArray.length>0){
				for(String rules:rulesArray){
					if(rules.indexOf("@&maxPage")>-1){
						//TODO  该分支未看懂
						String txForm = rules.substring(rules.indexOf("@&maxPage"),rules.indexOf("$")+1);
						JSONObject tempJson = new JSONObject(fieldConfig).getJSONObject("maxPage");
						Connection conn = Jsoup.connect(config.getWebsiteAdress());
						conn.timeout(5000);
						String html = null;
						try {
							html = conn.ignoreContentType(true).get().html();
						} catch (IOException e) {
							e.printStackTrace();
						}
						int pageNum = Integer.parseInt(getRegularStr(tempJson, html, "maxPage"));
						String[] rulesInfo = rules.split(",");
						if (rulesInfo.length == 3) {
							try {
								String url = rulesInfo[0];
								url = url.replace(txForm, "%s");
								int minnum = pageNum - Integer.parseInt(rulesInfo[1]);
								int maxnum = pageNum - Integer.parseInt(rulesInfo[2]);
								if(minnum>maxnum){
									int temp = minnum;
									minnum = maxnum;
									maxnum = temp;
								}
								this.addSeed(new CrawlDatum(websiteAddress));
								for (int j = minnum; j <= maxnum; j++) {
									this.addSeed(new CrawlDatum(String.format(url, j)));
								}
							} catch (Exception e) {
								this.errorInfo.add("分页规则配置有误,无法转换为整数！\n");
								return;
							}
						} else {
							this.errorInfo.add("分页规则配置有误！\n");
							return;
						}
						
					}else if(rules.indexOf("@Date")>-1){
						String dateForm = rules.substring(rules.indexOf("@Date")+6,rules.indexOf("$")-1);
						String oldStr = rules.substring(rules.indexOf("@Date"),rules.indexOf("$")+1);
						Calendar cal = Calendar.getInstance();
						int year = cal.get(Calendar.YEAR);
						int month = cal.get(Calendar.MONTH);
						int day = cal.get(Calendar.DAY_OF_MONTH);
						for(int i=0;i<=30;i++){
							cal.clear();
							cal.set(Calendar.YEAR,year);
							cal.set(Calendar.MONTH,month);
							cal.set(Calendar.DAY_OF_MONTH,day-i);
							String newStr = new SimpleDateFormat(dateForm).format(cal.getTime());
							String newRules = rules.replace(oldStr, newStr);
							//System.err.println(newRules);
							makeSeed(newRules,websiteAddress);
						}
					}else if(rules.indexOf("search_field=4")>-1){
						String oldStr = "search_field=4";
						Calendar cal = Calendar.getInstance();
						String nowTimeStr = new SimpleDateFormat("yyy-MM-dd").format(cal.getTime()); 
						int year = cal.get(Calendar.YEAR);
						int month = cal.get(Calendar.MONTH);
						int day = cal.get(Calendar.DAY_OF_MONTH);
						cal.clear();
						cal.set(Calendar.YEAR,year);
						cal.set(Calendar.MONTH,month);
						cal.set(Calendar.DAY_OF_MONTH,day-30);
						String oldTimeStr = new SimpleDateFormat("yyy-MM-dd").format(cal.getTime());
						String newStr = "search_field=4&post_time=5&sort=0&date="+oldTimeStr+"_"+nowTimeStr;
						rules = rules.replace(oldStr, newStr);
						makeSeed(rules,websiteAddress);
					}else{
						makeSeed(rules,websiteAddress);
					}
				}
			}

		} else {
			String [] addresses = websiteAddress.split(";");
			for(String address : addresses){
				this.addSeed(new CrawlDatum(address));
			}
			
		}
	}
	/**
	 * 生成分页的种子
	 * @param rules
	 * @param websiteAddress
	 */
	private void makeSeed(String rules,String websiteAddress){
		String[] rulesInfo = rules.split(",");
		if (rulesInfo.length == 3) {
			try {
				String url = rulesInfo[0];
				url = url.replace("#", "%s");
				int minnum = Integer.parseInt(rulesInfo[1]);
				int maxnum = Integer.parseInt(rulesInfo[2]);
				this.addSeed(new CrawlDatum(websiteAddress));
				for (int j = minnum; j <= maxnum; j++) {
					this.addSeed(new CrawlDatum(String.format(url, j)));
				}
			} catch (Exception e) {
				this.errorInfo.add("分页规则配置有误,无法转换为整数！\n");
				return;
			}
		} else {
			this.errorInfo.add("分页规则配置有误！\n");
			return;
		}
	}
	/**
	 * 添加规则
	 */
	public void addMyRegex() {
		String filterRegular = replaceAmp(config.getFilterRegular() == null ? "" : config.getFilterRegular());
		if (!filterRegular.trim().equals("")) {
			String[] urls = filterRegular.split(";");
			for (int i = 0; i < urls.length; i++) {
				this.addRegex(urls[i]);
			}
		}
	}

	/**
	 * 获取元素信息
	 * @param page
	 * @param fieldConfig 字段配置信息
	 * @param field 字段名称
	 * @return
	 */
	public String getField(Page page, String field) {
		String fieldText = "";
		if (fieldConfig != null && !fieldConfig.trim().equals("")) {
			JSONObject tempJson = null;
			// 获取某字段的配置
			try {
				tempJson = new JSONObject(fieldConfig).getJSONObject(field);
			} catch (Exception e) {
				//e.printStackTrace();
				return null;
			}
			try {
				String jsoupStr = tempJson.getString("jsoup");
				String regularstr = tempJson.getString("regular");
				if(jsoupStr.trim().equals("")){
					return regularstr;
				}
			} catch (Exception e) {
				this.errorInfo.add(page.getUrl()+ ":解析字段出错,读取" + field + "的jsoup规则出错！" + "\n");
			}
			fieldText = getJsoupStr(tempJson,field,page,null);
			fieldText = getRegularStr(tempJson,fieldText,field);
		}
		return fieldText;
	}
	/**
	 * 获取块下面的元素
	 * @param page
	 * @param el
	 * @param field
	 * @return
	 */
	public String getField(Page page, Element el, String field) {
		String fieldText = "";
		if (fieldConfig != null && !fieldConfig.trim().equals("")) {
			JSONObject tempJson = null;
			// 获取某字段的配置
			try {
				tempJson = new JSONObject(fieldConfig).getJSONObject(field);
			} catch (Exception e) {
				return null;
			}
			fieldText = getJsoupStr(tempJson,field,page,el);
			fieldText = getRegularStr(tempJson,fieldText,field);
		}
		return fieldText;
	}
	/**
	 * 获取jsoup规则的字符串
	 * @param tempJson
	 * @param field
	 * @param page
	 * @param el
	 * @return
	 */
	protected String getJsoupStr(JSONObject tempJson,String field,Page page,Element el){
		this.url = page.getUrl();
		String fieldText = "";
		// 获取某字段的jsoup规则
		String jsoupStr = "";
		if (tempJson != null) {
			try {
				jsoupStr = tempJson.getString("jsoup");
			} catch (Exception e) {
				this.errorInfo.add("解析字段出错(2),读取" + field + "的jsoup规则出错！" + "\n");
			}
		}
		String fnStr = "";
		if(jsoupStr != null&&jsoupStr.startsWith("fn")){
			fnStr = jsoupStr.substring(0, jsoupStr.indexOf("|"));
			jsoupStr = jsoupStr.substring(jsoupStr.indexOf("|")+1);
		}
		// 根据jsoup 规则得到元素
		if (jsoupStr != null && jsoupStr.equals("getHtml")) {// 直接获取html
			fieldText = page.getHtml();
		} else if (jsoupStr != null && jsoupStr.startsWith("getUrl")) {
			fieldText = page.getUrl();
			if (jsoupStr.indexOf("@encode") > -1) {
				try {
					fieldText = java.net.URLEncoder.encode(fieldText, "UTF-8");
				} catch (UnsupportedEncodingException e) {}
			}
		}else if (jsoupStr.startsWith("&ajax")) {// 异步请求
			String url = this.getField(page, jsoupStr.substring(1)+".url");
			fieldText = getAjaxData(page, url);
		} else if (jsoupStr.startsWith("#")) {
			fieldText = jsoupStr.substring(1);
		} else if(jsoupStr.startsWith("&")){
			try{fieldText = page.getMetaData(jsoupStr.substring(1));}catch(Exception e){}; 
		} else if(jsoupStr.trim().equals("")){
			fieldText = "";
		}else {
			try {
				String[] selectStrs = jsoupStr.split(";");
				Elements objs = null;
				Element obj = null;
				boolean flag = true;
				for (int i = 0; i < selectStrs.length; i++) {
					String selectStr = selectStrs[i];
					String select = selectStr.split(",")[0];
					if(el!=null){
						obj = el;
					}
					if (select.equals("attr")) {
						flag = false;
						if (selectStr.split(",").length > 1) {
							if (objs != null) {
								fieldText = objs.attr(selectStr.split(",")[1]);
								break;
							} else if (obj != null) {
								fieldText = obj.attr(selectStr.split(",")[1]);
								break;
							}
						} else {
							return "";
						}
					}else if(select.equals("html")){
						flag = false;
						if (objs != null) {
							fieldText = objs.html();
							break;
						} else if (obj != null) {
							fieldText = obj.html();
							break;
						}
					}
					int index = -1;
					if (selectStr.split(",").length > 1) {
						index = Integer.parseInt(selectStr.split(",")[1]);
					}
					if (objs == null && obj != null) {
						if (index >= 0) {
							obj = obj.select(select).get(index);
							objs = null;
						} else {
							objs = obj.select(select);
							obj = null;
						}
					} else if (objs != null && obj == null) {
						if (index >= 0) {
							obj = objs.select(select).get(index);
							objs = null;
						} else {
							objs = objs.select(select);
							obj = null;
						}
					} else {
						if (index >= 0) {
							if(el==null){
								obj = page.select(select).get(index);
							}else{
								obj = el.select(select).get(index);
							}
							objs = null;
						} else {
							if(el==null){
								objs = page.select(select);
							}else{
								objs = el.select(select);
							}
							obj = null;
						}
					}
				}
				if (flag) {
					if (obj != null) {
						fieldText = obj.text();
					} else if (objs != null) {
						fieldText = objs.text();
					} else {
						fieldText = "";
					}
				}
			} catch (Exception e) {
				this.errorInfo.add("解析字段出错(3),解析" + field + "的jsoup规则出错！" + "\n");
				e.printStackTrace();
			}
		}
		if(!fnStr.equals("")){
			fieldText = disposeFn(fnStr,fieldText);
		}
		return fieldText;
	}
	//获取正则处理过的字符串
	private String getRegularStr(JSONObject tempJson,String fieldText,String field){
		// 获取正则规则
		String regularStr = "";
		try {
			regularStr = tempJson.getString("regular");
		} catch (Exception e) {
			this.errorInfo.add("解析字段出错(4),读取" + field + "的正则规则出错！" + "\n");
		}
		String fnStr = "";
		if(regularStr != null&&regularStr.startsWith("fn")){
			fnStr = regularStr.substring(0, regularStr.indexOf("|"));
			regularStr = regularStr.substring(regularStr.indexOf("|")+1);
		}
		// 正则为空，直接返回fieldText
		if (regularStr == null || regularStr.trim().equals("")) {
			return fieldText;
		}
		try {
			if (!fieldText.equals("")) {
				if (fieldText.startsWith("#")) {
					return fieldText.substring(1);
				}
				String groupStr = tempJson.getString("groupNum");
				if (groupStr.trim().equals(""))
					groupStr = "0";
				String group[] = groupStr.split(",");
				Pattern p = Pattern.compile(regularStr);
				Matcher m = p.matcher(fieldText);
				if (m.find()) {
					fieldText = "";
					for (int i = 0; i < group.length; i++) {
						if (group[i].startsWith("#")) {
							fieldText += group[i].substring(1);
						} else {
							fieldText += m.group(Integer.parseInt(group[i]));
						}
					}
				} else {
					
					this.errorInfo.add(this.url+ ":字段" + field + "未匹配到正则表达式！" + "\n");
					fieldText = "";
				}
			}
		} catch (Exception e) {
			this.errorInfo.add(this.url + ":解析字段出错(5),解析" + field + "正则规则出错！" + "\n");
			fieldText = "";
		}
		if(!fnStr.equals("")){
			fieldText = disposeFn(fnStr,fieldText);
		}
		return fieldText;
	}
	private String getAjaxData(Page page,String url) {
		if (url == null || url.equals("")) {
			System.err.println("url为空！");
			return "";
		}
		String responseText = responseMap.get(url);
		if(responseText!=null){
			return responseText;
		}
		String headInfo = getField(page,"ajax.head");
		String responseStr = "";
		try {
			Connection conn = Jsoup.connect(url);
			if(headInfo!=null||!headInfo.equals("")){
				try{
					JSONObject headJson = new JSONObject(headInfo);
					Iterator<String> keys = headJson.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						String value = headJson.getString(key);
						conn.header(key, value);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			conn.timeout(5000);
			responseStr = conn.ignoreContentType(true).get().text();
			/*if(encode!=null&&!encode.equals("")){
				responseStr = new String(responseStr.getBytes("ISO-8859-1"),encode);
			}*/
			//System.err.println(responseStr);
			responseMap.put(url, responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseStr;
	}
	public void batchAddSeed(Page page,CrawlDatums crawlDatums){
		String fieldText = "";
		boolean isNull = false; 
		if(fieldConfig!=null&&!fieldConfig.trim().equals("")){
			JSONObject tempJson = null;
			//获取某字段的配置
			try {tempJson = new JSONObject(fieldConfig).getJSONObject("addseed");} catch (Exception e) {
				return ;
			}
			fieldText = getJsoupStr(tempJson, "addseed", page, null);
			//获取正则规则
			String regularStr = "";
			try{regularStr = tempJson.getString("regular");}catch(Exception e){
				this.errorInfo.add("解析字段出错(4),读取addseed的正则规则出错！"+"\n");
			}
			if(isNull){
				fieldText =  regularStr;
			}	
			//正则为空，直接返回fieldText
			if(regularStr==null||regularStr.trim().equals("")){
				return ;
			}
			try{
				if(!fieldText.equals("")){
					String groupStr = tempJson.getString("groupNum");
					if(groupStr.trim().equals(""))groupStr = "0";
					String group [] = groupStr.split(",");
					Pattern p = Pattern.compile(regularStr);
					Matcher m = p.matcher(fieldText);
					while(m.find()){
						fieldText = "";
						for(int i=0;i<group.length;i++){
							if(group[i].startsWith("#")){
								fieldText += group[i].substring(1);
							}else{
								fieldText += m.group(Integer.parseInt(group[i]));
							}
						}
						fieldText = fieldText.replace("\\", "");
						crawlDatums.add(fieldText);
					}
				}
			}catch(Exception e){
				this.errorInfo.add("解析字段出错(5),解析addseed正则规则出错！"+"\n");
				fieldText = "";
			}
		}
		return ;
	}
	
	public String replaceHtml(String str) {
		str = str.replace("&amp;", "&").replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\"")
				.replace("&amp;", "&");
		return str;
	}

	public String replaceAmp(String str) {
		str = str.replace("&amp;", "&");
		return str;
	}
	
	@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
		return this.setRequest(new HttpRequest(crawlDatum)).getResponse();
	}
	@Override
	public void afterVisit(Page arg0, CrawlDatums arg1) {
		// TODO Auto-generated method stub
		super.afterVisit(arg0, arg1);
		this.reponseText = "";
	}
	public abstract int saveData(T t);
	
	protected String disposeFn(String fnStr,String fieldText){
		String returnStr = null;
		try{
			String type = fnStr.split(":")[1];
			if(type.startsWith("smallType")){
				String canshu = "";
				if(type.indexOf("(")>-1&&type.indexOf(")")>-1){
					canshu = type.substring(type.indexOf("(")+1, type.indexOf(")"));
				}
				returnStr =getTypeDm(fieldText,canshu);
			}
			return returnStr;
		}catch(Exception e){
			return fieldText;
		}
	}
	private static List<String> CHINA = new ArrayList<String>(){{add("大陆");add("港澳");add("台湾");add("tw");add("地方");add("df");add("china");add("gn");add("时政");add("local");add("politics");add("sz");add("cnews");add("taiwan");add("opinion");add("legal");}};
	private static List<String> WORLD = new ArrayList<String>(){{add("国际");add("world");add("gj");add("华人");add("oversea");}};
	private static List<String> SOCIETY = new ArrayList<String>(){{add("社会");add("society");add("sh");}};
	private static List<String> FINANCE = new ArrayList<String>(){{add("财经");add("fortune");add("finance");add("cj");add("caijing");}};
	private static List<String> MILITARY = new ArrayList<String>(){{add("军事");add("mil");add("js");}};
	//政策分析
	private static List<String> POLICYANALYSIS = new ArrayList<String>(){{add("政策法规");add("政策导向");add("政府之窗");add("法律法规");}};
	//国内市场分析
	private static List<String>  GNSCFX = new ArrayList<String>(){{add("发行•营销");add("产业动态");}};
	//国际市场分析
	private static List<String>  GJSCFX = new ArrayList<String>(){{add("港台海外");add("国际动态");add("环球扫描");}};

	protected static String getTypeDm(String fieldText,String canshu){
		fieldText = fieldText.replace(" ", "").trim();
		if(canshu.equals("cb")){
			if(POLICYANALYSIS.contains(fieldText)){
				return "3";
			}else if(GNSCFX.contains(fieldText)){
				return "4";
			}else if(GJSCFX.contains(fieldText)){
				return "5";
			}else{
				return "1";
			}
		}else{
			if(CHINA.contains(fieldText.trim())){
				return "10101001";
			}else if(WORLD.contains(fieldText.trim())){
				return "10101002";
			}else if(FINANCE.contains(fieldText.trim())){
				return "10101004";
			}else if(SOCIETY.contains(fieldText.trim())){
				return "10101003";
			}else if(MILITARY.contains(fieldText.trim())){
				return "10101005";
			}else{
				return "";
			}
		}
	}
	protected void count(T entity) throws IllegalArgumentException, IllegalAccessException{
		synchronized(this){
		Long  total = statistics.get("total");
		if(total==null){
			total = 0L;
			statistics.put("total", total);
		}
		if(entity!=null){
			total++;
			statistics.put("total", total);
			Class clazz = entity.getClass();
			 //获取字段
			Field[] fields = clazz.getDeclaredFields();
			 //遍历每个元素的字段
			for (int i=0;i<fields.length;i++) {
	            Object objVal = null;
	            fields[i].setAccessible(true);
	            //获取字段的值
	            objVal = fields[i].get(entity);
	            //统计为空的字段
	            if(objVal!=null&&objVal.toString().trim().equals("")){
	            	Long temp = statistics.get(fields[i].getName());
	            	if(temp==null){
	            		temp = 0L;
	            	}
	            	temp++;
	            	statistics.put(fields[i].getName(), temp);
	            }
	        }
		}
		}
	}
	
	protected Elements getBlockElements(Page page,String jsoupStr){
		Elements objs = null;
		Element obj = null;
		try {
			String[] selectStrs = jsoupStr.split(";");
			boolean flag = true;
			for (int i = 0; i < selectStrs.length; i++) {
				String selectStr = selectStrs[i];
				String select = selectStr.split(",")[0];
				int index = -1;
				if (selectStr.split(",").length > 1) {
					index = Integer.parseInt(selectStr.split(",")[1]);
				}
				if (objs == null && obj != null) {
					if (index >= 0) {
						obj = obj.select(select).get(index);
						objs = null;
					} else {
						objs = obj.select(select);
						obj = null;
					}
				} else if (objs != null && obj == null) {
					if (index >= 0) {
						obj = objs.select(select).get(index);
						objs = null;
					} else {
						objs = objs.select(select);
						obj = null;
					}
				} else {
					if (index >= 0) {
						obj = page.select(select).get(index);
						objs = null;
					} else {
						objs = page.select(select);
						obj = null;
					}
				}
			}
		}catch (Exception e) {
				// TODO: handle exception
		}
		return objs;
	}
	public static void main(String[] args) {
		Hashtable<String ,Long> statistics = new Hashtable<String,Long>();
		Long total = total = statistics.get("total");
		if(total==null){
			total = 0L;
		}
	}
}
