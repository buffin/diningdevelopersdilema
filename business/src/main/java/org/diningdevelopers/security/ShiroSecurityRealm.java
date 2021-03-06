package org.diningdevelopers.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.permission.PermissionResolverAware;
import org.apache.shiro.authz.permission.RolePermissionResolverAware;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.Initializable;
import org.diningdevelopers.business.boundary.UserBoundary;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.util.InitialContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroSecurityRealm extends AuthorizingRealm implements Authorizer, Initializable, PermissionResolverAware, RolePermissionResolverAware {

	private static final Logger logger = LoggerFactory.getLogger(ShiroSecurityRealm.class);
	
	public ShiroSecurityRealm() {
		CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-1");
		setCredentialsMatcher(credentialsMatcher);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		UserBoundary userBoundary = InitialContextUtils.doLookup("java:module/UserInteractor");
		User user = userBoundary.findByUsername(username);
		
		if (user == null) {
			throw new AuthenticationException();
		}

		SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(username, user.getPassword(), user.getName());

		return account;

	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

}
