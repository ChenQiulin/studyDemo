/**
 * Copyright 2015 ProficientCity Inc.
 */
package util;

/**
 * 认证相关方法
 * @author Harry Wu
 */
public class AuthorizeUtil {
	
	/**
	 * 检查请求是否过期，默认有效期是10分钟
	 * @param ots
	 * @return 未过期返回true，过期返回false
	 */
	public static boolean checkOTS(int ots){
		long gap = System.currentTimeMillis()/1000-ots;
		
		//默认请求有效时间是10分钟
		int validPeriod = 10*60;
		if(gap < validPeriod){
			return true;
		}
		return false;
	}
	

}
