package worldcup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${headerAuthentication}")
    String headerAuthentication;

    @Value("${spring.mvc.servlet.path}")
    String path;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
		filter.setPrincipalRequestHeader(this.headerAuthentication);
		filter.setExceptionIfHeaderMissing(false);
		filter.setAuthenticationManager(this.authenticationManager());

		http
        .addFilterBefore(filter, filter.getClass())
		.headers()
		.contentTypeOptions().disable()
		.xssProtection().disable()
		.frameOptions().disable()
		.and()
//		.sessionManagement()
//		.
        .csrf()
        .disable()
		.exceptionHandling()
		.authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers(this.path+"/**").permitAll()
//        .antMatchers("/*").denyAll();
                ;
                
	}


	@Autowired
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint;

}