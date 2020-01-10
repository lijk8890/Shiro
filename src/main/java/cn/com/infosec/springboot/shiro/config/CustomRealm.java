package cn.com.infosec.springboot.shiro.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRealm extends AuthorizingRealm {

	final private Logger logger = LoggerFactory.getLogger(this.getClass());

	final private String USERNAME = "root";
	final private String PASSWORD = "11111111";

	/**
	 * 认证
	 */
	@Override
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
		String username = uptoken.getUsername();
		String password = new String(uptoken.getPassword());

		if (USERNAME.equalsIgnoreCase(username) == false || PASSWORD.equalsIgnoreCase(password) == false) {

			logger.error("认证失败: " + username);
			logger.error("认证失败: " + password);
			return null;
		}

		logger.info("认证: " + username);
		logger.info("认证: " + password);
		return new SimpleAuthenticationInfo(USERNAME, PASSWORD, this.getName());
	}

	/**
	 * 授权
	 */
	@Override
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		String username = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (USERNAME.equalsIgnoreCase(username) == true) {

			info.addRole("admin");
			info.addStringPermission("system");
		}

		logger.info("授权: " + username);
		return info;
	}

}
