package com.dragon.javase8.streamapi.model;

/**
 * 交易类
 * @author wanglei
 *
 */
public class Transaction {
	private Trader trade;
	private int year;
	private int value;
	
	public Transaction() {}
	
	public Transaction(Trader trade, int year, int value) {
		super();
		this.trade = trade;
		this.year = year;
		this.value = value;
	}
	
	public Trader getTrade() {
		return trade;
	}
	public void setTrade(Trader trade) {
		this.trade = trade;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Transaction [trade=" + trade + ", year=" + year + ", value=" + value + "]";
	}
}
