<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
<h1>测试List</h1>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>成绩</td>
        <td>生日</td>
    </tr>
    <#list stus as stu>
     <tr>
        <td>${stu_index+1}</td>
        <td>${stu.name}</td>
        <td>${stu.age}</td>
        <td>${stu.cj}</td>
        <td>${(stu.birthday)?string("yyyy年MM月dd日")}</td>
     </tr>
    </#list>
</table>
<h1>测试map</h1>
<h2>方式一</h2>
姓名：${stuMap['stu1'].name}</br>
成绩：${stuMap['stu1'].cj}</br>
<h2>方式二</h2>
姓名：${stuMap.stu2.name}</br>
成绩：${stuMap.stu2.cj}</br>
<h2>方式三</h2>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>成绩</td>
    <#--<td>生日</td>-->
    </tr>
    <#list stuMap?values as v>
     <tr>
         <td>${v_index+1}</td>
         <td>${v.name}</td>
         <td>${v.age}</td>
         <td <#if (v.cj > 9)>style="background: #bd94ff"</#if>>${v.cj}</td>
     <#--<td>${stu.birthday}</td>-->
     </tr>
    </#list>
</table>
<h1>空值判断</h1>
<h2>方式一</h2>
<#if name??>
    Hello ${name}!
</#if>
<h2>方式二</h2>
Hello ${name!'明天'}!
<h1>内建函数</h1>
集合大小：${stus?size}
数字：${point?c}
<#assign test="{'bank':'工商银行','account':'10101920201920212'}"><#assign data=test?eval/></br>
银行：${data.bank} 卡号:${data.account}
</body>
</html>