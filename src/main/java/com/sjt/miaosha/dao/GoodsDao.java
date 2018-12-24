package com.sjt.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sjt.miaosha.entity.SeckillGoods;
import com.sjt.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {

	@Select("select g.*,sg.seckill_price,sg.stock_count,sg.start_date,sg.end_date "
			+ "from s_goods sg left join goods g on sg.goods_id=g.id")
	public List<GoodsVo> listGoodsVo();

	@Select("select g.*,sg.seckill_price,sg.stock_count,sg.start_date,sg.end_date "
			+ "from s_goods sg left join goods g on sg.goods_id=g.id where g.id = #{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	//此处需要乐观锁吧需要？？？？？？ 我觉d
	@Update("update s_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
	public int reduceStock(@Param("goodsId") long goodsId);

	@Update("update s_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
	public void resetStock(SeckillGoods g);
	
	
}
