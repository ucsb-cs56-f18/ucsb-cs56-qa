<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <#include "./head.ftl" />
    <link href="css/login.css" rel="stylesheet" />
    <title>UCSB Q&A</title>
</head>
<body>
    
    <div class="container-fluid p-5" id="main_container">
        <h1 id="main_title">UCSB Q&A</h1>
        <div class="w-50 p-4 mb-3" id="login_box">
            <form action="/login" method="post">
                <div class="form-group row">
                    <label for="inputUsername" class="col-sm-2 col-form-label">UserID</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" name="userid" id="inputUsername" placeholder="UserID">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
                    <div class="col-sm-10">
                    <input type="password" class="form-control" name="password" id="inputPassword" placeholder="Password">
                    </div>
                </div>
                <p> <a href = "/login" type="submit" id="login_btn" class="btn btn-success w-50">Login</a> </p>
            </form>
        </div>
        <a id="reg_link" href="/create_user">Don't have an account? Click here to register!</a>
        <p> </p>
        <a id="reg_link" href="/forceLogin?client_name=GitHubClient">Authorize!</a>
        
        
        <img id="qa" src="img/qa.png" alt="qa">
        <img id="palm_tree" src="img/palm_tree.png" alt="palm tree">
    </div>
    <#include "./footer.ftl" />
</body>
</html>
