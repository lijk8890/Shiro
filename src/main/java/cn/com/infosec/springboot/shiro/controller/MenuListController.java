package cn.com.infosec.springboot.shiro.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuListController {

	final private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("index.do")
	public String indexController(ModelMap modelMap) {

		logger.info("index: /WEB-INF/jsp/index.jsp");
		return "index";
	}

	@PostMapping("index.do")
	public String loginController(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "rememberMe", required = false) boolean rememberMe, ModelMap modelMap) {

		logger.info("username: " + username);
		logger.info("password: " + password);
		logger.info("rememberMe: " + rememberMe);

		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setTimeout(30 * 1000);

		if (subject.isAuthenticated() == false) {

			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(rememberMe);

			try {

				subject.login(token);
			} catch (AuthenticationException e) {

				logger.error("user: /WEB-INF/jsp/index.jsp");
				return "index";
			}
		}

//		if (subject.hasRole("admin") == false) {
//
//			logger.error("role: /WEB-INF/jsp/index.jsp");
//			return "index";
//		}
//		if (subject.isPermitted("system") == false) {
//
//			logger.error("permission: /WEB-INF/jsp/index.jsp");
//			return "index";
//		}

		modelMap.put("username", username);
		modelMap.put("password", password);
		modelMap.put("rememberMe", rememberMe);

		logger.info("login: /WEB-INF/jsp/main.jsp");
		return "main";
	}

	@RequiresRoles("admin")
	@RequiresPermissions("system")
	@RequestMapping("main.do")
	public String mainController(HttpSession session, ModelMap modelMap) {

		logger.info("main: /WEB-INF/jsp/main.jsp");
		return "main";
	}

	@RequestMapping("forbidden.do")
	public String forbiddenController(HttpSession session, ModelMap modelMap) {

		logger.info("forbidden: /WEB-INF/jsp/forbidden.jsp");
		return "forbidden";
	}

}
