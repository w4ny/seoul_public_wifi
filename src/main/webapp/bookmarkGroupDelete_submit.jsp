<%@ page import="com.org.seoulpublicwifi.dao.BookMarkGroupDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
  <title>와이파이 정보 구하기</title>
  <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>
<body>
<h1>북마크 그룹 삭제</h1>
<%@ include file="head.jsp"%>
<%
  request.setCharacterEncoding("UTF-8");

  String id = request.getParameter("id");

  BookMarkGroupDao bookMarkGroupDAO = new BookMarkGroupDao();
  int affected = bookMarkGroupDAO.deleteBookMarkGroup(Integer.parseInt(id));
%>
</body>
<script>
  <%
      String text = affected > 0 ? "북마크 그룹 데이터를 삭제하였습니다." : "북마크 그룹 데이터를 삭제하지 못했습니다.";
  %>
  alert("<%= text %>");
  location.href = "bookmarkGroup.jsp";
</script>
</html>