package org.pac4j.demo.spring;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomAuthorizer extends ProfileAuthorizer<CommonProfile> {
   
	private Logger logger = LoggerFactory.getLogger(CustomAuthorizer.class);
	
    @Override
    public boolean isAuthorized(final WebContext context,
				final List<CommonProfile> profiles) throws HttpAction {
        return isAnyAuthorized(context, profiles);
    }

    @Override
    public boolean isProfileAuthorized(final WebContext context,
				       final CommonProfile profile) {
        if (profile == null) {
			logger.info("PROFILE WAS NULL!!!!!!!");
            return false;
        } 
		logger.info("profile.getUsername()="+profile.getUsername());
		
		// NOTE: THIS IS WHERE YOU hard code the criteria you want
		// in terms of who is authorized to access your application
		
        return ( CustomRoles.isMember(profile) ||
				 CustomRoles.isAdmin(profile) );
    }
}
