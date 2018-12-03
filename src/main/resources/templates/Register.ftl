<!DOCTYPE html>
<html>
 <head>
   <title>Sign Up</title>
   <link href="css/register.css" rel="stylesheet" />
    <#include "./partials/head.ftl" />
 </head>
 <body>
  <div id="main_container" class="pt-5">
      <h1 id="main_title">Welcome to UCSB Q&A!</h1>
      <div class="w-50 p-4 mb-3" id="reg_box">
          <form>
              <div class="form-group row">
                  <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
                  <div class="col-sm-10">
                  <input type="text" class="form-control" id="inputUsername" placeholder="Username">
                  </div>
              </div>
              <div class="form-group row">
                  <label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
                  <div class="col-sm-10">
                  <input type="text" class="form-control" id="inputEmail" placeholder="Email">
                  </div>
              </div>
              <div class="form-group row">
                  <label for="inputID" class="col-sm-2 col-form-label">UserID</label>
                  <div class="col-sm-10">
                  <input type="text" class="form-control" id="inputID" placeholder="UserID">
                  </div>
              </div>
              <div class="form-group row">
                  <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
                  <div class="col-sm-10">
                  <input type="password" class="form-control" id="inputPassword" placeholder="Password">
                  </div>
              </div>
              <a href="/home" id="reg_btn" class="btn btn-success w-50">Register</a>
          </form>
      </div>
      <a id="login_link" href="/">Already have an account? Login here</a>
  </div>
 </body>
</html>