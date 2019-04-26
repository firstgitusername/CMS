<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div class="banner-roll">
    <div class="banner-item">
        <#if model??>
               <#list model as m>
               <div class="item" style="background-image: url(${m.value});"></div>
               </#list>
        </#if>
    </div>
    <div class="indicators"></div>
</div>
</body>
</html>