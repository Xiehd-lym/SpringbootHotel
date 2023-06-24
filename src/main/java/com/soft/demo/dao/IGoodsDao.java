package com.soft.demo.dao;

import java.util.List;
import com.soft.demo.domain.Goods;

public interface IGoodsDao {

	public abstract int addGoods(Goods goods);

	public abstract int delGoods(String goods_id);

	public abstract int delGoodss(String[] goods_ids);

	public abstract int updateGoods(Goods goods);

	public abstract Goods getGoods(Goods goods);

	public abstract List<Goods>  listGoodss(Goods goods);

	public abstract int listGoodssCount(Goods goods);

	public abstract List<Goods>  listGoodssTop(Goods goods);
}
