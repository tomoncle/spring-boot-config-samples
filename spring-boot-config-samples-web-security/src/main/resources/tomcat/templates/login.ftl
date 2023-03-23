<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
    <script src="../static/js/index.js"></script>
</head>
<body style="text-align: center">
<img alt="tomcat" title="tomcat" src="../static/images/tomcat.png"/>
<h1>登录</h1>
<div style="background-image: inherit;">
    <form action="/sign-in" method="post" style="background: inherit">
        username: <input type="text" name="username" placeholder="username"/><br>
        password: <input type="password" name="password" placeholder="password"/><br><br>
        <button type="submit" value="提交">提交</button>
    </form>
</div>
</body>
</html>