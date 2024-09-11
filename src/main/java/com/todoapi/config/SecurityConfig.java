package com.todoapi.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import com.todoapi.security.JwtAuthFilter;
import com.todoapi.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		return userService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(request -> {
			List<String> allowedOrigins = new ArrayList<>();
			allowedOrigins.add("http://localhost");
			allowedOrigins.add("http://localhost:4200");
			allowedOrigins.add("https://angular-todo.davidebalice.dev");
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(allowedOrigins);
			config.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
			config.setAllowedHeaders(Arrays.asList("*"));
			config.setAllowCredentials(true);
			return config;
		}))
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(HttpMethod.OPTIONS, "/api/v1/**").permitAll()
						.requestMatchers("/", "/home", "/public", "/actuator/**", "/api/v1/error", "/api/v1/login",
								"/api/v1/csrf",
								"swagger-ui.html", "/swagger-ui/**", "/v3/**")
						.permitAll()
						// .requestMatchers("/api/v1/products/image/**").permitAll()
						.anyRequest().authenticated())

				.formLogin((form) -> form
						.loginPage("/api/v1/signin")
						.permitAll())
				.logout((logout) -> logout.permitAll())

				.authenticationProvider(authenticationProvider())

				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						// .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/v1/**",
						// "POST"))
						// .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/v1/**",
						// "PATCH"))
						// .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/v1/**",
						// "DELETE"))

						.ignoringRequestMatchers(new AntPathRequestMatcher("/api/v1/csrf", "POST"),
								new AntPathRequestMatcher("/api/v1/**", "POST"))
						.ignoringRequestMatchers(new AntPathRequestMatcher("/api/v1/csrf", "PATCH"),
								new AntPathRequestMatcher("/api/v1/**", "PATCH"))
						.ignoringRequestMatchers(new AntPathRequestMatcher("/api/v1/csrf", "DELETE"),
								new AntPathRequestMatcher("/api/v1/**", "DELETE"))

						.ignoringRequestMatchers(new AntPathRequestMatcher("/api/v1/csrf", "POST")))
				// new AntPathRequestMatcher("/api/v1/products/add", "POST")))

				// .ignoringRequestMatchers(new AntPathRequestMatcher("/api/v1/csrf", "POST"),
				// new AntPathRequestMatcher("/api/v1/products/add", "POST")))

				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)

				.exceptionHandling((exceptionHandling) -> exceptionHandling
						.authenticationEntryPoint((request, response, authException) -> {
							response.setContentType("application/json");
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							response.getWriter().write("{ \"message\": \"Unauthorized\" }");
						}));
		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}