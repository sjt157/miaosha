package com.sjt.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjt.miaosha.entity.OrderInfo;
import com.sjt.miaosha.entity.User;
import com.sjt.miaosha.result.CodeMsg;
import com.sjt.miaosha.result.Result;
import com.sjt.miaosha.service.GoodsService;
import com.sjt.miaosha.service.OrderService;
import com.sjt.miaosha.vo.GoodsVo;
import com.sjt.miaosha.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private GoodsService goodsService;

	@RequestMapping("/detail")
	@ResponseBody
	public Result<OrderDetailVo> info(Model model, User user, @RequestParam("orderId") long orderId){
		if(user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		OrderInfo order = orderService.getOrderById(orderId);
		if(order == null) {
			return Result.error(CodeMsg.ORDER_NOT_EXIST);
		}
		Long goodsId = order.getGoodsId();
		
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		
		OrderDetailVo vo = new OrderDetailVo();
		vo.setGoods(goods);
		vo.setOrder(order);
		return Result.success(vo);
		
	}
	
}
