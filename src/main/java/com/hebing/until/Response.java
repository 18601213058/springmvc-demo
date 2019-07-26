package com.hebing.until;
/**
* 项目名称：EnterpriseInfoService    * 类名称：Response    * 类描述：    
* 创建人：zhaozhichao    
* 创建时间：2015年9月25日 上午11:05:22    * 修改人：zhaozhichao    
* 修改时间：2015年9月25日 上午11:05:22    * 修改备注：    
* @version 
 */
public class Response<T>{
	private String code;
	private String msg;
	private T t;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	@Override
	public String toString() {
		return "Response [code=" + code + ", msg=" + msg + ", t=" + t + "]";
	}
	
	
	
}
