package com.dragon.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 一、缓冲区：在NIO中负责数据的存取。缓冲区就是数组，用于存储不同数据类型的数据，所以根据数据类型的不同，Java NIO听了相应类型的缓存区:
 * ByteBuffer、CharBuffer、ShortBuffer、IntBuffer、LongBuffer、FloatBuffer、DoubleBuffer
 * 上述缓存区的管理方法几乎一致，通过allocate(int size)获取指定大小的缓存区
 * 
 * 二、缓存区存取数据的核心方法:
 * put() : 存入数据到缓存区，写数据模式
 * get() : 获取缓存区的数据
 * mark(): 标记，表示记录当前position的位置，可以通过reset()恢复到mark的位置
 * 
 * 三、缓存区四个核心属性
 * capacity : 表示缓存区中最大存储数据的容量，一旦声明不能更改（缓冲区就是数组，数组的容量声明了就不能改变）
 * limit : 表示缓存区中可以操作数据的大小。也就是limit后面的数据不能进行读写
 * position : 表示缓存区中正在操作的位置
 * mark : 标记，用于记录当前position的位置。可以通过reset()恢复到mark的位置。
 * 这4个属性是Buffer这个父类声明的。这四个属性的关系是 : 0 <= mark <= position <= limit <= capacity
 * 
 * 四、直接缓冲区和非直接缓冲区
 * 非直接缓冲区 : 通过allocate()方法分配缓存区，将缓存区建立在JVM的内存中
 * 直接缓存区 : 通过allocateDirect()方法分配直接缓冲区，将缓存区建立在物理内存中
 */
public class BufferTest {
	
	@Test
	public void test3() {
		ByteBuffer allocateDirect = ByteBuffer.allocateDirect(20);
		
		System.out.println(allocateDirect.isDirect());
	}
	
	
	
	
@Test
public void test2() {
	String str = "abcde";
	ByteBuffer buf = ByteBuffer.allocate(1024);
	buf.put(str.getBytes());
	
	buf.flip();
	byte[] dst = new byte[buf.limit()];
	
	buf.get(dst, 0, 2);
	System.out.println(new String(dst, 0 ,2));
	System.out.println(buf.position());
	
	buf.mark(); //标记
	
	buf.get(dst, 2, 2);
	System.out.println(new String(dst, 2 ,2));
	
	System.out.println(buf.position());
	
	//重置，恢复到mark位置
	buf.reset();
	System.out.println(buf.position());
	
	//判断是否还有剩余数据
	if(buf.hasRemaining()){
		System.out.println("还剩下"+buf.remaining() + "个数据");
	}
}

	@Test
	public void test1() {
		String str = "abcde";
		//1、分配一个指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		System.out.println("==> 初始化缓冲区");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//2、利用put()方法存储数据到缓存区
		System.out.println("==> 存入数据之后的缓冲区属性");
		buf.put(str.getBytes());
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//3、切换为读取数据的模式
		Buffer read = buf.flip();
		System.out.println("==> 切换到读取数据的模式");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//4、利用get方法读取缓冲区的数据
		byte[] dst = new byte[buf.limit()];
		buf.get(dst);
		System.out.println("==>读取到的数据"+new String(dst));
		System.out.println("==> 读取数据完毕");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//5、rewind()方法可以重复读取数据
		buf.rewind();
		System.out.println("==> 调用rewind()方法之后的属性");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//6、清空缓存区，但是缓冲区中的数据依然存在，只不过这些数据处于被遗忘状态，也就是说这些数据其实还是能取到的
		buf.clear();
		System.out.println("==> 清空缓冲区之后的属性");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
	}

	
}
