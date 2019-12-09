package com.spring.shop.VO;

import java.util.Date;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class MainVO {
	private String title;
	private String content;
	private Date regDate;
	private Date updDate;
	private int price;
	private String evnet;
	
	
}
