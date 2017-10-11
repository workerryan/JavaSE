package com.dragon.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

/**
 * 非阻塞式NIO
 * 要使用非NIO完成网络通信也需要三个核心：
 * 1、通道(Channel) : 负责链接
 *     java.nio.channels.Channel 接口
 *         |- SelectableChannel 抽象类
 *             |- SocketChannel        连接到TCP网络套接字的通道。
 *             |- ServerSocketChannel  可以监听新进来的TCP连接的通道
 *             |- DatagramChannel      能收发UDP包的通道
 *             |- Pipe.SinkChannel
 *             |- Pipe.SourceChannel
 * 2、缓冲区(Buffer) : 负责数据的存取
 * 3、选择器(Selector) : 是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况
 */
public class NonBlockingNIOTest {
	@Test
	public void client() throws IOException{
		//1、获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		//2、切换为非阻塞模式
		sChannel.configureBlocking(false);
		//3、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		//4、发送数据给服务端
		buf.put(new Date().toString().getBytes());
		buf.flip();
		sChannel.write(buf);
		buf.clear();
		//5、关闭通道
		sChannel.close();
	}

	/**
	 * 对于阻塞IO，server端一开始就等待链接，而NIO，是通过选择器来控制的，当选择器准备好后，才开始等待链接
	 * @throws IOException
	 */
	@Test
	public void server() throws IOException{
		//1、获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		//2、切换为非阻塞模式
		ssChannel.configureBlocking(false);
		//3、绑定链接端口号
		ssChannel.bind(new InetSocketAddress(9898));
		//4、获取选择器。选择器用于监控通道
		Selector selector = Selector.open();
		//5、将通道注册到选择器上，并且指定监听事件
		// 将通道注册到选择器上的时候会有一个选择键，这个选择键的作用就是选择器监控通道的什么状态。
		// 选择键的取值有 :
		// SelectionKey.OP_ACCEPT  监控通道的接收状态
		// SelectionKey.OP_CONNECT 监控通道的链接状态
		// SelectionKey.OP_READ    监控通道的读状态
		// SelectionKey.OP_WRITE   监控通道的写状态
		// 若注册时需要监听一个以上的状态，可以通过位或进行链接
		// int ops = SelectionKey.OP_CONNECT | SelectionKey.OP_ACCEPT
		// 这里是服务端接收客户端发送的消息，所以选择键选用SelectionKey.OP_ACCEPT
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		//6、轮询式的获取选择器上已经“准备就绪”的事件
		while(selector.select() > 0) {
			//7、 获取当前选择器中所有注册的选择键（已就绪的监听事件）
			Iterator<SelectionKey> iterator = selector.selectedKeys() //包括了选择器的所有注册事件
				.iterator(); 
			while(iterator.hasNext()) {
				//8、获取准备“就绪”的事件
				SelectionKey sk = iterator.next();
				//9、判断具体是什么事件准备就绪
				if(sk.isAcceptable()) {
					//10、获取接收“就绪”就获取客户端链接
					SocketChannel sChannel = ssChannel.accept();
					//11、切换为非阻塞模式
					sChannel.configureBlocking(false);
					//12、将该通道注册到选择器上
					sChannel.register(selector, SelectionKey.OP_READ);
				}else if(sk.isReadable()) {
					//13、获取当前选择器上读就绪状态的通道
					SocketChannel sChannel = (SocketChannel)sk.channel();
					//14、读取数据
					ByteBuffer buf = ByteBuffer.allocate(1024);
					
					int len = 0;
					while((len = sChannel.read(buf)) > 0) {
						buf.flip();
						System.out.println(new String(buf.array(), 0 ,len));
						buf.clear();
					}
				}
				//15、取消选择键（SelectionKey）
				iterator.remove();
			}
		}
	}
}
