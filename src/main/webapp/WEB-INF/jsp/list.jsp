<%--
  Created by IntelliJ IDEA.
  User: Hexun
  Date: 2017/8/10 0010
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>
    <%@include file="common/head.jsp"%><!--静态引入bootstrap-->
    <%@include file="common/tag.jsp"%><!--引入jstl-->
</head>
<body>
    <div class="container">
           <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h2>秒杀列表</h2>
                </div>
               <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>名称</th>
                                <th>库存</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>创建时间</th>
                                <th>详情页</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sk" items="${list}">
                                <tr>
                                    <td>${sk.name}</td>
                                    <td>${sk.number}</td>
                                    <td><fmt:formatDate value="${sk.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/></td><!--HH表示二十四小时制-->
                                    <td><fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail">link</a>
                                    </td>
                                </tr>

                            </c:forEach>

                        </tbody>
                    </table>
               </div>
           </div>

    </div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
