package com.hebing.until;



import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Map.Entry;

/**
 */
public class HttpUtils {
	/**
	 * 请求成功
	 */
	public  static String SUCCESS = "0000";
	
	
	public static Response<String> sendGet(String url, Map<String, String> params, String charset){
		Response<String> res = new Response<String>();
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
        	String param = null;
        	if(params!=null && params.size()>0){
        		StringBuilder paramBuffer = new StringBuilder();
            	for(Entry<String, String> entry:params.entrySet()){
            		paramBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),charset)).append("&");
            	}
            	param = paramBuffer.substring(0, paramBuffer.length()-1);
        	}
        	
            String urlNameString = url;
            if(param!=null){
            	if(url.indexOf("?")>0){
                	urlNameString = url + "&" + param;
                }else{
                	urlNameString = url + "?" + param;
                }
            }
            
            
            URL realUrl = new URL(urlNameString);
            
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset",charset);
            connection.setRequestProperty("Content-Type", 
            "application/x-www-form-urlencoded;charset="+ charset);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.connect();
           
           
            
            int code = connection.getResponseCode();
           
            if(code==200){
            	in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
            	String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                res.setCode("0000");
                res.setMsg("ok");
                res.setT(result.toString());
            }else{
            	res.setCode("1001");
            	res.setMsg("返回码异常:" + code);
            }
            
            
        }catch(SocketTimeoutException e){
			res.setCode("1001");
			res.setMsg("连接超时");
			e.printStackTrace();
		}catch (Exception e) {
			res.setCode("1001");
			res.setMsg("连接异常");
			e.printStackTrace();
		}
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	res.setCode("1002");
    			res.setMsg("关闭连接异常");
    			e2.printStackTrace();
            }
        }
        return res;
    }
	/**
	 * 
	* @Title: sendPost
	* @Description: 发送Post请求
	* @param @param url
	* @param @param params
	* @param @param charset
	* @param @return    
	* @return Response<String>    返回类型
	* @throws
	 */
	public static Response<String> sendPost(String url, Map<String, String> params,String charset){
		Response<String> res = new Response<String>();
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
	        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
	        connection.setRequestMethod("POST");// 提交模式
	        connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset",charset);
            connection.setRequestProperty("Content-Type", 
            "application/x-www-form-urlencoded;charset="+ charset);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setDoOutput(true);
            connection.connect();
	        
	        String param = null;
	        if(params!=null && params.size()>0){
	    		StringBuilder paramBuffer = new StringBuilder();
	        	for(Entry<String, String> entry:params.entrySet()){
	        		paramBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),charset)).append("&");
	        	}
	        	param = paramBuffer.substring(0, paramBuffer.length()-1);
	        	
	        	byte[] bypes = param.toString().getBytes();
	 	        
	 	        connection.getOutputStream().write(bypes);// 输入参数
	    	}
	       
	        
	       
	        
	        int code = connection.getResponseCode();
	           
            if(code==200){
            	in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
            	String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                res.setCode("0000");
                res.setMsg("ok");
                res.setT(result.toString());
            }else{
            	res.setCode("1001");
            	res.setMsg("返回码异常:" + code);
            }
	        
		}catch(SocketTimeoutException e){
			res.setCode("1001");
			res.setMsg("连接超时");
			e.printStackTrace();
		}catch (Exception e) {
			res.setCode("1001");
			res.setMsg("连接异常");
			e.printStackTrace();
		}
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	res.setCode("1002");
    			res.setMsg("关闭连接异常");
    			e2.printStackTrace();
            }
        }
        
		
		
		return res;
	}
	
	
	
	
	
	/**
	 * 
	* @Title: sendGet
	* @Description: 发送Get请求
	* @param @param url
	* @param @param params
	* @param @param headParam
	* @param @param charset
	* @param @return    
	* @return Response<String>    返回类型
	* @throws
	 */
	public static Response<String> sendGet(String url,
			Map<String, String> params, Map<String, String> headParam,
			String charset) {
		Response<String> res = new Response<String>();
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {
			String param = null;
			if (params != null && params.size() > 0) {
				StringBuilder paramBuffer = new StringBuilder();
				for (Entry<String, String> entry : params.entrySet()) {
					paramBuffer
							.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(), charset))
							.append("&");
				}
				param = paramBuffer.substring(0, paramBuffer.length() - 1);
			}

			String urlNameString = url;
			if (param != null) {
				if (url.indexOf("?") > 0) {
					urlNameString = url + "&" + param;
				} else {
					urlNameString = url + "?" + param;
				}
			}

			URL realUrl = new URL(urlNameString);

			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=" + charset);
			if (headParam != null && headParam.size() > 0) {
				for (Entry<String, String> entry : headParam.entrySet()) {
					connection.setRequestProperty(entry.getKey(),
							entry.getValue());
				}
			}
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(15000);
			connection.connect();

			int code = connection.getResponseCode();

			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), charset));
				String line;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
				res.setCode("0000");
				res.setMsg("ok");
				res.setT(result.toString());
			} else {
				res.setCode("1001");
				res.setMsg("返回码异常:" + code);
			}

		} catch (SocketTimeoutException e) {
			res.setCode("1001");
			res.setMsg("连接超时");
			e.printStackTrace();
		} catch (Exception e) {
			res.setCode("1001");
			res.setMsg("连接异常");
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				res.setCode("1002");
				res.setMsg("关闭连接异常");
				e2.printStackTrace();
			}
		}
		return res;
	}
	/**
	 * 
	* @Title: sendPost
	* @Description: 发送Post请求
	* @param @param url
	* @param @param params
	* @param @param charset
	* @param @return    
	* @return Response<String>    返回类型
	* @throws
	 */
	public static Response<String> sendPost(String url, Map<String, String> params,Map<String, String> headParam,String charset){
		Response<String> res = new Response<String>();
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
	        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
	        connection.setRequestMethod("POST");// 提交模式
	        connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset",charset);
            connection.setRequestProperty("Content-Type", 
            "application/x-www-form-urlencoded;charset="+ charset);
            if(headParam!=null && headParam.size()>0){
            	for(Entry<String, String> entry:headParam.entrySet()){
            		  connection.setRequestProperty(entry.getKey(),entry.getValue());
            	}
            }
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setDoOutput(true);
            connection.connect();
	        
	        String param = null;
	        if(params!=null && params.size()>0){
	    		StringBuilder paramBuffer = new StringBuilder();
	        	for(Entry<String, String> entry:params.entrySet()){
	        		paramBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),charset)).append("&");
	        	}
	        	param = paramBuffer.substring(0, paramBuffer.length()-1);
	        	
	        	byte[] bypes = param.toString().getBytes();
	 	        
	 	        connection.getOutputStream().write(bypes);// 输入参数
	    	}
	       
	        
	       
	        
	        int code = connection.getResponseCode();
	           
            if(code==200){
            	in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
            	String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                res.setCode("0000");
                res.setMsg("ok");
                res.setT(result.toString());
            }else{
            	res.setCode("1001");
            	res.setMsg("返回码异常:" + code);
            }
	        
		}catch(SocketTimeoutException e){
			res.setCode("1001");
			res.setMsg("连接超时");
			e.printStackTrace();
		}catch (Exception e) {
			res.setCode("1001");
			res.setMsg("连接异常");
			e.printStackTrace();
		}
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	res.setCode("1002");
    			res.setMsg("关闭连接异常");
    			e2.printStackTrace();
            }
        }
        
		
		
		return res;
	}




	public static JSONObject getUrlInfo(String requestURL,String method,String json) throws Exception {
		URL get_url = new URL(requestURL);
		// 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
		// (标识一个url所引用的远程对象连接)
		// 此时cnnection只是为一个连接对象,待连接中
		HttpURLConnection httpURLConnection = (HttpURLConnection) get_url
				.openConnection();
		// 设置请求方式为post
		httpURLConnection.setRequestMethod(method);
		// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
		httpURLConnection.setDoOutput(true);
		// 设置连接输入流为true
		httpURLConnection.setDoInput(true);
		// post请求缓存设为false
		httpURLConnection.setUseCaches(false);
		// 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
		// application/x-javascript text/xml->xml数据
		// application/x-javascript->json对象
		// application/x-www-form-urlencoded->表单数据
		// ;charset=utf-8 必须要，不然妙兜那边会出现乱码
		httpURLConnection.setRequestProperty("Content-type",
				"application/json;charset=utf-8");
		// 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
		httpURLConnection.connect();

		// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
		OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
		if(method.equals("POST")){
			out.append(json);
		}
		out.flush();
		out.close();

		// 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpURLConnection.getInputStream(), "UTF-8"));
		// 读取数据操作
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while((str = reader.readLine())!= null){
			buffer.append(str);
		}
		//转换成json
		JSONObject jsonObj = JSONObject.parseObject(buffer.toString());
		reader.close();
		return jsonObj;

	}



}
