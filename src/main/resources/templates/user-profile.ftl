<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./head.ftl" />
    <title>User Profile Test</title>
</head>
<body>
    <#include "./navbar.ftl" />
    <h1>User Profile Test</h1>

    <h2>Retrieved User Profile:</h2>
    <p>Name = ${name}</p>
    <p>UID = ${uid}</p>
    <p>Email = ${email}</p>

    <h1>End</h1>
    <#include "./footer.ftl" />
</body>
</html>
