package restdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 
 * @author leonardlu
 *
 */

@SpringBootApplication
public class OctusRestdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OctusRestdemoApplication.class, args);
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.requestMatchers().antMatchers("/","/api/**")
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
            .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')");
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId("api");
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
			.withClient("my-client-with-secret")
            .authorizedGrantTypes("client_credentials", "password")
            .authorities("ROLE_CLIENT")
            .scopes("read","write")
            .resourceIds("api")
            .secret("secret");
		}

	}

}
