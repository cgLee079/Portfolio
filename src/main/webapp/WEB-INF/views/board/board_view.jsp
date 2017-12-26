<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board-view-basic.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/editor-contents-resizer.js"></script>
<script>
var page = '${page}';

function boardList(){
	if(!page){
		page = 1;
	}
	window.location.href = getContextPath() + "/board#" + page;
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
		window.location.href = getContextPath() + "/board/view?seq=" + seq +"&page=" + page;
	} else {
		alert("글이 없습니다.");
	}
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
				<a class="btn" onclick="boardView('${beforeBoard.seq}')">이전글</a>
				<a class="btn" onclick="boardView('${afterBoard.seq}')">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div style="height : 1px; background: #CCC; margin: 0.5rem 0rem" ></div>
					<div class="board-info">
						<a>${board.sect}</a>
						<a>${board.date}</a>
						<a>조회수 ${board.hits}</a>
					</div>
				</div>

				<div class="board-contents editor-contents">${board.contents}</div>
			</div>
			
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
			   <c:param name = "perPgLine" value = "10" />
			   <c:param name = "boardType" value = "BOARD" />
			   <c:param name = "boardSeq" value = "${board.seq}" />
			   <c:param name = "comtCnt" value = "${comtCnt}" />
			</c:import>
		</div>	
	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
		<c:if test="${!empty beforeItem}">
			<div class="btn btn-item-before h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
		
		<c:if test="${!empty afterItem}">
			<div class="btn btn-item-next" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
	</div>
</body>
</html>