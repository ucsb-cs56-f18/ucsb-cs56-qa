package org.pac4j.demo.spring;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.helper.HelperConfig;
import org.pac4j.springframework.web.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({HelperConfig.class, AnnotationConfig.class})
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class SecurityConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Config config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		
		// The roles "admin" and "member" are defined in
		// Pac4JConfig.java.   This code should probably be refactored
		// so that this is less scattered across multiple source files
		
		SecurityInterceptor gh_admin =
			new SecurityInterceptor(config, "GitHubClient", "admin");	    
		registry.addInterceptor(gh_admin).addPathPatterns("/admin/*");
		SecurityInterceptor gh_member =
			new SecurityInterceptor(config, "GitHubClient", "member");	    
		registry.addInterceptor(gh_member).addPathPatterns("/member/*");

		SecurityInterceptor gh_loggedIn =
			new SecurityInterceptor(config, "GitHubClient");	    
		registry.addInterceptor(gh_loggedIn).addPathPatterns("/github/*");


		// TODO: find out the difference between gh_loggedIn and
		// loggedIn.  Maybe that's only a difference if/when there
		// are multiple ways to login
		
		SecurityInterceptor loggedIn =
			new SecurityInterceptor(config);	    
		registry.addInterceptor(loggedIn).addPathPatterns("/protected/*");
    }
}
