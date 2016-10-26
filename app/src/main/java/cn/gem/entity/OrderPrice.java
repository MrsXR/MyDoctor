package cn.gem.entity;

public class OrderPrice {

	private Integer orderId;
	private float price;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public OrderPrice(Integer orderId, float price) {
		super();
		this.orderId = orderId;
		this.price = price;
	}
	
}
