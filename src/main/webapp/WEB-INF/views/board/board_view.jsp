<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/editor-contents-resizer.js"></script>

<script>
var boardPage = "${page}";

function boardList(){
	if(!boardPage){
		boardPage = 1;
	}
	window.location.href = getContextPath() + "/board#" + boardPage;
}

function boardDelete(seq){
	var question = "정말로 삭제 하시겠습니까?";
	if(confirm(question)){
		window.location.href = getContextPath() + "/admin/board/delete.do?seq=" + seq;	
	}
}

function boardModify(seq){
	window.location.href = getContextPath() + "/admin/board/upload?seq=" + seq;		
}

function boardView(seq){
	if (seq){
		window.location.href = getContextPath() + "/board/view?seq=" + seq +"&page=" + boardPage;
	} else {
		alert("글이 없습니다.");
	}
}

function downloadFile(pathNm){
	window.location.assign(getContextPath()	+ "/board/download.do?filename="+ pathNm);
}
</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-board">
			<div class="board-submenu">
				<a class="btn" onclick="boardModify('${board.seq}')">수정</a>
				<a class="btn" onclick="boardDelete('${board.seq}')">삭제</a>
				<a class="btn" onclick="boardList()">목록</a>
				<a class="btn" onclick="boardView('${afterBoard.seq}')">이전글</a>
				<a class="btn" onclick="boardView('${beforeBoard.seq}')">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div class="board-info">
						<div>${board.sect}</div>
						<div class="colum-border"></div>
						<div>${board.date}</div>
						<div class="colum-border"></div>
						<div>조회수 ${board.hits}</div>
					</div>
				</div>

				<c:if test="${!empty files}">
					<div class="board-files">
						<c:forEach var="file" items="${files}">
							<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
							<div class="board-file">
								 <a onclick="downloadFile('${file.pathNm}')">
								 	${file.realNm} (${filesize}MB)
								 </a>
							</div>												
						</c:forEach>
					</div>
				</c:if>
				<div class="board-contents editor-contents">${board.contents}</div>
			</div>
			
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
			   <c:param name = "perPgLine" value = "10" />
			   <c:param name = "boardType" value = "board" />
			   <c:param name = "boardSeq" value = "${board.seq}" />
			   <c:param name = "comtCnt" value = "${comtCnt}" />
			</c:import>
		</div>	
	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
	</div>
</body>
</html>