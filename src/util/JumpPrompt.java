package util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class JumpPrompt {
	/**
	 * 跳转到提示页面
	 * @param baseUrl
	 * @param baseContent
	 * @return
	 */
	public static ModelAndView jumpOfModelAndView(String baseUrl,String baseContent){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("baseContent",baseContent);
		model.put("baseUrl",baseUrl);
		return new ModelAndView("prompt",model);
	}
}
