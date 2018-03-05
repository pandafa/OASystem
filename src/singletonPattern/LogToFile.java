package singletonPattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogToFile {
	public static String PROJECT_NAME = "";
	private static final String fileName = "_webAccessLog.log";
	private static final String fileParentName = "webLog/cxx";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss.SSSZ");
	private static File file = null;
	private static LogToFile log = null;
	public synchronized static LogToFile getInstance(){
		if(log==null){
			log = new LogToFile();
		}
		return log;
	}
	private LogToFile(){
		String classPath = this.getClass().getResource("/").getPath();
		classPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes/"));
		PROJECT_NAME = classPath.substring(classPath.lastIndexOf("/")+1);
		file = new File(fileParentName,PROJECT_NAME+fileName);
		File fileParent = new File(fileParentName);
		if(!fileParent.exists()){
			fileParent.mkdirs();
		}
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public String getAbsolutePath(){
		return file.getAbsolutePath();
	}
	public String readAllFile(){
		FileInputStream fis = null;
		String res = "";
		try {
			fis = new FileInputStream(file);
			
			int len;
			byte[] b = new byte[1024];
			while((len=fis.read(b))!=-1){
				res += new String(b,0,len,"utf-8");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "读取失败";
		} catch (IOException e) {
			e.printStackTrace();
			return "读取失败";
		}finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	public void logStart(){
		System.out.println("【"+PROJECT_NAME+"】日志存储位置："+file.getAbsolutePath());
		String str = ""+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator();
		str += "####################系统启动###########################"+System.lineSeparator();
		str += "启动时间："+sdf.format(new Date())+System.lineSeparator();
		str += "[时间]\t\t\t\t\t 访问方式\t 访问IP\t\t 访问用户\t 内容\t 访问路径 \t\t"+System.lineSeparator();
		wirteToFile(str);
	}
	public void logStop(){
		String str = "";
		str += "关闭时间："+sdf.format(new Date())+System.lineSeparator();
		str += "####################系统关闭###########################"+System.lineSeparator();
		str += ""+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator();
		wirteToFile(str);
		System.out.println("【"+PROJECT_NAME+"】已完全关闭！");
	}

	public void log(HttpServletRequest req, HttpServletResponse resp,String content){
		String str = "["+sdf.format(new Date())+"]"+"\t";//访问时间
		str += req.getMethod()+"\t\t";//请求方式
		str += req.getRemoteAddr()+":"+req.getRemotePort()+"\t";//访问IP
		//访问用户
		HttpSession session = req.getSession();
		String userId = (String)session.getAttribute("userJobId");
		if(userId==null){
			str += "游客\t\t";
		}else{
			str += userId+"\t\t";
		}
		str += content+"\t";//内容
		str += req.getServletPath()+"\t";//请求路径
		str += req.getQueryString()+"\t";//参数
		str += System.lineSeparator();
		wirteToFile(str);
		System.out.println("【"+PROJECT_NAME+"】"+str);
	}
	/**
	 * 写入文件
	 * @param str
	 */
	private synchronized void wirteToFile(String str){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, true);
			byte[] b = str.getBytes("utf-8");
			fos.write(b);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
