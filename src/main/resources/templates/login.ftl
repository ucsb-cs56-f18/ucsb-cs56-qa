<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/head.ftl" />
    <link href="css/login.css" rel="stylesheet" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UCSB Q&A</title>
</head>
<body>
    <div class="container-fluid p-5" id="main_container">
        <h1 id="main_title">UCSB Q&A</h1>
        <div class="w-50 p-4 mb-3" id="login_box">
            <form>
                <div class="form-group row">
                    <label for="inputUsername" id="username_label" class="col-sm-2 col-form-label">Username</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputUsername" placeholder="Username">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" id="password_label" class="col-sm-2 col-form-label">Password</label>
                    <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword" placeholder="Password">
                    </div>
                </div>
                <a href="/home" id="login_btn" class="btn btn-success w-50">Login</a>
            </form>
        </div>
        <a id="reg_link" href="/register">Don't have an account? Click here to register!</a>
        <div class="mobilehidden">
            <img id="qa" src="img/qa.png" alt="qa">
            <img id="palm_tree" src="img/palm_tree.png" alt="palm tree">
        </div>
    </div>
</body>
</html>
