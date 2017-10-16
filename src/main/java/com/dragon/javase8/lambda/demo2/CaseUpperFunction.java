package com.dragon.javase8.lambda.demo2;


@FunctionalInterface
public interface CaseUpperFunction {
	
	/**
	 * 将接收参数转换为大写并且截取字符串
	 * @param string
	 * @return
	 */
	String caseUpper(String string);
}
