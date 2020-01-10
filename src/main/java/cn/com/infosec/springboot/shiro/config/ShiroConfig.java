package cn.com.infosec.springboot.shiro.config;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	final private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public Realm customRealm() {
		logger.info("customRealm");

		CustomRealm customRealm = new CustomRealm();

		return customRealm;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();

		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setMaxAge(5 * 60);

		cookieRememberMeManager.setCookie(simpleCookie);
		cookieRememberMeManager.setCipherKey(Base64.decode("MTIzNDU2NzgxMjM0NTY3OA=="));
		return cookieRememberMeManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		logger.info("securityManager");

		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

		securityManager.setRealm(customRealm());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		logger.info("shiroFilter");

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());

		shiroFilterFactoryBean.setLoginUrl("/index.do");
		shiroFilterFactoryBean.setSuccessUrl("/main.do");
		shiroFilterFactoryBean.setUnauthorizedUrl("/forbidden.do");

		Map<String, Filter> filters = new TreeMap<String, Filter>();

		filters.put("anon", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());

		shiroFilterFactoryBean.setFilters(filters);

		Map<String, String> chainDefinition = new TreeMap<String, String>();
		chainDefinition.put("/js/**", "anon");
		chainDefinition.put("/css/**", "anon");
		chainDefinition.put("/img/**", "anon");

		chainDefinition.put("/favicon.ico", "anon");
		chainDefinition.put("/logout.do", "logout");

		chainDefinition.put("/*.do", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinition);
		return shiroFilterFactoryBean;
	}

}
