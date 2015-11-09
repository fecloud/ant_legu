package cn.dotui.legu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.UUID;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.json.JSONObject;

public class LeGu extends Task {
	
	private final Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));

	private boolean userProxy = false;

	public static final String UPLOAD_URL = "http://legu.qq.com/data_deal/rec_app";
	public static final String COMMIT_URL = "http://legu.qq.com/data_deal/app_tab?act=jg&id=%s&diy_msg=";
	public static final String CHECK_URL = "http://legu.qq.com/data_deal/app_check";
	public static final String GETDOWNLOAD_URL = "http://legu.qq.com/data_deal/app_tab?act=jg_down&id=%s";
	
	public static final int APPCHEKCOUNT = 24;
	public static final int APPCHEKCOUNT_TIME = 5000;

	public static final String Accept = "application/json, text/javascript, */*; q=0.01";
	// public static final String Accept_Encoding = "gzip, deflate";
	public static final String Accept_Language = "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,vi;q=0.2";
	public static final String X_Requested_With = "XMLHttpRequest";
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";

	private String input;
	private String output;
	private String cookie;
	
	
	
	private UploadResult uploadResult;
	private CommitResult commitResult;

	public String getInput() {
		return this.input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public void execute() throws BuildException {

		System.out.println("输入文件" + input);

		if (this.input == null || checkInput(input)) {
			throw new BuildException("Missing attribute input");
		}
		
		System.out.println("cookie" + cookie);
		if (this.cookie == null || checkInput(cookie)) {
			throw new BuildException("Missing attribute cookie");
		}

		if (this.output == null) {
			throw new BuildException("Missing attribute output");
		}

		System.out.println("输入出件" + output);

		System.out.println("上传文件中...");
		uploadFile();
		System.out.println("上传文件完成");
		
		System.out.println("提交加密中...");
		commitJiaMi();
		System.out.println("提交加密完成");
		
		try {
			Thread.sleep(APPCHEKCOUNT_TIME);
		} catch (InterruptedException e) {
		}
		boolean complteJiaMi = false;
		for(int i = 0 ;i <APPCHEKCOUNT;i++) {
			System.out.println("检查加密中...");
			final CheckResult result = appCheck();
			if (result != null) {
				if (result.getRes().equalsIgnoreCase("ing")) {
					System.out.println("加密等待" + (APPCHEKCOUNT_TIME / 1000) + "秒");
					try {
						Thread.sleep(APPCHEKCOUNT_TIME);
					} catch (InterruptedException e) {
					}
				} else if(result.resIsOK()) {
					complteJiaMi = true;
					break;
				}
			}
		}
		
		if(!complteJiaMi) {
			throw new BuildException("服务器检查加密错误");
		}
		System.out.println("服务器加密完成");
		
		System.out.println("提交加密中...");
		String downloadUrl = getDownloadUrl();
		if (null != downloadUrl) {
			System.out.println("加密后的apk下载地址：" + downloadUrl);
		} else {
			throw new BuildException("加密后的apk下载地址错误");
		}
		
		downloadApk(downloadUrl);
		System.out.println("乐固加密成功");

		super.execute();
	}

	/**
	 * 
	 */
	private void uploadFile() throws BuildException {
		
		uploadResult = null;
		try {
			String splitString = "WebKitFormBoundaryGbM2Y6s7LiL7jcJV"
					+ UUID.randomUUID().toString().substring(0, 16);
			
			HttpURLConnection conn = null;
			
			if (userProxy) {
				conn = (HttpURLConnection) new URL(UPLOAD_URL).openConnection(proxy);
			} else {
				conn = (HttpURLConnection) new URL(UPLOAD_URL).openConnection();
			}
			
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept", Accept);
			// conn.setRequestProperty("Accept-Encoding", Accept_Encoding);
			conn.setRequestProperty("Accept-Language", Accept_Language);
			conn.setRequestProperty("X-Requested-With", X_Requested_With);
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Cookie", readText(cookie));

			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=----" + splitString);
			

			final OutputStream out = conn.getOutputStream();

			out.write(("------" + splitString).getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			out.write(String
					.format("Content-Disposition: form-data; name=\"deal_app\"; filename=\"%s\"",
							new File(input).getName()).getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			out.write("Content-Type: application/octet-stream"
					.getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));

			final FileInputStream fileInputStream = new FileInputStream(input);
			final byte buffer[] = new byte[1024 * 1024];
			int len = 0;

			System.out.println("发送文件内容中...");
			while (-1 != (len = fileInputStream.read(buffer))) {
				out.write(buffer, 0, len);
			}

			out.write("\r\n".getBytes("UTF-8"));
			out.write(("------" + splitString).getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			out.write("Content-Disposition: form-data; name=\"win_flag\""
					.getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			
			out.write("jg".getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));
			out.write(("------" + splitString).getBytes("UTF-8"));
			out.write("--".getBytes("UTF-8"));
			out.write("\r\n".getBytes("UTF-8"));

			out.flush();
			System.out.println("发送文件内容中完成");

			if (conn.getResponseCode() == 200) {
				System.out.println("上传文件返回200");
				final String resultString = read(conn.getInputStream());
				if (resultString == null || resultString.trim().length() == 0) {
					throw new Exception("读取响应错误");
				}

				System.out.println(resultString);
				final JSONObject json = new JSONObject(resultString);
				if (json.has("res")) {
					String res = json.getString("res");
					if (res != null && res.trim().equals("login")) {
						System.out.println("必须登录");
						throw new Exception("cookie不对了");
					} else {
						uploadResult = new UploadResult();
						uploadResult.fromJSON(json);
						if (uploadResult.resIsOK()){
							System.out.println("上传apkd成功");
						} else {
							throw new Exception("上传apk错误");
						}
					}
				}

			} else {
				throw new Exception("http result code:"
						+ conn.getResponseCode());
			}

		} catch (Exception e) {
			throw new BuildException("uploadFile error\n" + e.getMessage(), e);
		}

	}
	

	private void commitJiaMi() throws BuildException {
		if (null != uploadResult) {
			try {
				String address = String
						.format(COMMIT_URL, uploadResult.getId());
				HttpURLConnection conn = null;

				if (userProxy) {
					conn = (HttpURLConnection) new URL(address)
							.openConnection(proxy);
				} else {
					conn = (HttpURLConnection) new URL(address)
							.openConnection();
				}

				conn.setConnectTimeout(10000);
				conn.setReadTimeout(10000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setDoOutput(false);
				conn.setRequestProperty("Accept", Accept);
				conn.setRequestProperty("Accept-Language", Accept_Language);
				conn.setRequestProperty("X-Requested-With", X_Requested_With);
				conn.setRequestProperty("User-Agent", USER_AGENT);
				conn.setRequestProperty("Cookie", readText(cookie));
				
				if (conn.getResponseCode() == 200) {
					System.out.println("提交加密返回200");
					final String resultString = read(conn.getInputStream());
					if (resultString == null || resultString.trim().length() == 0) {
						throw new Exception("读取响应错误");
					}

					System.out.println(resultString);
					final JSONObject json = new JSONObject(resultString);
					if (json.has("res")) {
						String res = json.getString("res");
						if (res != null && res.trim().equals("login")) {
							System.out.println("必须登录");
							throw new Exception("cookie不对了");
						} else {
							commitResult = new CommitResult();
							commitResult.fromJSON(json);
							if (commitResult.resIsOK()){
								System.out.println("提交加密apk成功");
							} else {
								throw new Exception("提交加密apk错误");
							}
						}
					}

				} else {
					throw new BuildException("http result code:"
							+ conn.getResponseCode());
				}
				
			} catch (Exception e) {
				throw new BuildException("commitJiaMi error\n" + e.getMessage(), e);
			}
		}
	}
	
	private CheckResult appCheck() {
		try {
			HttpURLConnection conn = null;

			if (userProxy) {
				conn = (HttpURLConnection) new URL(CHECK_URL)
						.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) new URL(CHECK_URL).openConnection();
			}

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept", Accept);
			conn.setRequestProperty("Accept-Language", Accept_Language);
			conn.setRequestProperty("X-Requested-With", X_Requested_With);
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Cookie", readText(cookie));

			final OutputStream out = conn.getOutputStream();
			out.write(String.format("act=jg&id=%s", uploadResult.getId())
					.getBytes("UTF-8"));
			out.flush();

			if (conn.getResponseCode() == 200) {
				System.out.println("检查加密返回200");
				final String resultString = read(conn.getInputStream());
				if (resultString == null || resultString.trim().length() == 0) {
					throw new Exception("读取响应错误");
				}

				System.out.println(resultString);
				final JSONObject json = new JSONObject(resultString);
				if (json.has("res")) {
					String res = json.getString("res");
					if (res != null && res.trim().equals("login")) {
						System.out.println("必须登录");
						throw new Exception("cookie不对了");
					} else {
						final CheckResult checkResult = new CheckResult();
						checkResult.fromJSON(json);
						return checkResult;
					}
				}

			} else {
				throw new BuildException("http result code:"
						+ conn.getResponseCode());
			}

		} catch (Exception e) {
			throw new BuildException("commitJiaMi error\n" + e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 返回加密后的地址
	 * @return
	 */
	private String getDownloadUrl() {
		try {
			HttpURLConnection conn = null;

			if (userProxy) {
				conn = (HttpURLConnection) new URL(String.format(GETDOWNLOAD_URL, uploadResult.getId()))
						.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) new URL(String.format(GETDOWNLOAD_URL, uploadResult.getId())).openConnection();
			}

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setRequestProperty("Accept", Accept);
			conn.setRequestProperty("Accept-Language", Accept_Language);
			conn.setRequestProperty("X-Requested-With", X_Requested_With);
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Cookie", readText(cookie));
			conn.setInstanceFollowRedirects(false);

			if (conn.getResponseCode() == 302) {
				//
				String result = conn.getHeaderField("Location");
				if (null == result) {
					result = conn.getHeaderField("location");
				}
				return result;
			} else {
				throw new BuildException("http result code:"
						+ conn.getResponseCode());
			}

		} catch (Exception e) {
			throw new BuildException("getDownloadUrl error\n" + e.getMessage(), e);
		}
	}
	
	private void downloadApk(String address){
		try {
			HttpURLConnection conn = null;

			if (userProxy) {
				conn = (HttpURLConnection) new URL(address)
						.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) new URL(address).openConnection();
			}

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setRequestProperty("Accept", Accept);
			conn.setRequestProperty("Accept-Language", Accept_Language);
			conn.setRequestProperty("X-Requested-With", X_Requested_With);
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Cookie", readText(cookie));
			conn.setInstanceFollowRedirects(false);

			if (conn.getResponseCode() == 200) {
				//
				System.out.println(conn.getHeaderField("Content-Disposition"));
				System.out.println("加固后apk保存为:" + output);
				final long conLen = conn.getContentLengthLong();
				final InputStream in = conn.getInputStream();
				final FileOutputStream out = new FileOutputStream(output);
				
				final byte [] buffer = new byte[1024 * 1024];
				int len = -1;
				while(-1 != (len = in.read(buffer))){
					out.write(buffer, 0, len);
					out.flush();
				}
				
				out.close();
				conn.disconnect();
				
				//chekfile
				if (new File(output).length() == conLen) {
					System.out.println("下载加密后的apk成功");
				} else {
					throw new BuildException("下载加密后的apk错误");
				}
			} else {
				throw new BuildException("http result code:"
						+ conn.getResponseCode());
			}

		} catch (Exception e) {
			throw new BuildException("getDownloadUrl error\n" + e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private boolean checkInput(String input) {
		if (input != null) {
			final File file = new File(input);
			if (file.exists()) {
				return false;
			}
		}
		return true;
	}

	private static String read(InputStream in) throws IOException {
		if (null != in) {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = 0;
			final byte buffer[] = new byte[1024];
			while (-1 != (len = in.read(buffer))) {
				out.write(buffer, 0, len);
			}

			if (out.size() > 0) {
				return new String(out.toByteArray(), "UTF-8");
			}
		}
		return null;
	}
	
	private static String readText(String file) throws IOException {
		final FileInputStream in = new FileInputStream(file);
		return read(in);
	}

}
