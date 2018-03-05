package timing;

import java.io.File;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每一个小时清除一下验证码文件
 * FileName ClearFileTask.java
 * @author DELL
 * @date 2017年12月20日
 */
@Component
public class ClearFileTask {
	
	@Scheduled(fixedRate=1000*60*60)
	public void aotoThaw(){
		String proPath = System.getProperty("user.dir");
		String path = proPath.substring(0,proPath.lastIndexOf(File.separator));
		String classPath = this.getClass().getResource("/").getPath();
		classPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes/"));
		String name = classPath.substring(classPath.lastIndexOf("/")+1);
		path += "/webapps/"+name+"/img/loginCode";
		System.out.println("【"+name+"】开始清理验证码文件。");
		File f = new File(path);
		System.out.println(f.getAbsolutePath());
		if(!f.exists()){
			System.out.println("【"+name+"】验证码文件不存在。");
			return;
		}
		File[] files = f.listFiles();
		for(int i=0;i<files.length;i++){
			if(!files[i].getName().equals("error.jpg") && !files[i].getName().equals("wait.jpg")){
				files[i].delete();
			}
		}
		
	}
}
