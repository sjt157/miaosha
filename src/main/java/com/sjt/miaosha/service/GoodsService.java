package com.sjt.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjt.miaosha.dao.GoodsDao;
import com.sjt.miaosha.entity.SeckillGoods;
import com.sjt.miaosha.vo.GoodsVo;

@Service
public class GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;

	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public boolean reduceStock(GoodsVo goods) {
		int res = goodsDao.reduceStock(goods.getId());
		return res > 0;
	}
	
	

	public void resetStock(List<GoodsVo> goodsList) {
		for(GoodsVo goods : goodsList ) {
			SeckillGoods g = new SeckillGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}
}
