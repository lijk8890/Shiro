package cn.com.infosec.springboot.shiro.config;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

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

//		Map<String, Filter> filters = new TreeMap<String, Filter>();
//
//		filters.put("anon", new AnonymousFilter());
//		filters.put("authc", new FormAuthenticationFilter());
//		filters.put("logout", new LogoutFilter());
//
//		shiroFilterFactoryBean.setFilters(filters);

		Map<String, String> chainDefinition = new TreeMap<String, String>();
		chainDefinition.put("/js/**", "anon");
		chainDefinition.put("/css/**", "anon");

		chainDefinition.put("/favicon.ico", "anon");
		chainDefinition.put("/logout.do", "logout");

		chainDefinition.put("/*.do", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinition);

		shiroFilterFactoryBean.setLoginUrl("/index.do");
		shiroFilterFactoryBean.setSuccessUrl("/main.do");
//		shiroFilterFactoryBean.setUnauthorizedUrl("/forbidden.do");
		return shiroFilterFactoryBean;
	}

	@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

		Properties properties = new Properties();
		properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/forbidden");

		exceptionResolver.setExceptionMappings(properties);
		return exceptionResolver;
	}

}
