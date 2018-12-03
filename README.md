# spring-boot-oauth-demo02

This is a refactored version of code from:

https://github.com/ucsb-cs56-pconrad/spring-boot-github-oauth-demo01

Both the code and the explanation in the README.md are cleaned up a bit.

# Configuring the application

The configuration steps need to be followed VERY CAREFULLY or it will not work.


## Running on localhost with https

NOTE: *If you are running *Java version 11* on your machine, you will have trouble with this version 
of the app.  Instead, use this version: <https://github.com/ucsb-cs56-f18/spring-boot-github-oauth-demo02-java11>

You can check your java version via `javac -version`

1. If you want to be able to test on localhost with https, configure the key store.  
   * This is a self-signed certificate, and does NOT offer security; it is only for testing purposes
   * When running with `https` with a self-signed certificate, you will
      likely get browser warnings that the site may be unsafe.  It is ok
      to proceed to the site in spite of these warnings.
   * Run this, noting how to respond to the prompts below:
      ```
      cd src/main/resources
      keytool -genkey -alias mydomain -keyalg RSA -keystore KeyStore.jks -keysize 2048
      ```
      * Use `password` as the password (or else change the hardcoded value `password` in the file `src/main/resources/application.properties`

      * For all the other values, you can just take the defaults (except you have to answer "yes" to the question where it asks you if the values are correct.)

2. Copy the file `localhost.json.SAMPLE` to `localhost.json`
   * Double check that `localhost.json` is in your `.gitignore` file.  The reason
       there is a `localhost.json.SAMPLE` that does NOT have real client secrets in it
       is so that these won't accidentally leak to being stored in a github repo.
   * In the next step, you'll edit the values in this file.

2. Create a Github OAuth app to get the client-id and client-secret values, and put those values into `localhost.json`.  To create a Github OAuth app:
   * Login to Github, and go to Settings under your personal account
   * Navigate to Developer Settings (Or just go to: <https://github.com/settings/developers>)
   * Click the "New OAuth App" button
   * Give the app a name that matches your repo name, plus "test on localhost" (for example `GauchoTool test on localhost`).  It is not required that this name match exactly, but you will *want to be able to find it later* to be sure that you are debugging the settings of the correct app
   * For `Homepage URL` enter `https://127.0.0.1:8082`
   * For `Application description`, you may put in anything you want.
   * For `Authorization callback URL` you must put in this.  Be sure it matches *exactly*, including upper/lower case: `https://127.0.0.1:8082/callback?client_name=GitHubClient`
   * Click `Register Application`
   * Now you have the `Client ID` and `Client Secret` values you need for the next step.
   
3. Carefully edit the `Client ID` and `Client Secret` into the `localhost.json` file.

3. Run `. env.sh`
   * This defines the environment variable `SPRING_APPLICATION_JSON` which is
      an environment variable that can override application setting values
      in the `src/main/resources/application.properties` file.

4. Run `mvn spring-boot:run`


## Running on Heroku

To run on heroku, you need to create a DIFFERENT OAuth app.  That's because the
callback URL is different when running on Heroku.

1. Create a heroku application.  You can do this with the Heroku Dashboard online,
   or by doing `heroku login` and then `heroku create app-name`.

   If you choose `app-name`, then your app's url will be `https://app-name.herokuapp.com`

   You'll need that URL below.  Everywhere you see `app-name.herokuapp.com` in these
   insructions,
   you must substitutee your actual
   application's name.
   
2. Copy the file `heroku.json.SAMPLE` to `heroku.json`
   * Double check that `heroku.json` is in your `.gitignore` file.

2. Create a separate Github OAuth app to get the client-id and client-secret values, and put those values into `heroku.json`.  To create a Github OAuth app:
   * Login to Github, and go to Settings under your personal account
   * Navigate to Developer Settings (Or just go to: <https://github.com/settings/developers>)
   * Click the "New OAuth App" button
   * Give the app a name that matches your repo name, plus "on heroku" (for example `GauchoTool test on localhost`).  It is not required that this name match exactly, but you will *want to be able to find it later* to be sure that you are debugging the settings of the correct app
   * For `Homepage URL` enter `https://app-name.herokuapp.com` (substituting your REAL app name.)
   * For `Application description`, you may put in anything you want.
   * For `Authorization callback URL` you must put in this.  Be sure it matches *exactly*, including upper/lower case: `https://app-name.herokuapp.com/callback?client_name=GitHubClient`
   * Click `Register Application`
   * Now you have the `Client ID` and `Client Secret` values you need for the next step.
   
3. Carefully edit the `Client ID` and `Client Secret` into the `heroku.json` file.

3. Run `./setHerokuEnv.py --app appname`
   * `appname` should be your app name on Heroku (without the `.herokuapp.com` part)
   * This is some Python code that reads from `heroku.json` and for each variable,
      runs the command `heroku config:set variable=value`

4. As usual, set the heroku application name in the `pom.xml` (in the usual way).

5. Run `mvn heroku:deploy`


# What is this?

This is a demo of an application that illustrates github OAuth with
spring boot.   That is, it allows you to implement logging in with a username/password
with a good tradeoff between effort and security; not much effort, very good security.

# Do I really need this?

If you have an app that only PROVIDES information, and does not allow users to STORE information, then you *might not need this*.

If your app DOES allow users to STORE information in a database of any kind, then you DO need this.  Having an app on the public web in which users can store information, without logging in or authenticating, presents a security risk.  In particular:
* spammers may store all kinds of unsavory content in your database, including
    advertisements for illegal products, offensive material, etc.
* hackers may launch a "denial of service" attack by trying to just fill your database with content as quickly as possible.

The fact that your app requires not authentication may embolden the hackers, since there is no accountability.

* Requiring them to register with, for example, a github id, provides at
   least a moderate level of accountability.
* Requiring, for updating content, that their account be specifically *approved* by one of your group members (acting as an admin for the app) provides even more accountability.

The code in this app allows you to do exactly that, with minimum effort.

# What capabilities are demonstrated in this example app?

The code here allows you to delegate certain features to Github so that you don't have
to implement them yourself:
* That means that you don't have to implement the function of creating a user account.
   Someone that wants to use your account just creates a Github account and uses that
   to login.
* You don't have to keep track of user's passwords.  Github does that for you.
* Unless you want to associate data with specific users (e.g. have user profiles,
  have user's "own" certain data within the application), you don't even need to keep
  track of users in a database.   (If you want to associate data with users, you
  can do that, but you'll need to create your own table. You'll only store the username
  as the "key" for that table to lookup users, and then associate whatever data you want
  with each user.)
* Without having to do much extra coding, you can establish four permission levels:

   * Pages that anyone on the web can access without logging in
   * Pages that only folks that have logged in with a github account can access
   * Pages that only folks belonging to a certin github organization (one that you specify)
      can access.  This allows you to control who can, and who cannot use those
      features in your webapp; users have to ask one of your organization admins to
      add them to the organization; and then they can access those pages.
   * Pages that only folks that are `owners` (`admin` users) in that organization
      can access.
      
   That means that, at least when you are at the "minimum viable product" stage,
   the features of adding users to groups, deleting users from groups,
   and designating certain users as admin users, etc. can all be delegated to github;
   you don't have to build those into your web app.

# What you DO have to do

There are lots of things you don't have to do (store passwords, set up account creation, set up a "forgot password" function, verifying
user emails, etc.)

But there are some things you DO have to do.

## Stuff you do before you start coding

1.  You need to create an OAuth application under your github account, which sets up
    a `client_id` and `client_secret`, two hexadecimal numbers that are like a username/password combination that gives you permission to communicate with github's authentication
    servers.  These pieces of data need to be treated carefully; in particular, they should
    NOT be stored in a github repo (even a private one).   Instead, you store these pieces
    of information either in:
    * local files that are in your `.gitignore`
       * Typical names for these files are `app.json`, `localhost.json`
    * in configuration variables that you set on the Heroku dashboard.

    You set up a Github OAuth application by going to the settings menu for your
    account on Github, selecting `Developer Settings` from the menu at left (it's way
    down the page) and then choosing `Create OAuth App`.  You'll need the URL of the
    application, and the `Callback URL`.   Those steps are discussed in more detail
    below.

2.  If you want to have multiple permission levels (i.e. more than just "logged in or not")
    then you need to create a github organization.  You do this by selecting the
    `New Organization` button from the dropdown menu (you can also just visit:
    <https://github.com/organizations/new>.)    
    
    NOTE: You need to remove third-party restrictions in order for this to work (or else figure out how to authorize
    your specific application).  Go to <https://github.com/organizations/ORG-NAME-HERE/settings/oauth_application_policy>,
    substituting your organization's name in place of `ORG-NAME-HERE`

## The code you need (examples in this repo)

1.  You need to make sure that your application includes the `Pac4J` dependencies
    in the `pom.xml` file, and that `Pac4J` is configured properly.

    See the `pom.xml` file in this repo for the `Pac4J` related dependencies.


2.  You need to configure the Pac4J client.

    The java source file that configures `Pac4J` with the values of `client_id` and `client_secret`
    is in  `Pac4JConfig.java`.

    This is also where we set up the OAuth *scope*, which tells Github (and the end user,
    when they first login to our app via Github), what information we are allowed to see
    about the user.   The *scope* can restrict our access to the user's information to
    a very small amount of information, or at the other extreme, it can give us access
    to do anything and everything the user can do on github.com, i.e. actually *be* that
    user.   When doing OAuth with Facebook or Google, scope works in a similar way;
    the scope, for example, controls whether or not an app that uses Facebook OAuth
    can post things to the User's feed on Facebook.

    We also set up the callback url (in this case `/callback`) in this file.

    Finally, in this part of the code, we set up an `AuthorizationGenerator<GitHubProfile>`,
    which is a templated class that works with the custom roles that we set up in the file
    `CustomRoles.java` (i.e. `ROLE_ADMIN` and `ROLE_MEMBER`.)


3.  You need some code that sets up the custom roles that you want to have in your
    application, and defines what it means to have those roles.  That code is in `CustomRoles.java`.

    In this code, we set up two roles:

    * `ROLE_MEMBER` for users that belong to our designated github organization
    * `ROLE_ADMIN` for users that have `owner` permission in our designated github organization

    The two member functions `is_admin` and `is_member` use the Github API to check for membership
    or owner status in the designated github org.
    
4.  You need a `CustomAuthorizer`, i.e. a class that extends `ProfileAuthorizer<CommonProfile>`, which is an abstract class.

   The single abstract method of that class is:  

   ```
      protected abstract boolean
      isProfileAuthorized(WebContext context, U profile)
   ```

   which is supposed to return true or false based on "whether a specific profile is authorized".

   In this context, `CommonProfile` means the information associated with
   a given user that we have authenticated; and the `CommonProfile` is
   information we can get about a user regardless of which login method
   that used (i.e. whether its Github, or Facebook, or Google, or
   whatever.)

   See:
   * [`ProfileAuthorizer` javadoc](http://static.javadoc.io/org.pac4j/pac4j-core/1.9.0/org/pac4j/core/authorization/authorizer/ProfileAuthorizer.html)
   * [`CommonProfile` javadoc](http://static.javadoc.io/org.pac4j/pac4j-core/1.9.0/org/pac4j/core/profile/CommonProfile.html)

   Our code simply checks the values of the `is_admin` and `is_member` methods we defined in `CustomRoles`

5.  You need some code that sets up the URLs in your application with various
   levels of permission.

   This is done in the file `SecurityConfig.java`.  More detail on that file appears later in this `README.md`

# Future work

Optionally, you might also have the following.  These are not yet implemented
in this version of the code, but represent things we might add in future versions:

* Code to put a login/logout button in your navigation bar
* Code to put the username and user's avatar in your navigation bar
* Code to put an indication of the user's level of access in your navigation bar
* Code so that when showing the user various links to pages, we hide the links
  (or grey them out) if the user is not authorized for that function of the app

Now, let's walk through the details.




# Based on code from:

* <https://github.com/pac4j/spring-webmvc-pac4j-boot-demo>
* <https://github.com/pconrad/try2-pac4j-sb-oauth>


# Configuring the KeyStore

<http://pconrad-webapps.github.io/topics/spring_boot_https/>

# Details about a few other source code files

## `MyErrorController.java`

This file simply allows us to customize the pages that show up when various kinds of errors occur, such as:

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `500 Internal Server Error`

There are many other errors, and it would not be reasonable to create
custom pages for each one.  (See, for example: <https://www.restapitutorial.com/httpstatuscodes.html>.   We may not even need that here, and it might
be better to remove this from the tutorial to simplify it.  Maybe that goes
better in its own tutorial.

## `SecurityConfig.java`

In this file, we configure the url patterns that need to be authenticated
before they may be accessed.  For example, in the method
`addInterceptors`, we find this code:


```
SecurityIntercepter gh_admin =
	new SecurityInterceptor(config, "GitHubClient", "admin");	    
registry.addInterceptor(gh_admin).addPathPatterns("/admin/*");
```

This sets up a security intercepter that requires us to be logged
into github in an admin role.  What "admin" role is defined in
Pac4JConfig.java (which may not be the best place for it, as
we've discussed.)

Similarly, we have a custom role defined and we can restrict
certain URL patterns to that role as well, and finally restrict
some pages to only be available to when users are logged in:

```
SecurityIntercepter gh_custom =
	new SecurityInterceptor(config, "GitHubClient", "custom");	    
registry.addInterceptor(gh_admin).addPathPatterns("/custom/*");

SecurityIntercepter gh_loggedIn =
	new SecurityInterceptor(config, "GitHubClient");	    
registry.addInterceptor(gh_admin).addPathPatterns("/github/*");
		
```

This file extends `WebMvcConfigurerAdapter`
* [`WebMvcConfigurerAdapter` javadoc](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurerAdapter.html)
* import statement:
   ```
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
   ```

## `SpringBootPac4jDemo.java`

This is the main for our app, and it looks pretty much like every other Spring Boot webapp main file

## `UserInterfaceApplication.java`

This is the `@Controller` and it just sets up the routes to the pages.

# Useful resources:

* <https://www.pac4j.org/docs/clients.html#3-the-callback-url>


The callback url should apparently be defined this way:

```
FacebookClient facebookClient = new FacebookClient(fbKey, fbSecret);
TwitterClient twitterClient = new TwitterClient(twKey, twSecret);
Config config = new Config("http://localhost:8080/callback", facebookClient, twitterClient);
```

> In that case, the callback URL of the FacebookClient is
> `http://localhost:8080/callback?client_name=FacebookClient` and the
> callback URL of the TwitterClient is
> `http://localhost:8080/callback?client_name=TwitterClient`.


# Original README contents:

<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-spring-webmvc.png" width="300" />
</p>

This `spring-webmvc-pac4j-boot-demo` project is a Spring Boot application secured by the [spring-webmvc-pac4j](https://github.com/pac4j/spring-webmvc-pac4j) security library with various authentication mechanisms: Facebook, Twitter, form, basic auth, CAS, SAML, OpenID Connect, JWT...

## Run and test

You can build the project and run it on [http://localhost:8080](http://localhost:8080) using the following commands:

    cd spring-webmvc-pac4j-boot-demo
    mvn clean compile exec:java

For your tests, click on the "Protected url by **xxx**" link to start the login process with the **xxx** identity provider...
