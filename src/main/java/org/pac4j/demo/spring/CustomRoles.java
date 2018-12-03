package org.pac4j.demo.spring;

import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Organization;
import com.jcabi.github.RtGithub;
import com.jcabi.github.User;
import com.jcabi.github.wire.RetryCarefulWire;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
   This class defines two custom roles, <code>adminRoleName</code>
   and <code>memberRoleName</code>.

 */
public class CustomRoles {

	// TODO: make the github_org_name a property set in the .json files
	
	public static final String github_org_name = "ucsb-cs56-f18";
	public static final String adminRoleName = "ROLE_ADMIN";
	public static final String memberRoleName = "ROLE_MEMBER";
	private static Logger logger =
		LoggerFactory.getLogger(CustomRoles.class);

	/**
	   isAdmin checks whether user is an admin in the
	   designated github organization.
	*/
		
	public static boolean isAdmin(CommonProfile profile) {
		String oauth_token = (String) profile.getAttribute("access_token");
		String user = (String) profile.getAttribute("login");

		logger.info("oauth_token="+oauth_token);
		logger.info("user="+user);
			
		Github github=null;
			
		try {

			// I forget why we have Github wrapped like this
			// TODO: find the tutorial that explains it
			// I think it has something to do with respecting rate limits
			github = new RtGithub(new RtGithub(oauth_token)
								  .entry()
								  .through(RetryCarefulWire.class, 50));
			
			logger.info("github="+github);
			User ghuser = github.users().get(user);
			logger.info("ghuser="+ghuser);
				
			JsonResponse jruser = github.entry()
				.uri().path("/user")
				.back()
				.method(Request.GET)
				.fetch()
				.as(JsonResponse.class);
				
			logger.info("jruser ="+ jruser);

			Organization org = github.organizations().get(github_org_name);

			logger.info("org ="+ org);
				
			JsonResponse jr = github.entry()
				.uri().path("/user/memberships/orgs/" + github_org_name)
				.back()
				.method(Request.GET)
				.fetch()
				.as(JsonResponse.class);

			String role = jr.json().readObject().getString("role");
				
			logger.info("jr ="+ jr);				
			logger.info("role ="+ role);

			if (role.equals("admin")) {
				return true;
			}
				
		} catch (Exception e) {
			logger.warn("Exception happened while trying to determine membership in github org");
			logger.warn(e.toString());
		} 
		return false;
	}

	// TODO: Refactor common code from isAdmin and isMember

	/**
	   Is the user a member of the organization? (But not an admin)
	 */
	
	public static boolean isMember(CommonProfile profile) {
		String oauth_token = (String) profile.getAttribute("access_token");
		String user = (String) profile.getAttribute("login");

		logger.info("oauth_token="+oauth_token);
		logger.info("user="+user);
			
		Github github=null;
			
		try {

			// I forget why we have Github wrapped like this
			// TODO: find the tutorial that explains it
			// I think it has something to do with respecting rate limits
			github = new RtGithub(new RtGithub(oauth_token)
								  .entry()
								  .through(RetryCarefulWire.class, 50));
			
			logger.info("github="+github);
			User ghuser = github.users().get(user);
			logger.info("ghuser="+ghuser);
				
			JsonResponse jruser = github.entry()
				.uri().path("/user")
				.back()
				.method(Request.GET)
				.fetch()
				.as(JsonResponse.class);
				
			logger.info("jruser ="+ jruser);

			Organization org = github.organizations().get(github_org_name);

			logger.info("org ="+ org);
				
			JsonResponse jr = github.entry()
				.uri().path("/user/memberships/orgs/" + github_org_name)
				.back()
				.method(Request.GET)
				.fetch()
				.as(JsonResponse.class);

			String role = jr.json().readObject().getString("role");
				
			logger.info("jr ="+ jr);				
			logger.info("role ="+ role);

			if (role.equals("member") || role.equals("admin") ) {
				return true;
			}
				
		} catch (Exception e) {
			logger.warn("Exception happened while trying to determine membership in github org");
			logger.warn(e.toString());
		} 
		return false;
	}


}
