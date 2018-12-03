<!DOCTYPE html>
<html>
  <head>
    <title>Spring Boot Freemarker Demo</title>
    <#include "head.ftl" />
  </head>
  <body>
    <#include "navbar.ftl" />

    <h1>Index</h1>

    <table>
      <tr><th>Link</th><th>Explanation</th></tr>
      <tr><td><a href="/">/</a></td><td>Home</td></tr>
      <tr><td><a href="/index.html">/index.html</a></td><td>&nbsp;</td></tr>
      <tr><td><a href="/github/index.html">/github/index.html</a></td><td>github index</td></tr>
      <tr><td><a href="/admin/index.html">/admin/index.html</a></td><td>admin index</td></tr>
      <tr><td><a href="/member/index.html">/member/index.html</a></td><td>member</td></tr>
      <tr><td><a href="/protected/index.html">/protected/index.html</a></td><td>protected index</td></tr>
      <tr><td><a href="/forceLogin?client_name=GitHubClient">/forceLogin?client_name=GitHubClient"</a></td><td>force login</td></tr>
      <tr><td><a href="/logout">/logout</a></td><td>local logout</td></tr>
      <tr><td><a href="/centralLogout">/centralLogout</a></td><td>central logout</td></tr>
    </table>

    <#include "footer.ftl" />
    
  </body>
</html>
