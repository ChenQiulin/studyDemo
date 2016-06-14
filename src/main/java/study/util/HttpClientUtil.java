/**
 * Copyright 2015 ProficientCity Inc.
 */
package study.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Http请求工具类
 * 
 * @author Chenql
 * 
 */
public class HttpClientUtil {

	private static String HTTPS_PROTOCOL = "https://";
	//private static String HTTP_PROTOCOL = "http://";

	/**
	 * Get请求,请求异常时throw RuntimeException
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String get(String url, Map<String, Object> param) {
		List<NameValuePair> list = new ArrayList<>();
		if (param != null) {
			for (Map.Entry<String,Object> entry : param.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		return HttpClientUtil.get(url, list);
	}

	/**
	 * Get请求,请求异常时throw RuntimeException
	 *
	 * @param url
	 *
	 * @return
	 */
	public static String get(String url) {
		checkNotNull(url, "url is null");
		String body = null;
		try {
			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置参数

			httpget.setURI(new URI(httpget.getURI().toString() ));
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();

			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}

			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httpget);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new RuntimeException("请求异常");
		}
		return body;
	}


	/**
	 * DELETE请求,请求异常时throw RuntimeException
	 *
	 * @param url
	 *
	 * @return
	 */
	public static String delete(String url) {
		checkNotNull(url, "url is null");
		String body = null;
		try {
			// Get请求
			HttpDelete httpDelete = new HttpDelete(url);
			// 设置参数

			httpDelete.setURI(new URI(httpDelete.getURI().toString() ));
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();

			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}

			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httpDelete);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new RuntimeException("请求异常");
		}
		return body;
	}
	/**
	 * Get请求,请求异常时throw RuntimeException
	 * 
	 * @param url
	 * @param params
	 * 
	 * @return
	 */
	public static String get(String url, List<NameValuePair> params) {
		checkNotNull(url, "url is null");
		String body = null;
		try {
			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();
			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}
			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httpget);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new RuntimeException("请求异常");
		}
		return body;
	}

	/**
	 * Get请求 重载 增加Header属性,请求异常时throw RuntimeException
	 * 
	 * @param url
	 * @param param
	 * @param header
	 * @return
	 */
	public static String get(String url, Map<String, Object> param, Map<String, Object> header) {
		List<NameValuePair> list = new ArrayList<>();
		if (param != null) {
			for (Map.Entry<String,Object> entry : param.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		return HttpClientUtil.get(url, list, header);
	}

	/**
	 * Get请求 重载 增加Header属性,请求异常时throw RuntimeException
	 * 
	 * @param url
	 * @param params
	 * @param header
	 * @return
	 */
	public static String get(String url, List<NameValuePair> params, Map<String, Object> header) {
		checkNotNull(url, "url is null");
		String body = null;
		try {
			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置Header属性
			if (header != null) {
				for (Map.Entry<String,Object> entry : header.entrySet()) {
					httpget.addHeader(entry.getKey(), (String) entry.getValue());
				}
			}
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));

			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();
			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}

			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httpget);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new RuntimeException("请求异常");
		}
		return body;
	}
	
	/**
	 * Get请求 重载 增加请求等待、读取等待时间限制
	 * 
	 * @param url
	 * @param param
	 * @param waitTimestamp 
	 * 			-- 请求等待时间， 为null即默认
	 * @param readTimestamp
	 * 			-- 读取时间， 为null即默认
	 * @return
	 */
	public static String get(String url, Map<String, Object> param, Integer waitTimestamp, Integer readTimestamp) {
		List<NameValuePair> list = new ArrayList<>();
		if (param != null) {
			for (Map.Entry<String,Object> entry: param.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		
		String body = null;
		try {
			checkNotNull(url, "url is null");
			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(list, "UTF-8"));
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();
			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}
			if(waitTimestamp != null && waitTimestamp > 0) {
				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, waitTimestamp);//连接时间
			}
			if(readTimestamp != null && readTimestamp > 0) {
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimestamp);//数据传输时间
			}
			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httpget);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new RuntimeException("请求异常");
		}
		return body;
	}



	/**
	 * Post请求,请求异常时throw RuntimeException
	 * @param url
	 * @param param
	 * @return
	 */
	public static String post(String url, Map<String, Object> param) {
		List<NameValuePair> list = new ArrayList<>();
		if (param != null) {
			for (Map.Entry<String,Object> entry : param.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		return HttpClientUtil.post(url, list);
	}


	/**
	 * Post请求,请求异常时throw RuntimeException
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 */
	public static String post(String url, Map<String, Object> param, Map<String, Object> headers) {
		List<NameValuePair> list = new ArrayList<>();
		if (param != null) {
			for (Map.Entry<String,Object> entry : param.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		List<Header> headerList = null;// new ArrayList<>();
		if (headers != null) {
			headerList = new ArrayList<Header>();
			for (Map.Entry<String,Object> entry : headers.entrySet()) {
				headerList.add(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		return HttpClientUtil.post(url, list, headerList);
	}


	/**
	 * Post请求,请求异常时throw RuntimeException
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postByJson(String url, Map<String, Object> param) {

		try {
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();
			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}
			HttpPost method = new HttpPost(url);
			String json = JSONObject.toJSONString(param);
			StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			method.setEntity(entity);
			HttpResponse result = httpClient.execute(method);
			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			return resData;
		} catch (Exception e) {
			throw new RuntimeException("请求异常:" + e.getMessage());
		}

	}

	public static void main(String[] args) {
		Map<String, Object> param = new HashMap<String, Object>();

		String receiptData = "ewoJInNpZ25hdHVyZSIgPSAiQW1mMnkxQm1SRm1uTlRCdHpMSXU0eGk0djdLRHAwMitpSkRzSmpaS3QyV2FqWFErNTNhMnBOemVkdCtmN3BFME5Fcm1iRUpnMXA5VmZFZ0x1akx6QTU1bUNTdXI1UlNUOGRoOFg1VDNNSXhtQnRIbUxxL1c1ZnNTSHRwVDVsTHFyNlRLVjMyWnQzYWNSYUFxK0hFYzZVOVF2aXhBZnRmMzJLTkIrTzRhZEpVTkFBQURWekNDQTFNd2dnSTdvQU1DQVFJQ0NCdXA0K1BBaG0vTE1BMEdDU3FHU0liM0RRRUJCUVVBTUg4eEN6QUpCZ05WQkFZVEFsVlRNUk13RVFZRFZRUUtEQXBCY0hCc1pTQkpibU11TVNZd0pBWURWUVFMREIxQmNIQnNaU0JEWlhKMGFXWnBZMkYwYVc5dUlFRjFkR2h2Y21sMGVURXpNREVHQTFVRUF3d3FRWEJ3YkdVZ2FWUjFibVZ6SUZOMGIzSmxJRU5sY25ScFptbGpZWFJwYjI0Z1FYVjBhRzl5YVhSNU1CNFhEVEUwTURZd056QXdNREl5TVZvWERURTJNRFV4T0RFNE16RXpNRm93WkRFak1DRUdBMVVFQXd3YVVIVnlZMmhoYzJWU1pXTmxhWEIwUTJWeWRHbG1hV05oZEdVeEd6QVpCZ05WQkFzTUVrRndjR3hsSUdsVWRXNWxjeUJUZEc5eVpURVRNQkVHQTFVRUNnd0tRWEJ3YkdVZ1NXNWpMakVMTUFrR0ExVUVCaE1DVlZNd2daOHdEUVlKS29aSWh2Y05BUUVCQlFBRGdZMEFNSUdKQW9HQkFNbVRFdUxnamltTHdSSnh5MW9FZjBlc1VORFZFSWU2d0Rzbm5hbDE0aE5CdDF2MTk1WDZuOTNZTzdnaTNvclBTdXg5RDU1NFNrTXArU2F5Zzg0bFRjMzYyVXRtWUxwV25iMzRucXlHeDlLQlZUeTVPR1Y0bGpFMU93QytvVG5STStRTFJDbWVOeE1iUFpoUzQ3VCtlWnRERWhWQjl1c2szK0pNMkNvZ2Z3bzdBZ01CQUFHamNqQndNQjBHQTFVZERnUVdCQlNKYUVlTnVxOURmNlpmTjY4RmUrSTJ1MjJzc0RBTUJnTlZIUk1CQWY4RUFqQUFNQjhHQTFVZEl3UVlNQmFBRkRZZDZPS2RndElCR0xVeWF3N1hRd3VSV0VNNk1BNEdBMVVkRHdFQi93UUVBd0lIZ0RBUUJnb3Foa2lHOTJOa0JnVUJCQUlGQURBTkJna3Foa2lHOXcwQkFRVUZBQU9DQVFFQWVhSlYyVTUxcnhmY3FBQWU1QzIvZkVXOEtVbDRpTzRsTXV0YTdONlh6UDFwWkl6MU5ra0N0SUl3ZXlOajVVUllISytIalJLU1U5UkxndU5sMG5rZnhxT2JpTWNrd1J1ZEtTcTY5Tkluclp5Q0Q2NlI0Szc3bmI5bE1UQUJTU1lsc0t0OG9OdGxoZ1IvMWtqU1NSUWNIa3RzRGNTaVFHS01ka1NscDRBeVhmN3ZuSFBCZTR5Q3dZVjJQcFNOMDRrYm9pSjNwQmx4c0d3Vi9abEwyNk0ydWVZSEtZQ3VYaGRxRnd4VmdtNTJoM29lSk9PdC92WTRFY1FxN2VxSG02bTAzWjliN1BSellNMktHWEhEbU9Nazd2RHBlTVZsTERQU0dZejErVTNzRHhKemViU3BiYUptVDdpbXpVS2ZnZ0VZN3h4ZjRjemZIMHlqNXdOelNHVE92UT09IjsKCSJwdXJjaGFzZS1pbmZvIiA9ICJld29KSW05eWFXZHBibUZzTFhCMWNtTm9ZWE5sTFdSaGRHVXRjSE4wSWlBOUlDSXlNREUxTFRBMUxUSXhJREl6T2pRM09qSTFJRUZ0WlhKcFkyRXZURzl6WDBGdVoyVnNaWE1pT3dvSkluVnVhWEYxWlMxcFpHVnVkR2xtYVdWeUlpQTlJQ0kxWWpOaE16RmhZbUZrTlRjMU4ySTBaV05qTVdRelpXWTRaamN3TURRd1pqSTBOMlprWm1RNUlqc0tDU0p2Y21sbmFXNWhiQzEwY21GdWMyRmpkR2x2YmkxcFpDSWdQU0FpTVRBd01EQXdNREUxTmpJNE1EWTRPU0k3Q2draVluWnljeUlnUFNBaU1TNHdJanNLQ1NKMGNtRnVjMkZqZEdsdmJpMXBaQ0lnUFNBaU1UQXdNREF3TURFMU5qSTRNRFk0T1NJN0Nna2ljWFZoYm5ScGRIa2lJRDBnSWpFaU93b0pJbTl5YVdkcGJtRnNMWEIxY21Ob1lYTmxMV1JoZEdVdGJYTWlJRDBnSWpFME16SXlOemN5TkRVd01ERWlPd29KSW5WdWFYRjFaUzEyWlc1a2IzSXRhV1JsYm5ScFptbGxjaUlnUFNBaVJERXhNelJETVVNdFFUTXhNUzAwTWpORUxVSkZOamN0T1RNNE1UVkZNRFJGT1RKQklqc0tDU0p3Y205a2RXTjBMV2xrSWlBOUlDSXlNREUxTURVeE5ERXlNakV3TVNJN0Nna2lhWFJsYlMxcFpDSWdQU0FpT1RrME56azJOVFV6SWpzS0NTSmlhV1FpSUQwZ0ltTnZiUzVzZFhONGQyTnFjeTVuYUhjdWFXRndJanNLQ1NKd2RYSmphR0Z6WlMxa1lYUmxMVzF6SWlBOUlDSXhORE15TWpjM01qUTFNREF4SWpzS0NTSndkWEpqYUdGelpTMWtZWFJsSWlBOUlDSXlNREUxTFRBMUxUSXlJREEyT2pRM09qSTFJRVYwWXk5SFRWUWlPd29KSW5CMWNtTm9ZWE5sTFdSaGRHVXRjSE4wSWlBOUlDSXlNREUxTFRBMUxUSXhJREl6T2pRM09qSTFJRUZ0WlhKcFkyRXZURzl6WDBGdVoyVnNaWE1pT3dvSkltOXlhV2RwYm1Gc0xYQjFjbU5vWVhObExXUmhkR1VpSUQwZ0lqSXdNVFV0TURVdE1qSWdNRFk2TkRjNk1qVWdSWFJqTDBkTlZDSTdDbjA9IjsKCSJlbnZpcm9ubWVudCIgPSAiU2FuZGJveCI7CgkicG9kIiA9ICIxMDAiOwoJInNpZ25pbmctc3RhdHVzIiA9ICIwIjsKfQ==";
		param.put("receipt-data", receiptData);
		System.out.println(HttpClientUtil.postByJson("https://sandbox.itunes.apple.com/verifyReceipt", param));
	}


	/**
	 * Post请求,请求异常时throw RuntimeException
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, List<NameValuePair> params) {
		checkNotNull(url, "url is null");

		String body = null;
		try {
			// Post请求
			HttpPost httppost = new HttpPost(url);

			// 设置参数
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();
			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}

			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httppost);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();

			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);

		} catch (Exception e) {
			throw new RuntimeException("请求异常:" + e.getMessage());
		}
		return body;
	}

	/**
	 * Post请求,请求异常时throw RuntimeException
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, List<NameValuePair> params, List<Header> headers) {
		checkNotNull(url, "url is null");

		String body = null;
		try {
			// Post请求
			HttpPost httppost = new HttpPost(url);
			if (headers != null && headers.size() > 0) {
				for (Header header : headers) {
					httppost.addHeader(header);
				}
			}
			// 设置参数
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			boolean useHttps = url.startsWith(HTTPS_PROTOCOL);
			HttpClient httpClient = new DefaultHttpClient();
			if (useHttps) {
				httpClient = HttpClientUtil.wrapClient(httpClient);
			}

			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httppost);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();

			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);

		} catch (Exception e) {
			throw new RuntimeException("请求异常:" + e.getMessage());
		}
		return body;
	}

	/**
	 * 使用https, 指定DefaultHttpClient忽略证书认证错误，继续进行https交互
	 * 
	 * @param base
	 * @return
	 */
	public static HttpClient wrapClient(HttpClient base) {

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
			return new DefaultHttpClient(mgr, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
