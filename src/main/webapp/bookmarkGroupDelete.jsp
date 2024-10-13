<%@ page import="com.org.seoulpublicwifi.dao.BookMarkGroupDao" %>
<%@ page import="com.org.seoulpublicwifi.dto.BookMarkGroupDto" %>

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
<div>
  <h4>북마크를 삭제하시겠습니까?</h4>
</div>
<%
  String id = request.getParameter("id");

  BookMarkGroupDao bookMarkGroupDAO = new BookMarkGroupDao();
  BookMarkGroupDto bookMarkGroupDTO = bookMarkGroupDAO.selectBookMarkGroup(Integer.parseInt(id));
%>

<form method="post" action="bookmarkGroupDelete_submit.jsp">
  <table>
    <tr>
      <th>북마크 이름</th>
      <td><%=bookMarkGroupDTO.getName()%></td>
    </tr>
    <tr>
      <th>순서</th>
      <td><%=bookMarkGroupDTO.getOrder()%></td>
    </tr>
    <tr>
      <td colspan="2">
        <a href="bookmarkList.jsp">돌아가기</a>
        <span style="margin: 0 10px;">|</span>
        <input type="submit" name="delete" value="삭제">
        <input type="hidden" name="id" value="<%=bookMarkGroupDTO.getId()%>">
      </td>
    </tr>
  </table>
</form>

</body>
</html>