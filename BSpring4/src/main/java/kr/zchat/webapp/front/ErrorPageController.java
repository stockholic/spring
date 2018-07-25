package kr.zchat.webapp.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorPageController {

	public static final String ERROR_PATH = "/error";
	public static final String ERROR_DEFAULT = ERROR_PATH + "/000";
	public static final String ERROR_401 = ERROR_PATH + "/401";
	public static final String ERROR_403 = ERROR_PATH + "/403";
	public static final String ERROR_404 = ERROR_PATH + "/404";
	public static final String ERROR_500 = ERROR_PATH + "/500";

	@RequestMapping(value = ERROR_DEFAULT, method = RequestMethod.GET)
	public String defaultError() {
		return "error/000";
	}

	@RequestMapping(value = ERROR_401, method = RequestMethod.GET)
	public String error401() {
		return "error/401";
	}

	@RequestMapping(value = ERROR_403, method = RequestMethod.GET)
	public String error403() {
		return "error/403";
	}

	@RequestMapping(value = ERROR_404, method = RequestMethod.GET)
	public String error404() {
		return "error/404";
	}

	@RequestMapping(value = ERROR_500, method = RequestMethod.GET)
	public String error500() {
		return "error/500";
	}


}