package com.inventorysystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inventorysystem.api.service.MyUserService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserService myUserService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*  
		auth.inMemoryAuthentication()
		 .withUser("harry@gmail.com").password(getEncoder().encode("potter@123"))
		 .authorities("CUSTOMER")
		 .and()
		 .withUser("albus@gmail.com").password(getEncoder().encode("albus@123"))
		 .authorities("EXECUTIVE");
		 */
		auth.authenticationProvider(getAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors() // Enable CORS
				.and().authorizeRequests()
				.antMatchers(HttpMethod.POST,"/customer/add").permitAll()
				.antMatchers(HttpMethod.GET,"/product/category/{catId}").permitAll()
				.antMatchers(HttpMethod.GET,"/product/all").permitAll()
				.antMatchers(HttpMethod.GET,"/product/purchase/{cid}").hasAnyAuthority("CUSTOMER","EXECUTIVE")
				.antMatchers(HttpMethod.GET,"/auth/login").authenticated()
				.antMatchers(HttpMethod.POST,"/category/add").hasAuthority("EXECUTIVE")
				.antMatchers(HttpMethod.POST,"/order/post").hasAuthority("EXECUTIVE")
				.antMatchers(HttpMethod.GET,"/order/all").hasAuthority("EXECUTIVE")
				.antMatchers(HttpMethod.GET,"/order/supplier/all").hasAnyAuthority("SUPPLIER","EXECUTIVE")
				.antMatchers(HttpMethod.GET,"/order/manager/all").hasAnyAuthority("MANAGER","EXECUTIVE")
				.antMatchers(HttpMethod.PUT,"/order/status/update").hasAnyAuthority("SUPPLIER","EXECUTIVE","MANAGER")
				.antMatchers(HttpMethod.POST,"/auth/reset/db").permitAll()
				.antMatchers(HttpMethod.POST,"/product/dispatch").hasAuthority("MANAGER")
				.antMatchers(HttpMethod.GET,"/product/not-dispatched").hasAnyAuthority("MANAGER","EXECUTIVE")
				.anyRequest().permitAll()
				.and().httpBasic()
				.and().csrf().disable();

	}
	@Bean
	public PasswordEncoder getEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder; 
	}
	
	private DaoAuthenticationProvider getAuthenticationProvider(){
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(getEncoder());
		dao.setUserDetailsService(myUserService);
		return dao;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://localhost:3001");
		configuration.addAllowedOrigin("http://localhost:3000"); // Allow your frontend origin
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Add more methods if needed
		configuration.setAllowCredentials(true); // Allow cookies and headers
		configuration.addAllowedHeader("*"); // Allow all headers

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // Apply the configuration to all paths
		return source;
	}

}





