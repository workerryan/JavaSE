package com.dragon.javase8.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.Test;

/**
 * 对时区的处理
 * Java8 中加入了对时区的支持，带时区的时间为分别为: ZonedDate、ZonedTime、ZonedDateTime
 * 其中每个时区都对应着 ID，地区ID都为 “{区域}/{城市}”的格式 例如 :Asia/Shanghai 等
 * ZoneId:该类中包含了所有的时区信息 getAvailableZoneIds() : 可以获取所有时区时区信息 of(id) : 用指定的时区信息获取 ZoneId 对象
 * @author wanglei
 *
 */
public class ZoneTest {
	
	@Test
	public void test2() {
		System.out.println(LocalDateTime.now());
		//获取指定时区的本地时间
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of("US/Samoa"));
		System.out.println(ldt);
		
		LocalDateTime ldt2 = LocalDateTime.now();
		//构建一个带时区的时间，但是这个时间还是是本地的时间
		ZonedDateTime zdt = ldt2.atZone(ZoneId.of("US/Samoa"));
		System.out.println(zdt);
		
		LocalDateTime ldt3 = LocalDateTime.now(ZoneId.of("US/Samoa"));
		//构建一个带时区的时间，但是这个时间还是US/Samoa的时间
		ZonedDateTime zdt3 = ldt3.atZone(ZoneId.of("US/Samoa"));
		System.out.println(zdt3);
	}

	/**
	 * 查看java8支持的时区
	 */
	@Test
	public void test1() {
		Set<String> zones = ZoneId.getAvailableZoneIds();
		zones.forEach(System.out :: println);
	}
}
