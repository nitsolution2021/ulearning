package org.ulearn.packageservice.entity;

import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetails implements UserDetails{
	
		public LoginUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

		private String token;
		private Long UId;
		private String username;
		private String password;

		
		public LoginUserDetails(LoginEntity vendor) {
			this.username = vendor.getUserName();
			this.password = vendor.getPassword();
			
		}
		
		
		public String getToken() {
			return token;
		}


		public void setToken(String token) {
			this.token = token;
		}


		


		public void setUsername(String username) {
			this.username = username;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public LoginUserDetails(String token, Long uId, String username, String password) {
			super();
			this.token = token;
			UId = uId;
			this.username = username;
			this.password = password;
		}


		public Long getUId() {
			return UId;
		}


		public void setUId(Long uId) {
			UId = uId;
		}


		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}

		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}

}
