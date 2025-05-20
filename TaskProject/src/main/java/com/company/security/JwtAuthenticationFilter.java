package com.company.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserServiceDetails customUserServiceDetails;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token = getToken(request);
		if (StringUtils.isNotBlank(token) && jwtTokenProvider.validateToken(token)) {
			String email = jwtTokenProvider.getEmailFromToken(token);
			UserDetails userDetails = customUserServiceDetails.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		filterChain.doFilter(request, response);

	}

	private String getToken(HttpServletRequest request) {
		// Retrieve the "Authorization" header
		String token = request.getHeader("Authorization");

		// Check if the token exists, is not null, and starts with "Bearer "
		if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
			// Return the token without the "Bearer " prefix
			return token.substring(7, token.length()); // Remove the "Bearer " part and return the token
		}

		// Return null if the token is invalid
		return null;
	}

}
