package com.project.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.demo.model.user.User;
import com.project.demo.service.UserService;
import com.project.demo.service.impl.TokenUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {

	@Autowired
	private TokenUserDetailsService jwtUserDetailsService;

	@Autowired
	private TokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userServiceImpl;
	
	@Value("${dailyLimit}")
	private int dailyLimit;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				response.setHeader("msg", "Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				response.setHeader("msg", "JWT Token has expired");
			}
		} else {
			response.setHeader("msg", "JWT Token does not begin with Bearer String");
		}

		//Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			User user =userServiceImpl.getLoginCountInDay(username);
			if(user!=null) {
				request.setAttribute("userSession", user);
			}
			long loginCountDay=user.getLoginLimit();
			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails) && loginCountDay<=dailyLimit) {
				
				userServiceImpl.updateLastLogin(username);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				response.setHeader("msg", "you are exceed daily limit");
				
			}
		}
		chain.doFilter(request, response);
	}

}
