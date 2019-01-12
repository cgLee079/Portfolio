<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<script src="${pageContext.request.contextPath}/resources/js/project/project-view.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-comment.js"></script>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
		<div class="project">
			<div class="project-head">
				<div class="project-head-bg" style="background-image: url('${pageContext.request.contextPath}${projectThumbDir}${project.thumbnail}"></div>
				<div class="project-head-fg"></div>
				<div class="project-detail">
					<div class="project-subtitle"><c:out value="${project.subtitle}"/></div>
					<div class="project-title"><c:out value="${project.title}"/></div>
					<div class="project-subinfo">
						<c:if test="${!empty project.developer}"> 
							<div class="project-developer">by ${project.developer}</div>
							<div class="colum-border"></div>
						</c:if>
						
						<div>조회수 <c:out value="${project.hits}"/></div>
						
						<c:if test="${!empty project.sourcecode}">
							<div class="colum-border"></div>
							<div class="row-center project-source">
								<div class="git-logo" style="background-image: url('${pageContext.request.contextPath}/resources/image/btn_projectview_source.png')"></div>
								<a target="_blank" href="${project.sourcecode}">SOURCE</a>
							</div>
						</c:if>
					</div>
				</div>
				
				<div class="project-submenu">
					<a class="submenu btn-project-list" onclick="projectList()">목록</a>
					<a class="submenu btn-project-before" onclick="projectView('${beforeProject.seq}')">이전글</a>
					<a class="submenu btn-project-next" onclick="projectView('${afterProject.seq}')">다음글</a>
				</div>
			</div>

			<div class="project-desc">
				<div class="project-content editor-contents">
					<c:out value="${project.contents}" escapeXml="false"/>
					
					<c:if test="${!empty files}">
						<div>첨부파일</div>
						<div class="project-files">
							<c:forEach var="file" items="${files}">
								<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
								<div class="project-file">
									 <a onclick="downloadFile('${projectFileDir}', '${file.pathname}', '${file.filename}')"> 
									 	<c:out value="${file.filename}"/> (<c:out value="${filesize}"/> MB)
									 </a>
								</div>												
							</c:forEach>
						</div>
					</c:if>
				</div>
			
				<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
				   <c:param name = "perPgLine" value = "10" />
				   <c:param name = "boardType" value = "${boardType.PROJECT.val}" />
				   <c:param name = "boardSeq" value = "${project.seq}" />
				   <c:param name = "comtCnt" value = "${project.comtCnt}" />
				</c:import>
			</div>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
	
</body>
</html>