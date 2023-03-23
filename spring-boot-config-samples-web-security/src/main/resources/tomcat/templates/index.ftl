<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
    <script src="../static/js/index.js"></script>
</head>
<body>
<div style="text-align:right;margin-right: 5%">
    <a href="/logout" type="button">退出登录</a>
</div>
<div style="text-align: center">
    <h1>Congratulations <span style="font-weight: bold; color: red">${username}</span>, login successfully!</h1>
    <img alt="tomcat" title="tomcat" src="../static/images/tomcat.png"/>
</div>
<br/>
<br/>
<ul style="text-align:left;margin-left: 45% ">
    <h2>UserInfo: </h2>
    <li><span style="font-weight: bold; color: darkgreen">ID:</span> ${id}</li>
    <br/>
    <li><span style="font-weight: bold; color: red">username:</span> ${username}</li>
    <br/>
    <li><span style="font-weight: bold; color: dodgerblue">nickname:</span> ${nickname}</li>
    <br/>
</ul>
</body>
</html>