package org.diningdevelopers.security;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

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
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.Initializable;
import org.diningdevelopers.model.UserModel;
import org.diningdevelopers.service.UserService;

@Stateless
@Local(Realm.class)
public class ShiroSecurityRealm extends AuthorizingRealm implements Authorizer, Initializable, PermissionResolverAware, RolePermissionResolverAware {

	@EJB
	private UserService developerService;

	public ShiroSecurityRealm() {
		CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-1");
		setCredentialsMatcher(credentialsMatcher);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		UserModel user = developerService.findByUsername(username);

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
