package com.fadeevivan.springboot.security;

import com.fadeevivan.springboot.security.handler.LoginSuccessHandler;
import com.fadeevivan.springboot.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	UserDetailsService userDetailsService;

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("user").password("{noop}100").roles("USER")
//				.and()
//				.withUser("admin").password("{noop}100").roles("USER", "ADMIN");
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				// указываем страницу с формой логина
				.loginPage("/login")
				//указываем логику обработки при логине
				.successHandler(new LoginSuccessHandler())
				// указываем action с формы логина
				.loginProcessingUrl("/login")
				// Указываем параметры логина и пароля с формы логина
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				// даем доступ к форме логина всем
				.permitAll();

		http.logout()
				// разрешаем делать логаут всем
				.permitAll()
				// указываем URL логаута
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				// указываем URL при удачном логауте
				.logoutSuccessUrl("/login?logout")
				//выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
				.and().csrf().disable();

		http
				// делаем страницу регистрации недоступной для авторизированных пользователей
				.authorizeRequests()
				//страницы аутентификаци доступна всем
				.antMatchers("/login").anonymous()
				// защищенные URL
//				.antMatchers("/users").access("hasAnyRole('ADMIN')").anyRequest().authenticated();
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN").and().formLogin();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
	}
}
