package com.dragon.javase8.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.junit.Test;

/**
 * 1、LocalDate、LocalTime、LocalDateTime 类的实例是不可变的对象，
 *     分别表示使用 ISO-8601日历系统的日期、时间、日期和时间。
 *     它们提供了简单的日期或时间，并不包含当前的时间信息。也不包含与时区相关的信息。
 *     这3个对象的使用方式是一样的，只不过LocalDate表示日期，LocalTime表示时间、LocalDateTime包含日期和时间
 *     这3个对象都是便于人进行读写的
 *     这3个对象常用的方法(用法都一致)有:
 *     1.1、now() - 静态方法，根据当前时间创建对象 LocalDateTime localDateTime = LocalDateTime.now();
 *     1.2、of() - 静态方法，根据指定日期/时间创建对象 LocalDateTime localDateTime = LocalDateTime.of(2016, 10, 26, 12, 10, 55);
 *     1.3、plusDays, plusWeeks, plusMonths, plusYears - 向当前 LocalDate 对象添加几天、几周、几个月、几年
 *     1.4、minusDays, minusWeeks, minusMonths, minusYears - 从当前 LocalDate 对象减去几天、几周、几个月、几年
 *     1.5、plus, minus - 添加或减少一个 Duration 或 Period
 *     1.6、withDayOfMonth, withDayOfYear, withMonth, withYear - 将月份天数、年份天数、月份、年份修改为指定的值并返回新的 LocalDate 对象
 *     1.7、getDayOfMonth - 获得月份天数(1-31)
 *     1.8、getDayOfYear - 获得年份天数(1-366)
 *     1.9、getDayOfWeek - 获得星期几(返回一个 DayOfWeek 枚举值)
 *     1.11、getMonth - 获得月份, 返回一个 Month 枚举值
 *     1.12、getMonthValue - 获得月份(1-12)
 *     1.13、getYear - 获得年份
 *     1.14、until - 获得两个日期之间的 Period 对象， 或者指定 ChronoUnits 的数字
 *     1.15、isBefore, isAfter - 比较两个 LocalDate
 *     1.16、isLeapYear - 判断是否是闰年
 *     
 * 2、Instant 时间戳(以Unix元年: 1970年1月1日 00:00:00 到某个时间之间的毫秒值)
 * 
 * 3、时间(日期)之间的计算
 *     Duration : 计算2个“时间”之间的间隔
 *     Period : 计算2个“日期”之间的间隔
 *     
 * 4、TemporalAdjuster : 时间校正器。有时我们可能需要获取例如:将日期调整到“下个周日”等操作。
 *     TemporalAdjuster是一个接口，JDK8提供了TemporalAdjusters，该类通过静态方法提供了大量的常用 TemporalAdjuster 的实现。
 *     
 * 5、java.time.format.DateTimeFormatter : 用于格式化时间/日期
 * 
 * @author wanglei
 *
 */
public class LocalDateTimeTest {
	
	/**
	 * Date和LocalDate的转换
	 */
	@Test
	public void test6() {
		//1、date转换为LocalDateTime
		Date date = new Date();
		Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDateTime localDate = instant.atZone(zoneId).toLocalDateTime();
        System.out.println("Date = " + date);
        System.out.println("LocalDate = " + localDate + "\n");
		
        //2、LocalDateTime 转换为Date
        ZoneId zoneId2 = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime atZone = localDateTime.atZone(zoneId2);
        
        Date date2 = Date.from(atZone.toInstant());

        System.out.println("LocalDateTime = " + localDateTime);
        System.out.println("Date = " + date2  + "\n");
        
        //3、LocalDate转换为Date
        
        ZoneId zoneId3 = ZoneId.systemDefault();
        LocalDate localDate3 = LocalDate.now();
        ZonedDateTime zdt3 = localDate3.atStartOfDay(zoneId3);

        Date date3 = Date.from(zdt3.toInstant());

        System.out.println("LocalDate = " + localDate3);
        System.out.println("Date = " + date3);
	}
	
	@Test
	public void test5() {
		DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
		LocalDateTime ldt = LocalDateTime.now();
		String str = ldt.format(dtf);
		System.out.println(str);
		
		//自定义格式
		DateTimeFormatter dft2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
		String str2 = dft2.format(ldt);
		System.out.println(str2);
		
		//字符串按照指定格式解析到时间，要从字符串解析到时间，格式必须是有日期和时间，否则会报错
		LocalDateTime parse = ldt.parse(str2, dft2); 
		System.out.println(parse);
	}
	
	
	@Test
	public void test4() {
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);  //2017-10-26T21:38:34.471
		
		//指定为10号
		LocalDateTime ldt2 = ldt.withDayOfMonth(10);
		System.out.println(ldt2); //2017-10-10T21:38:34.471
		
		//计算下一个周一
		LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		System.out.println(ldt3);
		
		//自定义计算，比如需要计算下一个工作日是哪天（周日到周四，下个工作日加1天，周六加2天，周五加3天，这样就得到下个工作日了）
		LocalDateTime ldt5 = ldt.with( l -> {
			LocalDateTime ldt4 = (LocalDateTime)l;
			DayOfWeek dayOfWeek = ldt4.getDayOfWeek();
			
			if(dayOfWeek.equals(DayOfWeek.FRIDAY)) {
				return ldt4.plusDays(3);
			}else if(dayOfWeek.equals(DayOfWeek.FRIDAY)) {
				return ldt4.plusDays(2);
			}else {
				return ldt4.plusDays(1);
			}
		});
		System.out.println(ldt5);
	}
	
	@Test
	public void test3() {
		Instant ins1 = Instant.now();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		Instant ins2  = Instant.now();
		
		Duration duration = Duration.between(ins1, ins2);
		System.out.println(duration.getSeconds()); //相差秒
		System.out.println(duration.getNano());    //相差纳秒
		System.out.println(duration.toMillis());   //相差毫秒
		System.out.println(duration.toMinutes());  //相差分钟
		System.out.println(duration.toHours());    //相差小时
		
		System.out.println("---------------------");
		
		LocalTime lt1 = LocalTime.now();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		LocalTime lt2 = LocalTime.now();
		
		System.out.println(Duration.between(lt1, lt2).toMillis());
		
		System.out.println("---------------------");
		
		//创建指定日期的对象
		LocalDate ld1 = LocalDate.of(2016, 3, 8); 
		LocalDate ld2 = LocalDate.now();
		
		Period period = Period.between(ld1, ld2);
		System.out.println(period.getYears());  //2个日期相差几年
		System.out.println(period.getMonths()); //2个日期之间去掉年份相差几个月(只比较分月)
		System.out.println(period.getDays());   //2个日期之间去掉年份和月份相差几天(只比较天)
		
 	}
	
	@Test
	public void test2() {
		Instant now = Instant.now(); //默认获取UTC 时区
		System.out.println(now);
		
		OffsetDateTime zone = now.atOffset(ZoneOffset.ofHours(8));  //在now基础上偏移了8小时时差
		System.out.println(zone);
		
		System.out.println(now.toEpochMilli()); 	//显示当前时间戳
		System.out.println(System.currentTimeMillis());
		
	}
	
	
	
	@Test
	public void test1() {
		LocalDateTime ldt = LocalDateTime.now(); //获取当前系统时间，通过now()方法直接获取
		System.out.println(ldt);
		
		LocalDateTime ldt2 = LocalDateTime.of(2017, 10, 26, 11, 23); //创建指定时间的对象
		System.out.println(ldt2);
		
		//可以通过LocalDateTime.plusXXX方法来对时间进行累加，比如加天、小时、分钟、月、纳秒、秒、星期、年
		LocalDateTime ldt3 = ldt.plusDays(1L);   //在ldt基础上加1天，会产生一个新的实例
		System.out.println(ldt3);    
		System.out.println(ldt.plusHours(3L));   //在ldt基础上加3小时
		System.out.println(ldt.plusMinutes(30L));//在ldt基础上加30分钟
		System.out.println(ldt.plusMonths(2L));  //在ldt基础上加2月
		
		//可以通过LocalDateTime.minusXXX方法来对时间进行减法，比如减天、小时、分钟、月、纳秒、秒、星期、年
		LocalDateTime ldt4 = ldt.minusDays(6); //在ldt基础上减6天
		System.out.println(ldt4);
		
		System.out.println(ldt.getYear());
		System.out.println(ldt.getMonthValue()); //返回几月，这个月份是1 - 12
		System.out.println(ldt.getDayOfMonth()); //返回这个月的第几天，返回值是1 - 31
		DayOfWeek week = ldt.getDayOfWeek();     //返回星期几
		System.out.println(week.getValue());     //the day-of-week, from 1 (Monday) to 7 (Sunday)
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
