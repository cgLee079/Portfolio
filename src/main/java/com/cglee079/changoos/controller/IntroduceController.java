package com.cglee079.changoos.controller;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.VisitMsgVo;
import com.cglee079.changoos.service.VisitMsgService;
import com.cglee079.changoos.util.Formatter;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IntroduceController {

	@Autowired
	private VisitMsgService visitMsgService;

	@RequestMapping(value = "/introduce")
	public String introduce() {
		return "introduce/introduce_view";
	}

	/**방명록 등록 **/
	@ResponseBody
	@RequestMapping(value = "/introduce/remain_message.do")
	public String doRemainMessage(String contents) {
		VisitMsgVo visitMsg = new VisitMsgVo();
		visitMsg.setContents(contents);
		visitMsg.setDate(Formatter.toDateTime(new Date()));

		boolean result = visitMsgService.insert(visitMsg);

		JSONObject re = new JSONObject();
		re.put("result", result);
		return re.toString();
	}

}