package com.http.client.tools.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jdk.nashorn.internal.parser.JSONParser;

public class HttpUtils {

	private static HttpUtils http = new HttpUtils();
	
	private static final String CTYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";
	private static final String CTYPE_JSON = "application/json;charset=utf-8";
	private static final String charset = "utf-8";

	public static HttpUtils getInstance() {
		return http;
	}

	public String get(String url, Map<String, String> params, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("GET", url, buildQuery(params), 15000, 15000, CTYPE_FORM, headerMap);
	}

	public String get(String url) throws SocketTimeoutException, IOException {
		return doRequest("GET", url, "", 15000, 15000, CTYPE_FORM, null);
	}

	public String post(String url, Map<String, String> params, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("POST", url, buildQuery(params), 15000, 15000, CTYPE_JSON, headerMap);
	}

	public String postJson(String url, String jsonContent, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("POST", url, jsonContent, 15000, 15000, CTYPE_JSON, headerMap);
	}
	
	public String put(String url, String jsonContent, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("PUT", url, jsonContent, 15000, 15000, CTYPE_JSON, headerMap);
	}
	
	public String head(String url, String jsonContent, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("HEAD", url, jsonContent, 15000, 15000, CTYPE_JSON, headerMap);
	}
	
	public String options(String url, String jsonContent, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("OPTIONS", url, jsonContent, 15000, 15000, CTYPE_JSON, headerMap);
	}
	
	public String delete(String url, String jsonContent, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("DELETE", url, jsonContent, 15000, 15000, CTYPE_JSON, headerMap);
	}
	
	public String trace(String url, String jsonContent, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		return doRequest("TRACE", url, jsonContent, 15000, 15000, CTYPE_JSON, headerMap);
	}

	private String doRequest(String method, String url, String requestContent, int connectTimeout, int readTimeout,
			String ctype, Map<String, String> headerMap) throws SocketTimeoutException, IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			conn = getConnection(new URL(url), method, ctype, headerMap);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			if (requestContent != null && requestContent.trim().length() > 0) {
				out = conn.getOutputStream();
				out.write(requestContent.getBytes(charset));
			}

			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
			conn = null;
		}
		return rsp;
	}

	private HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap)
			throws IOException {
		HttpURLConnection conn;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,application/json");
		conn.setRequestProperty("Content-Type", ctype);
		if (headerMap != null) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		return conn;
	}

	private class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}

	private String getResponseAsString(HttpURLConnection conn) throws IOException {
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset, conn);
		} else {
			String msg = getStreamAsString(es, charset, conn);
			if (msg != null && msg.trim().length() > 0) {
				throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
			} else {
				return msg;
			}
		}
	}

	private String getStreamAsString(InputStream stream, String charset, HttpURLConnection conn) throws IOException {
		try {
			Reader reader = new InputStreamReader(stream, charset);
			StringBuilder response = new StringBuilder();
			final char[] buff = new char[1024];
			int read = 0;
			while ((read = reader.read(buff)) > 0) {
				response.append(buff, 0, read);
			}
			return response.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private String buildQuery(Map<String, String> params) throws IOException {
		if (params == null || params.isEmpty()) {
			return "";
		}

		StringBuilder query = new StringBuilder();
		Set<Map.Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Map.Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (hasParam) {
				query.append("&");
			} else {
				hasParam = true;
			}
			query.append(name).append("=").append(URLEncoder.encode(value, charset));
		}
		return query.toString();
	}
	
	public String checkHttps(String url){
		if(url == null || "".equals(url.trim())) {
			return "url is null or empty!";
		}
		if(!url.startsWith("http")) {
			return "url is error!";
		}
		return null;
	}

	public static void main(String[] args) throws SocketTimeoutException, IOException {
//		System.out.println(getInstance().get("https://www.baidu.com/"));
		String s = "{\"respCode\":\"00000\",\"memo\":\"成功\",\"data\":[{\"id\":30,\"jeName\":\"全部权限\",\"permissionType\":\"00,11,20,30,10\",\"remark\":null,\"projectNo\":\"20HM7VGO8GKW\",\"projectName\":\"我的第一个项目\",\"companyNo\":\"1000001\",\"companyName\":\"renguoqing公司\",\"createName\":null,\"createDate\":1628048102000,\"modifyName\":null,\"modifyDate\":null}],\"success\":true}";
		System.out.println(JSONParser.quote(s));
	}
}
