package com.sjt.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjt.miaosha.entity.SeckillOrder;
import com.sjt.miaosha.entity.User;
import com.sjt.miaosha.redis.RedisService;
import com.sjt.miaosha.service.GoodsService;
import com.sjt.miaosha.service.OrderService;
import com.sjt.miaosha.service.SeckillService;
import com.sjt.miaosha.vo.GoodsVo;

@Service
public class MQReceiver {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private SeckillService seckillService;


	private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

	@RabbitListener(queues = MQConfig.SECKILL_QUEUE)
	public void receive(String message) {
		log.info("receive message : " + message);
		SeckillMessage sm = RedisService.stringToBean(message, SeckillMessage.class);
		User user = sm.getUser();
		long goodsId = sm.getGoodsId();

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		Integer stock = goods.getStockCount();
		if (stock <= 0) {
			return;
		}
		// 判断是否已经秒杀到了
		SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			return;
		}
		// 减库存，下订单，写入秒杀订单
		seckillService.seckill(user, goods);
	}

	// @RabbitListener(queues=MQConfig.QUEUE)
	// public void receive(String message) {
	// log.info("-----------receive message:" + message);
	// }
	//
	// @RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
	// public void receiveTopic1(String message) {
	// log.info("-----------topic queue1 message:" + message);
	// }
	//
	// @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
	// public void receiveTopic2(String message) {
	// log.info("-----------topic queue2 message:" + message);
	// }
	//
	// @RabbitListener(queues=MQConfig.HEADER_QUEUE)
	// public void receiveHeaderQueue(byte[] message) {
	// log.info("-----------header queue message:" + new String(message));
	// }

}
