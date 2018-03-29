package edu.asu.sbs.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}
	
	
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	/** Builds the target URL according to the logic defined in the main class. */
	protected String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication
				.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			
			// customer
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_CUSTOMER") || 
					grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_MERCHANT")) {
				return "/customer/home/";
			} 
			
			// regular employee - tier1 and manager - tier2
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_REGULAR")||
					grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_MANAGER")) {
				return "/employee/home";
			}
			
			// Admin
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
				System.out.println("$$$$$$$$ADMIN REDIRECT$$$$$$$$$$");
				return "/admin/home";
			}
			
			// Manager - tier2
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_MANAGER")) {
				return "/employee/home";
			}
			
		}
		
		throw new IllegalStateException();
	}
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
