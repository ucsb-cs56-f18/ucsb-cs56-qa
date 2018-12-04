package org.pac4j.demo.spring;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.springframework.annotation.ui.RequireAnyRole;
import org.pac4j.springframework.helper.UISecurityHelper;
import org.pac4j.springframework.web.LogoutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
public class UserInterfaceApplication {

    @Value("${pac4j.centralLogout.defaultUrl:#{null}}")
    private String defaultUrl;

    @Value("${pac4j.centralLogout.logoutUrlPattern:#{null}}")
    private String logoutUrlPattern;

    @Autowired
    private Config config;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private UISecurityHelper uiSecurityHelper;

    private LogoutController logoutController;

    @PostConstruct
    protected void afterPropertiesSet() {
        logoutController = new LogoutController();
        logoutController.setDefaultUrl(defaultUrl);
        logoutController.setLogoutUrlPattern(logoutUrlPattern);
        logoutController.setLocalLogout(false);
        logoutController.setCentralLogout(true);
        logoutController.setConfig(config);
        logoutController.setDestroySession(false);
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String requestLogin(@ModelAttribute("loginuser") User user, Model model) {
        if (user == null) {
            return "login";
        }
        if (!user.hasUPfield() || !DatabaseAPI.requestLogin(user)) {
            return "login";
        }
        return "redirect:/user-id=" + user.getUserid();
    }

	public void setAttributes(Model model) {
		List<CommonProfile> profiles = uiSecurityHelper.getProfiles();
		model.addAttribute("profiles",profiles);
		model.addAttribute("profiles_s",profiles.toString());

		final J2EContext context = uiSecurityHelper.getJ2EContext();
		SessionStore<J2EContext> ss = context.getSessionStore();
		String sessionID = ss.getOrCreateSessionId(context);
        model.addAttribute("sessionId",sessionID);
	}
	  
	
    @RequestMapping("/index")
    @RequireAnyRole("ROLE_MEMBER")
    public String index(Model model) throws HttpAction {
		setAttributes(model);
        return "index";
    }



    // DONE
    @RequestMapping(value = "/create_user", method=RequestMethod.GET)
    public ModelAndView testCreateUser() {
        return new ModelAndView("create-user", "user", new User());
    }
    @RequestMapping(value="/create_user", method=RequestMethod.POST)
    public String createUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
        if (result.hasErrors() || !user.hasAllField() || DatabaseAPI.userExists(user.getUserid())) {
            return "redirect:/create_user";
        }
        DatabaseAPI.createUser(user);
        user = DatabaseAPI.findUser(user.getUserid());
        model.addAttribute("name", user.getName());
        model.addAttribute("uid", user.getUserid());
        model.addAttribute("email", user.getEmail());

        return "redirect:/user-id=" + user.getUserid();
    }

    /*@RequestMapping("/index2")
    @RequireAnyRole("ROLE_MEMBER")
    public String index2(Model model) throws HttpAction {
		setAttributes(model);
        return "index2";
    }*/
    @RequestMapping("/question")
    @RequireAnyRole("ROLE_MEMBER")
    public String question(Model model) throws HttpAction {
		setAttributes(model);
        return "question";
    }
    @RequestMapping("/profile")
    @RequireAnyRole("ROLE_MEMBER")
    public String profile(Model model) throws HttpAction {
		setAttributes(model);
        return "profile";
    }

    @RequestMapping(value="/user-id={uid}", method=RequestMethod.GET)
    public String testUserProfile(@PathVariable("uid") String uid, Model model) {
        // if (uid.equals("")) { return "redirect:/"; }
        User user = DatabaseAPI.findUser(uid);
        if (user == null) { return "redirect:/"; }

        model.addAttribute("name", user.getName());
        model.addAttribute("uid", user.getUserid());
        model.addAttribute("email", user.getEmail());

        return "user-profile";
    }

    

    @RequestMapping("/github/index")
    public String github(Model model) {
        return protectedIndex(model);
    }


    @RequestMapping("/admin/index")
    @RequireAnyRole("ROLE_ADMIN")
    public String github_admin(Model model) {
        return protectedIndex(model);
    }

	
    @RequestMapping("/member/index")
    public String github_member(Model model) {
        return protectedIndex(model);
    }


    @RequestMapping("/protected/index")
    public String protect(Model model) {
        return protectedIndex(model);
    }



    @RequestMapping("/forceLogin")
    @ResponseBody
    public void forceLogin() {
        final Client client = config.getClients().findClient(request.getParameter(Pac4jConstants.DEFAULT_CLIENT_NAME_PARAMETER));
        try {
            client.redirect(uiSecurityHelper.getJ2EContext());
        } catch (final HttpAction e) {
        }
    }

    @RequestMapping("/askquestion", method=RequestMethod.GET)
    public String askquestion(Model model) {
        return askquestion(model);
    }
    @RequestMapping(value="/ask-question", method=RequestMethod.GET)
    public String questions() {
        return "ask-question";
    }

    // DONE
    @RequestMapping(value="/ask-question", method=RequestMethod.POST)
    public ModelAndView questions(@ModelAttribute("newquestion") Question question, Model model) {
        if (!question.hasTCAfield() || !DatabaseAPI.isValueUser(question.getAskerid())) {
            return new ModelAndView("redirect:/ask-question");
        }
        Question newQuestion = new Question(question.getTitle(), question.getContent(), question.getAskerid());
        DatabaseAPI.composeQuestion(newQuestion);
        return new ModelAndView("redirect:/question-id=" + question.getQid());
    }


    // DONE
    @RequestMapping(value="/question-id={qid}", method = RequestMethod.GET)
    public ModelAndView questionPage(@PathVariable("qid") String qid, Model model) {
        if (qid.equals("")) { return new ModelAndView("redirect:/question-list"); }
        Question q = DatabaseAPI.findQuestion(qid);
        if (q == null) { return new ModelAndView("redirect:/question-list"); }
        Map<String, Object> params = new HashMap<>();
        params.put("question", q.toStringList());
        List<String> answerList = DatabaseAPI.retrieveQuestionAnswerList(qid);
        List<List<String>> as = new ArrayList<List<String>>();
        List<String> us = new ArrayList<>();
        for (String aid : answerList) {
            Answer a = DatabaseAPI.findAnswer(aid);
            as.add(a.toStringList());
            User u = DatabaseAPI.findUser(a.getAnswererid());
            us.add(u.getName());

        }
        params.put("answers", as);
        params.put("answerer", us);
        return new ModelAndView("question-page", params);
    }

    // DONE
    @RequestMapping(value="/new_answer_qid={qid}", method = RequestMethod.POST)
    public ModelAndView questionPage(@PathVariable("qid") String qid, @ModelAttribute("newanswer") Answer answer, Model model) {
        if (!answer.hasQCAfield() || !DatabaseAPI.isValueUser(answer.getAnswererid())) {
            return new ModelAndView("redirect:/question-id=" + qid);
        }
        Answer newAnswer = new Answer(qid, answer.getContent(), answer.getAnswererid());
        DatabaseAPI.composeAnswer(newAnswer);
        return new ModelAndView("redirect:/question-id=" + qid);
    }

    // DONE
    @RequestMapping(value={"/question", "/question-list", "/question-page", "/index"}, method=RequestMethod.GET)
    public ModelAndView questionList() {
        List<Question> questions = DatabaseAPI.retrieveQuestionList();
        List<List<String>> qs = new ArrayList<List<String>>();
        for (Question q : questions) {
            qs.add(q.toStringList());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("questions", qs);
        return new ModelAndView("question-list", params);
    }

    protected String protectedIndex(Model model) {
		setAttributes(model);
        return "protectedIndex";
    }

    @RequestMapping("/centralLogout")
    public void centralLogout() {
        logoutController.logout(request, response);
    }

    @ExceptionHandler(HttpAction.class)
    public void httpAction() {
        // do nothing
    }
}
