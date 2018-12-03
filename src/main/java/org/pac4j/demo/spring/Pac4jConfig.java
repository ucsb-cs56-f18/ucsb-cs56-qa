package org.pac4j.demo.spring;

import org.pac4j.core.authorization.generator.AuthorizationGenerator;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.GitHubClient;

import org.pac4j.oauth.profile.github.GitHubProfile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;


import com.jcabi.github.RtGithub;
import com.jcabi.github.Github;
import com.jcabi.github.User;
import com.jcabi.github.wire.RetryCarefulWire;
import com.jcabi.github.Organization;
import com.jcabi.github.Coordinates;

import org.pac4j.core.profile.CommonProfile;

@Configuration
public class Pac4jConfig {

	private static Logger logger = LoggerFactory.getLogger(Pac4jConfig.class);
	
    @Value("${salt}")
    private String salt;
    
    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${github_client_id}")
    private String github_client_id;

    @Value("${github_client_secret}")
    private String github_client_secret;


	
    @Bean
    public Config config() {

        final GitHubClient ghClient =
			new GitHubClient(github_client_id, github_client_secret);

		logger.info("github_client_id ="+ github_client_id);
		logger.info("github_client_secret ="+ github_client_secret);
		
		// https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
		ghClient.setScope("read:org");

		AuthorizationGenerator<GitHubProfile> authGen = (ctx, profile) -> {
			if (CustomRoles.isAdmin(profile)) {
				profile.addRole(CustomRoles.adminRoleName);
				logger.info("added role "
							+ CustomRoles.adminRoleName + " to pac4j profile");
			}
			if (CustomRoles.isMember(profile)) {
				profile.addRole(CustomRoles.memberRoleName);
				logger.info("added role "
							+ CustomRoles.memberRoleName + " to pac4j profile");
			}
			return profile;
		};

		ghClient.addAuthorizationGenerator(authGen);

	    String callbackUrl = baseUrl + "/callback";
		logger.info("OAuth callback URL=" + callbackUrl);
		logger.info("  NOTE: On Heroku is this internal or external url?");
        final Clients clients =
			new Clients(callbackUrl, ghClient);

		// In that case, the callback URL of the FacebookClient is
		// baseUrl + "/callback?client_name=FacebookClient
		// and the callback URL of the GitHubClient is
		// baseUrl + "/callback?client_name=GitHubClient.
	
        final Config config = new Config(clients);
		
		RequireAnyRoleAuthorizer adminAuthorizer =
			new RequireAnyRoleAuthorizer(CustomRoles.adminRoleName);
		config.addAuthorizer("admin",adminAuthorizer);

		RequireAnyRoleAuthorizer memberAuthorizer =
			new RequireAnyRoleAuthorizer(CustomRoles.memberRoleName);
		config.addAuthorizer("member",memberAuthorizer);

        return config;
    }
}
