package com.cglee079.changoos.model;

public class BoardVo {
	private int seq;
	private String type;
	private int sort;
	private String sect;
	private String title;
	private String contents;
	private String date;
	private int hits;
	private int comtCnt;

	@Override
	public String toString() {
		return "BoardVo [seq=" + seq + ", type=" + type + ", sort=" + sort + ", sect=" + sect + ", title=" + title
				+ ", contents=" + contents + ", date=" + date + ", hits=" + hits + "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getSect() {
		return sect;
	}

	public void setSect(String sect) {
		this.sect = sect;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getComtCnt() {
		return comtCnt;
	}

	public void setComtCnt(int comtCnt) {
		this.comtCnt = comtCnt;
	}
}