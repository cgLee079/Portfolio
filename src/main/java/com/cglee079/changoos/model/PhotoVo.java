package com.cglee079.changoos.model;

import lombok.Data;

@Data
public class PhotoVo {
	private int seq;
	private String filename;
	private String pathname;
	private String thumbnail;
	private String name;
	private String desc;
	private String date;
	private String time;
	private String location;
	private String tag;
	private String device;
	private int likeCnt;
	private boolean like;

}
