package com.cglee079.portfolio.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.service.BoardService;
import com.cglee079.portfolio.util.Formatter;
import com.cglee079.portfolio.util.MyTelegramHandler;

public class BComtNoticeInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MyTelegramHandler telegramHandler;

	@Autowired
	private BoardService boardService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String name = request.getParameter("name");
		String contents = request.getParameter("contents").replaceAll("<br />", "\n");
		String boardSeq = request.getParameter("boardSeq");
		String parentSeq = request.getParameter("parentSeq");
		if (parentSeq == null) {
			BoardVo board = boardService.get(Integer.parseInt(boardSeq));
			String boardTitle = board.getTitle();

			String msg = "#게시판에 댓글이 등록되었습니다.\n";
			msg += "게시글	 : " + boardTitle + "\n";
			msg += "ID : " + name + "\n";
			msg += "시간 : " + Formatter.toDateTime(new Date()) + "\n";
			msg += "내용 :\n";
			msg += contents;

			telegramHandler.sendMessage(msg);
		}
	}

}
