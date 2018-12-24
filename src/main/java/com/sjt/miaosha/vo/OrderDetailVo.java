package com.sjt.miaosha.vo;

import com.sjt.miaosha.entity.Goods;
import com.sjt.miaosha.entity.OrderInfo;

public class OrderDetailVo {
	private Goods goods;
	private OrderInfo order;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}

}
