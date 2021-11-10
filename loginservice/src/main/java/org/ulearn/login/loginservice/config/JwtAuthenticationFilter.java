package org.ulearn.login.loginservice.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ulearn.login.loginservice.entity.LoginEntity;
import org.ulearn.login.loginservice.exception.CustomException;
import org.ulearn.login.loginservice.helper.JwtUtil;
import org.ulearn.login.loginservice.repository.LoginRepository;
import org.ulearn.login.loginservice.services.VendorDetailsService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Controller
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	
	@Autowired
	private YMLConfig myConfig;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	UserDetails userDetails;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException{
		try {
			String headers = request.getHeader("Authorization");
			String username = null;
			String jwtToken = null;
			
			if (headers != null && headers.startsWith("Bearer ")) {
				jwtToken = headers.substring(7);
				
				try {
					username = this.jwtUtil.extractUsername(jwtToken);
				} catch (Exception e) {
					throw new CustomException("token is not validated");
				}
				
				
				 Optional<LoginEntity> findByUserName = loginRepository.findByUserNameAndToken(username,jwtToken);
				 
				if (findByUserName.isPresent()) {
					this.userDetails = vendorDetailsService.loadUserByUsername(username);
				} else {
					
						throw new CustomException("Token not valid");
					
				}
				
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				else {
					throw new CustomException("token is not validated");
				}
				
			}
			
//			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		    response.setHeader("Access-Control-Allow-Credentials", "true");
//		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		    response.setHeader("Access-Control-Max-Age", "3600");
//		    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
//			
			
			filterChain.doFilter(request, response);
		}catch(Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json");
            response.getWriter().write("{ \"statuss\" : 500 , \"messagee\" : \"Token not valid\"}");
//			throw new CustomeException(e.getMessage());
		}
		
		
	}
	
	
	
}
