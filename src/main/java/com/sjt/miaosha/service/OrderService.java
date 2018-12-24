package com.sjt.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjt.miaosha.dao.OrderDao;
import com.sjt.miaosha.entity.OrderInfo;
import com.sjt.miaosha.entity.SeckillOrder;
import com.sjt.miaosha.entity.User;
import com.sjt.miaosha.redis.OrderKey;
import com.sjt.miaosha.redis.RedisService;
import com.sjt.miaosha.vo.GoodsVo;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private RedisService redisService;

	public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, long goodsId) {
		//return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
		return redisService.get(OrderKey.getSeckillOrderByUidGid, ""+userId+"_"+goodsId, SeckillOrder.class);
	}

	public OrderInfo createOrder(User user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSeckillPrice());
		orderInfo.setOrderChannel(1);//1在线下单
		orderInfo.setStatus(0);//0未支付
		orderInfo.setUserId(user.getId());
		
		orderDao.insert(orderInfo);
		
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setGoodsId(goods.getId());
		seckillOrder.setOrderId(orderInfo.getId());
		seckillOrder.setUserId(user.getId());
		
		orderDao.insertSeckillOrder(seckillOrder);
		
		//放入 Redis
		redisService.set(OrderKey.getSeckillOrderByUidGid, ""+user.getId()+"_"+goods.getId(), seckillOrder);

		return orderInfo;
	}

	public OrderInfo getOrderById(long orderId) {
		return orderDao.getOrderById(orderId);
	}

	public void deleteOrders() {
		orderDao.deleteOrders();
		orderDao.deleteSeckillOrders();
	}


}
