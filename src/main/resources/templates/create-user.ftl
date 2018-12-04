<!DOCTYPE HTML>
<head>
    <#include "./head.ftl" />
    <title>Create New User</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
    <#include "./navbar.ftl" />
	<h1>Create New User</h1>

    <form id="submit_form" action="/create_user" method="post">
        <label for="name">Name</label>
        <input type="text" name="name">
        <label for="email">Email</label>
        <input type="text" name="email">
        <label for="userid">UserID</label>
        <input type="text" name="userid">
        <label for="password">Password</label>
        <input type="password" name="password">
        <button type="submit">Submit!</button>
    </form>


    <#include "./footer.ftl" />
</body>
</html>
