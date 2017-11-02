package com.dragon.concurrent;

/**
 * 模拟原子类的CAS算法
 * @author wanglei
 *
 */
public class CompareAndSwapTest {
	public static void main(String[] args) {
		final CompareAndSwap cas = new CompareAndSwap();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int expectedValue = cas.get(); // 1
					//在进行自己的比较和替换之前，get一次内存值作为期望值
					boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 101));
					System.out.println(b);
				}
			}).start();
		}
	}
}

//该类中是模拟，所以加上了synchronized，肯定和底层不一致
class CompareAndSwap{
	private int value;
	//获取内存值
	public synchronized int get() {
		return value;
	}
	
	//比较
	public synchronized int compareAndSwap(int expectdValue, int newValue) {
		int oldValue = value; //内存值 2
		if(oldValue == expectdValue) { //比较内存值和预估值
			this.value = newValue;    //如果内存值和预估值相等，替换内存值
		}
		return oldValue;  //不管是否成功，都会返回内存值
	}
	
	//设置
	public synchronized boolean compareAndSet(int expectdValue, int newValue) {
		return expectdValue == compareAndSwap(expectdValue, newValue);
	}
}
