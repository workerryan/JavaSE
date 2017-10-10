package com.dragon.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 一、通道(Channel) : 用于源节点和目标节点的链接。在JAVA NIO中负责缓冲区中数据的传输。
 *     Channel本身不存储数据，因此需要配合缓冲区进行传输。
 * 二、通道的主要实现类:
 *     java.nio.channels.Channel 接口 :
 *         |-- FileChannel    : 本地文件通道
 *         |-- SocketChannel  : socket通道
 *         |-- ServerSocketChannel  : ServerSocket通道
 *         |-- DatagramChannel  : UDP通道
 * 三、获取通道
 *  1、Java针对支持通道的类提供了getChannel()方法 ：
 *      本地IO:
 *      FileInputStream / FileOutputStream
 *      RandomAccessFile
 *      网络IO:
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *  2、在JDK1.7中的NIO2针对各个通道提供了静态方法open()
 *  3、在JDK1.7中的NIO2的Files工具类的newByteChannel()方法  
 * 四、通道之间的数据传输
 *     transferFrom()
 *     transferTo()  
 *     
 * 五、分散(Scatter)与聚集(Gather)
 *     分散读取(Scattering Reads) : 将通过中的数据分散到多个缓冲区中
 *     聚集写入(Gathering Writers) : 将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集(Charset)
 *     编码 : 字符串 -> 字节数组
 *     解码 : 字符数组 -> 字节串
 * 
 * 
 * @author wanglei
 *
 */
public class ChannelTest {
	
	@Test
	public void test5() throws IOException {
		Charset cs = Charset.forName("GBK");
		
		//通过字符集获取编码器
		CharsetEncoder ce = cs.newEncoder();
		//通过字符集获取解码器
		CharsetDecoder cd = cs.newDecoder();
		
		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("三石哥威武");
		cBuf.flip();
		
		//编码
		ByteBuffer byteBuffer = ce.encode(cBuf);
//		for(int i = 0; i<12; i++) {
//			System.out.println(byteBuffer.get());
//		}
		
		//解码
		byteBuffer.flip();
		CharBuffer charBuffer = cd.decode(byteBuffer);
		System.out.println(charBuffer.toString());
	}
	
	
	@Test
	public void test4() throws IOException {
		RandomAccessFile raf1 = new RandomAccessFile("/Users/wanglei/Downloads/work/ee/1.txt", "rw");
		
		//1、获取通道
		FileChannel channel1 = raf1.getChannel();
		
		//2、分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(200);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		//3、分散读取
		ByteBuffer[] bufs = {buf1, buf2};
		channel1.read(bufs); //channel读取的数据是依次读取，写满第一个再写第二个。
		
		for(ByteBuffer byteBuffer : bufs) {
			byteBuffer.flip();
		}
		
		System.out.println(new String(bufs[0].array(), 0 , bufs[0].limit()));
		System.out.println("------");
		System.out.println(new String(bufs[1].array(), 0 , bufs[0].limit()));
		
		
		RandomAccessFile raf2 = new RandomAccessFile("/Users/wanglei/Downloads/work/ee/2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(bufs);
	}
	
	
	
	//通道之间的数据传输(直接缓冲区)
	@Test
	public void test3() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("/Users/wanglei/Downloads/work/ee/1.jpeg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("/Users/wanglei/Downloads/work/ee", "ribi2.jpeg"), 
				StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE_NEW); 
				
//		inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		
		inChannel.close();
		outChannel.close();
	}
	
	
	//利用直接缓冲区完成文件的复制（内存映射文件）
	@Test
	public void test2() throws IOException {
		//Paths.get()用于指定要读取的路径,可以拼接多个路径，OpenOption参数用于指定要操作的模式，这里是读模式
		FileChannel inChannel = FileChannel.open(Paths.get("/Users/wanglei/Downloads/work/ee/1.jpeg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("/Users/wanglei/Downloads/work/ee", "ribi.jpeg"), 
				StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE_NEW); 
			    //StandardOpenOption.CREATE_NEW是不存在就创建，存在就报错
		        //StandardOpenOption.CREATE是不存在就创建，存在就覆盖
		
		//内存映射文件
	    MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());	
	    MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
	    
	    //直接对缓冲区进行数据的读写操作
	    byte[] dst = new byte[inMappedBuf.limit()];
	    inMappedBuf.get(dst);
	    outMappedBuf.put(dst);
	    
	    inChannel.close();
	    outChannel.close();
	}
	
	
	
	//1、利用通道完成文件的复制(非直接缓冲区)
	@Test
	public void test1() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inChannel = null, outChannel = null;
		
		
		try {
			fis = new FileInputStream("/Users/wanglei/Downloads/work/ee/1.jpeg");
			fos = new FileOutputStream("/Users/wanglei/Downloads/work/ee/2.jpeg");
			//1、获取通道
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			//2、分配指定大小的缓冲区(通道无法存储数据，所以借助缓冲区)
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//3、将通道中的数据写入缓冲区
			while(inChannel.read(buf) != -1) {
				buf.flip();  //切换为读取数据的模式
				//4、将缓冲区的数据写入通道中
				outChannel.write(buf);
				buf.clear(); //清空缓冲区
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(outChannel != null) {
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inChannel != null) {
				try {
					inChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
