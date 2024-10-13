<%@ page import="com.org.seoulpublicwifi.dto.BookMarkDto" %>
<%@ page import="com.org.seoulpublicwifi.dao.BookMarkDao" %>
<%@ page import="com.org.seoulpublicwifi.dao.BookMarkGroupDao" %>
<%@ page import="com.org.seoulpublicwifi.dto.BookMarkGroupDto" %>
<%@ page import="com.org.seoulpublicwifi.dao.WifiDao" %>
<%@ page import="com.org.seoulpublicwifi.dto.WifiDto" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
  <title>와이파이 정보 구하기</title>
  <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>
<body>
<h1>북마크 삭제</h1>
<%@include file="head.jsp"%>
<h4>북마크를 삭제하시겠습니까?</h4>
<%
  String id = request.getParameter("id");

  //북마크
  BookMarkDao bookMarkDAO = new BookMarkDao();
  BookMarkDto bookMarkDTO = bookMarkDAO.selectBookMarkList(Integer.parseInt(id));

  //북마크 그룹
  BookMarkGroupDao bookMarkGroupDAO = new BookMarkGroupDao();
  BookMarkGroupDto bookMarkGroupDTO = bookMarkGroupDAO.selectBookMarkGroup(bookMarkDTO.getGroupId());

  //와이파이 정보
  WifiDao wifiDAO = new WifiDao();
  WifiDto wifiDTO = wifiDAO.selectWifi(bookMarkDTO.getMgrNo());
%>
</body>
<form method="post" action="bookmarkDelete_submit.jsp">
  <table>
    <tr>
      <th>북마크 이름</th>
      <td><%=bookMarkGroupDTO.getName()%></td>
    </tr>
    <tr>
      <th>와이파이명</th>
      <td><%=wifiDTO.getXSwifiMainNm()%></td>
    </tr>
    <tr>
      <th>등록일자</th>
      <td><%=bookMarkDTO.getRegister_date()%></td>
    </tr>
    <tr>
      <td colspan="2">
        <a href="bookmarkList.jsp">돌아가기</a>
        <span style="margin: 0 10px;">|</span>
        <input type="submit" name="delete" value="삭제">
        <input type="hidden" name="id" value="<%=bookMarkDTO.getId()%>">
      </td>
    </tr>
  </table>
</form>
</html>