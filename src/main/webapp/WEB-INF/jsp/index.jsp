<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="head.jsp"%>
<title>经济日报财富平台后台管理系统</title>
</head>
<body>
<header class="Hui-header cl"> <a class="Hui-logo l" title="经济日报财富平台" href="/">经济日报财富平台</a>
	<nav class="mainnav cl" id="Hui-nav">
		<ul>
			<li class="dropDown dropDown_click"><a href="javascript:;" aria-expanded="true" aria-haspopup="true" data-toggle="dropdown" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>
				<ul class="dropDown-menu radius box-shadow">
					<li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>
					<li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>
					<li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>
					<li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
				</ul>
			</li>
		</ul>
	</nav>
	<ul class="Hui-userbar">
		<li>${admin.roleName}</li>
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A">${admin.userName} <i class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="#">个人信息</a></li>
				<li><a href="<%=request.getContextPath()%>/toLogout">退出</a></li>
			</ul>
		</li>
		<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		<!-- <li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul>
		</li> -->
	</ul>
	<a href="javascript:;" class="Hui-nav-toggle Hui-iconfont" aria-hidden="false">&#xe667;</a> </header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 内容管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<shiro:hasPermission name="article:finance:list">
						<li><a _href="<%=request.getContextPath() %>/article/financeArticleList" data-title="财经资讯" href="javascript:void(0)">财经资讯</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:research:list">
						<li><a _href="<%=request.getContextPath() %>/article/researchArticleList" data-title="研究报告" href="javascript:void(0)">研究报告</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:expert:list">
						<li><a _href="<%=request.getContextPath() %>/article/expertArticleList" data-title="专家观点" href="javascript:void(0)">专家观点</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:video:list">
						<li><a _href="<%=request.getContextPath() %>/article/videoArticleList" data-title="讲座视频" href="javascript:void(0)">讲座视频</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:grab:list">
						<li><a _href="<%=request.getContextPath() %>/articleGrab/grabList" data-title="资讯抓取" href="javascript:void(0)">资讯抓取</a></li>
					</shiro:hasPermission>
					
					<li><a _href="<%=request.getContextPath() %>/crawler/main" data-title="爬虫" href="javascript:void(0)">爬虫</a></li>
					
					<shiro:hasPermission name="society:comment:list">
						<li><a _href="<%=request.getContextPath() %>/article/articleCommentList" data-title="评论管理" href="javascript:void(0)">评论管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:category:list">
						<li><a _href="<%=request.getContextPath() %>/article/newsCategoryList" data-title="栏目管理" href="javascript:void(0)">栏目管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:focusPic:list">
						<li><a _href="<%=request.getContextPath() %>/focusPicture/focusPictureList" data-title="焦点图管理" href="javascript:void(0)">焦点图管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="article:hotArticle:list">
						<li><a _href="<%=request.getContextPath() %>/article/HotNewsList" data-title="热门资讯" href="javascript:void(0)">热门资讯</a></li>
					</shiro:hasPermission>
				</ul>
			</dd>
		</dl>
		<dl id="menu-picture">
			<dt><i class="Hui-iconfont">&#xe6aa;</i> 社交管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<shiro:hasPermission name="society:user:list">
					<li><a _href="<%=request.getContextPath()%>/user/getUserListByMap" data-title="用户管理" href="javascript:void(0)">用户管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="society:content:list">
					<li><a _href="<%=request.getContextPath()%>/content/contentList" data-title="动态管理" href="javascript:void(0)">动态管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="society:comment:list">
					<li><a _href="<%=request.getContextPath()%>/comment/commentList" data-title="评论管理" href="javascript:void(0)">评论管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="society:verified:list">
					<li><a _href="<%=request.getContextPath()%>/verified/verifiedList" data-title="认证管理" href="javascript:void(0)">认证管理</a></li>
					</shiro:hasPermission>
					<!-- delete by jiangyx  2016.05.27 reason 问卷管理   独立成单独模块  -->
					<!-- <li><a _href="picture-list.html" data-title="问卷管理" href="javascript:void(0)">问卷管理</a></li> -->
					
					<shiro:hasPermission name="society:systemInfo:list">
					<li><a _href="<%=request.getContextPath()%>/systemInfo/systemInfoList" data-title="系统消息" href="javascript:void(0)">系统消息</a></li>
					</shiro:hasPermission>
				</ul>
			</dd>
		</dl>
		<!-- add by jiangyx 2016.05.27 问卷管理单独模块 -->
		<dl id="menu-questionnaire"> <!--questionnaire 问卷  -->
			<dt><i class="Hui-iconfont">&#xe633;</i> 问卷管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<shiro:hasPermission name="questionnaire:vote:list">
					<li><a _href="<%=request.getContextPath()%>/vote/voteList?voteType=1" data-title="用户问卷" href="javascript:void(0)">用户问卷</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="questionnaire:vote:list">
					<li><a _href="<%=request.getContextPath()%>/vote/voteList?voteType=0" data-title="系统问卷" href="javascript:void(0)">系统问卷</a></li>
					</shiro:hasPermission>
				</ul>
			</dd>
		</dl>
		
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe633;</i> 问答管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<shiro:hasPermission name="qa:category:list">
					<li><a _href="<%=request.getContextPath()%>/question/categoryList" data-title="分类管理" href="javascript:void(0)">分类管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="qa:question:list">
					<li><a _href="<%=request.getContextPath()%>/question/questionList" data-title="问题管理" href="javascript:void(0)">问题管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="qa:answer:list">
					<li><a _href="<%=request.getContextPath()%>/question/answerList" data-title="评论管理" href="javascript:void(0)">评论管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="qa:hotQuestion:list">
					<li><a _href="<%=request.getContextPath()%>/question/hotQuestionList" data-title="热门问答" href="javascript:void(0)">热门问答</a></li>
					</shiro:hasPermission>
				</ul>
			</dd>
		</dl>
		<dl id="menu-comments">
			<dt><i class="Hui-iconfont">&#xe611;</i> 小组管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				
				<shiro:hasPermission name="groups:group:list">
					<li><a _href="<%=request.getContextPath()%>/groups/groupsList" data-title="小组列表" href="javascript:;">小组列表</a></li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="groupPost:post:list">
					<li><a _href="<%=request.getContextPath()%>/groupPost/postList" data-title="小组帖子" href="javascript:void(0)">小组帖子</a></li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="gpComment:comment:list">
					<li><a _href="<%=request.getContextPath()%>/groupPostComment/commentList" data-title="帖子评论" href="javascript:void(0)">帖子评论</a></li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="groups:HotGroups:list">
					<li><a _href="<%=request.getContextPath()%>/groups/HotGroups" data-title="热门小组" href="javascript:void(0)">热门小组</a></li>
				</shiro:hasPermission>
				</ul>
			</dd>
		</dl>
		<!--  add by jiangyx 项目管理 -->
		<dl id="menu-project">
			<dt><i class="Hui-iconfont">&#xe611;</i> 项目管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				<shiro:hasPermission name="project:publish:list">
					<li><a _href="<%=request.getContextPath()%>/project/getProjectList?isAdmin=true" data-title="项目发布" href="javascript:;">项目发布</a></li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="project:publish:list">
					<li><a _href="<%=request.getContextPath()%>/project/getProjectList?isAdmin=false" data-title="项目审核" href="javascript:void(0)">项目审核</a></li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="project:publish:list">
					<li><a _href="<%=request.getContextPath()%>/project/getProjectList?isRecommend=true" data-title="项目推荐" href="javascript:void(0)">项目推荐</a></li>
				</shiro:hasPermission>
				
				</ul>
			</dd>
		</dl>
		
		<!-- add by yx 2016.06.27      活动管理单独模块 -->
		<dl id="menu-activity"> <!--questionnaire 问卷  -->
			<dt><i class="Hui-iconfont">&#xe633;</i> 活动管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<shiro:hasPermission name="activity:act:list">
					<li><a _href="<%=request.getContextPath()%>/activity/actList" data-title="活动发布" href="javascript:void(0)">活动发布</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="activity:enroll:list">
					<li><a _href="<%=request.getContextPath()%>/actEnroll/enrollList" data-title="活动报名" href="javascript:void(0)">活动报名</a></li>
					</shiro:hasPermission>
				</ul>
			</dd>
		</dl>
		
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<shiro:hasPermission name="system:role:list">
						<li><a _href="<%=request.getContextPath()%>/role/toRoleList" data-title="角色管理" href="javascript:void(0)">角色管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="system:admin:list">
						<li><a _href="<%=request.getContextPath()%>/system/adminList" data-title="管理员管理" href="javascript:void(0)">管理员管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="system:action:list">
						<li><a _href="<%=request.getContextPath()%>/action/actionList" data-title="访问路径管理" href="javascript:void(0)">访问路径管理</a></li>
					</shiro:hasPermission>
					
					<li><a _href="system-base.html" data-title="权限管理" href="javascript:void(0)">权限管理</a></li>
					<shiro:hasPermission name="system:idx:list">
					<li><a _href="<%=request.getContextPath()%>/article/articleIdxSearch" data-title="全文索引" href="javascript:void(0)">全文索引</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="system:pointRule:list">
						<li><a _href="<%=request.getContextPath()%>/point/pointRuleList" data-title="积分规则" href="javascript:void(0)">积分规则</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="system:pageView:list">
						<li><a _href="<%=request.getContextPath()%>/pageView/toPageViewList" data-title="访问量统计" href="javascript:void(0)">访问量统计</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="system:words:list">
						<li><a _href="<%=request.getContextPath() %>/words/wordsList" data-title="敏感词管理" href="javascript:void(0)">敏感词管理</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="report:report:list">
						<li><a _href="<%=request.getContextPath()%>/report/selectPageList" data-title="举报管理" href="javascript:void(0)">举报管理</a></li>
					</shiro:hasPermission>
					
					
						<li><a _href="<%=request.getContextPath()%>/adminLog/logList" data-title="日志管理" href="javascript:void(0)">日志管理</a></li>
					
					
					<shiro:hasPermission name="ipAdr:adress:list">
						<li><a _href="<%=request.getContextPath()%>/ipAdr/selectPageList" data-title="IP管理" href="javascript:void(0)">IP管理</a></li>
					</shiro:hasPermission>
					
					<li><a _href="system-log.html" data-title="系统参数" href="javascript:void(0)">系统参数</a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="welcome.html">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="<%=request.getContextPath()%>/welcome"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript">
/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
</body>
</html>
