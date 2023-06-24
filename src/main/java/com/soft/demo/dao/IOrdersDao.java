package com.soft.demo.dao;

import java.util.List;
import com.soft.demo.domain.Orders;

public interface IOrdersDao {

	public abstract int addOrders(Orders orders);

	public abstract int delOrders(String orders_id);

	public abstract int delOrderss(String[] orders_ids);

	public abstract int updateOrders(Orders orders);

	public abstract Orders getOrders(Orders orders);

	public abstract List<Orders>  listOrderss(Orders orders);

	public abstract int listOrderssCount(Orders orders);

}
