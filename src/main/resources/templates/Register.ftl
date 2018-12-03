<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<html>
 <head>
   <title>Sign Up</title>
    <#include "head.ftl"/>
 </head>
 <body>
   
   <h1>Sign Up</h1>
  <p>Enter username:</p>
  <input type= "text" name="textbox1" size="50" />
  <p>Enter pass:</p>
  <input type= "text" name="textbox1" size="50" />
  <p></p>
  <a href="/login"><button auto="autofocus">Register</button></a>
 <#include "footer.ftl" />
 </body>
</html>