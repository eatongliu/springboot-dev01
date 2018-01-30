package com.apabi.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtils {
	
	private static int connectionRequestTimeout = 100000;
	private static int connectTimeout = 60000;
	private static int socketTimeout = 100000;
	public static final int DEFAULT_TIMEOUT = 60000;
	
	public static HttpResponse httpPut(String url, HttpEntity entity) throws Exception {
		return httpPut(url, null, entity);
	}
	
	public static HttpResponse httpPut(String url, Map<String, String> httpHeaders, HttpEntity entity) throws Exception {
		HttpPut httpPut = new HttpPut(url);
		setRequestConfig(httpPut);
		setRequestHeaders(httpPut, httpHeaders);
		httpPut.setEntity(entity);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient.execute(httpPut);
	}
	
	public static HttpResponse httpPost(String url, HttpEntity entity) throws Exception {
		return httpPost(url, null, entity);
	}
	
	public static HttpResponse httpPost(String url, Map<String, String> httpHeaders, HttpEntity entity) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		setRequestConfig(httpPost);
		setRequestHeaders(httpPost, httpHeaders);
		httpPost.setEntity(entity);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient.execute(httpPost);
	}
	
	//请求带base认证权限的请求
	public static HttpResponse httpPost(String url, Map<String, String> httpHeaders,HttpEntity entity, HttpContext hc) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		setRequestConfig(httpPost);
		setRequestHeaders(httpPost, httpHeaders);
		httpPost.setEntity(entity);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient.execute(httpPost,hc);
	}
	
	public static HttpResponse httpDelete(String url) throws Exception {
		return httpDelete(url, null, null);
	}
	
	public static HttpResponse httpDelete(String url, HttpEntity entity) throws Exception {
		return httpDelete(url, null, entity);
	}

	public static HttpResponse httpDelete(String url, Map<String, String> httpHeaders, HttpEntity entity) throws Exception {
		HttpDelete httpDelete = new HttpDelete(url);
		setRequestConfig(httpDelete);
		setRequestHeaders(httpDelete, httpHeaders);
		httpDelete.setEntity(entity);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient.execute(httpDelete);
	}
	//http请求获取HttpEntity
	public static HttpResponse httpGet(String url) throws Exception{
		return httpGet(url, null);
	}
	
	public static HttpResponse httpGet(String url, Map<String, String> httpHeaders) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		setRequestConfig(httpGet);
		setRequestHeaders(httpGet, httpHeaders);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient.execute(httpGet);
	}
	
	public static HttpResponse httpGet(String url,Map<String, String> httpHeaders,HttpClientContext hcc) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		setRequestConfig(httpGet);
		setRequestHeaders(httpGet, httpHeaders);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient.execute(httpGet,hcc);
	}
	public static InputStream getResponseInputStream(HttpResponse httpResponse) throws Exception {
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null) {
			return entity.getContent();
		} else {
			return null;
		}
	}
	
	public static HttpEntity toStringEntity(String src) {
		return toStringEntity(src, Consts.UTF_8.name());
	}
	
	public static HttpEntity toStringEntity(String src, String charset) {
		StringEntity stringEntity = new StringEntity(src, charset);
		return stringEntity;
	}
	
	public static HttpEntity toInputStreamEntity(InputStream inputStream) throws Exception {
		return toInputStreamEntity(inputStream, null, null);
	}
	
	public static HttpEntity doGetEntity(String url, Map<String, String> httpHeaders) throws Exception {
		HttpResponse response = httpGet(url, httpHeaders);
		return getEntityFromResponse(response);
	}
	
	public static HttpEntity doGetEntity(String url, Map<String, String> httpHeaders,HttpClientContext hcc) throws Exception {
		HttpResponse response = httpGet(url, httpHeaders,hcc);
		return getEntityFromResponse(response);
	}
	
	public static HttpEntity doGetEntity(String url) throws Exception {
		return doGetEntity(url, null);
	}
	
	public static HttpEntity doPostEntity(String url, Map<String, String> httpHeaders, HttpEntity entity) throws Exception {
		HttpResponse response = httpPost(url, httpHeaders, entity);
		return getEntityFromResponse(response);
	}
	
	public static HttpEntity doPostEntity(String url, HttpEntity entity) throws Exception {
		return doPostEntity(url, null, entity);
	}
	
	public static HttpEntity doPutEntity(String url, Map<String, String> httpHeaders, HttpEntity entity) throws Exception {
		HttpResponse httpResponse = httpPut(url, httpHeaders, entity);
		return getEntityFromResponse(httpResponse);
	}
	
	public static HttpEntity doPutEntity(String url, HttpEntity entity) throws Exception {
		return doPutEntity(url, null, entity);
	}
	
	public static byte [] doGetByteArray(String url) throws Exception {
		HttpResponse response = httpGet(url, null);
		HttpEntity entity = getEntityFromResponse(response);
		byte[] byteArr = IOUtils.toByteArray(entity.getContent());
		return byteArr;
	}
	
	public static HttpEntity toInputStreamEntity(InputStream inputStream, String contentType, String charset) throws Exception {
		InputStreamEntity entity = new InputStreamEntity(inputStream);
		entity.setContentEncoding(charset);
		entity.setContentType(contentType);
		return entity;
	}
	
	private static void setRequestHeaders(HttpRequest httpRequest, Map<String, String> httpHeaders) {
		if(httpHeaders != null) {
			Set<Entry<String, String>> headEntries = httpHeaders.entrySet();
			for(Entry<String, String> entry : headEntries){
				httpRequest.addHeader(entry.getKey(), entry.getValue());
			}
		}
	}
	
	private static void setRequestConfig(HttpRequestBase httpRequest) {
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
		httpRequest.setConfig(requestConfig);
	}

	private static HttpEntity getEntityFromResponse(HttpResponse response) {
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity();
		} else {
			return null;
		}
	}
	
	public static HttpEntity doGetEntityForSolrPaper(String url) throws Exception{
	Map<String, String> header = new HashMap<String,String>();
	HttpClientContext context = makeCredentials();
	HttpEntity he= HttpUtils.doGetEntity(url,header,context);
	return he;
	}
	
	public static HttpEntity doGetEntityForSolrTopic(String url) throws Exception{
	Map<String, String> header = new HashMap<String,String>();
	HttpClientContext context = makeCredentialsForTopic();
	HttpEntity he= HttpUtils.doGetEntity(url,header,context);
	return he;
	}
	
	//构建请求solr的认证参数
	private static HttpClientContext makeCredentials(){
		CredentialsProvider cp = new BasicCredentialsProvider();
		String username = "apabi";
		String password = "Founder123";
		Credentials credentials = new UsernamePasswordCredentials(username, password);
		cp.setCredentials(AuthScope.ANY, credentials);
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(cp);
		return context;
	}
	//构建请求solr的认证参数
	private static HttpClientContext makeCredentialsForTopic(){
		CredentialsProvider cp = new BasicCredentialsProvider();
		String username = "apabi";
		String password = "Founder123";
		Credentials credentials = new UsernamePasswordCredentials(username, password);
		cp.setCredentials(AuthScope.ANY, credentials);
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(cp);
		return context;
	}
	public static InputStream httpPostByStream(String strUrl,String data,String dataType) throws Exception{
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("content-type", dataType);
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("user-agent", "Android-Large");
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		writer.write(data);
		writer.flush();
		writer.close();
		return conn.getInputStream();
	}
	
	public static InputStream httpPostByStreamForPaper(String strUrl,String data,String dataType) throws Exception{
		Map<String, String> httpheader  = new HashMap<>();
		httpheader.put("accept", "*/*");
		httpheader.put("connection", "Keep-Alive");
		httpheader.put("content-type", dataType);
		httpheader.put("Charsert", "UTF-8");
		httpheader.put("user-agent", "Android-Large");
		StringEntity entity = new StringEntity(data, "UTF-8");
		HttpClientContext context = makeCredentials();
		HttpResponse hr = httpPost(strUrl, httpheader, entity, context);
		InputStream in = null;
		if(hr.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			in = hr.getEntity().getContent();
		}
		return in;
	}
	
	public static void main(String[] args) throws Exception {

	}
}
