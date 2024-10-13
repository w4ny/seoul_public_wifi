<%@ page import="com.org.seoulpublicwifi.dao.BookMarkDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.org.seoulpublicwifi.dto.BookMarkDto" %>
<%@ page import="com.org.seoulpublicwifi.dto.BookMarkGroupDto" %>
<%@ page import="com.org.seoulpublicwifi.dao.BookMarkGroupDao" %>
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
<h1>북마크 목록</h1>
<%@ include file="head.jsp"%>

<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>북마크 이름</th>
    <th>와이파이명</th>
    <th>등록일자</th>
    <th>비고</th>
  </tr>
  </thead>
  <tbody>
  <%
    BookMarkDao bookMarkDao = new BookMarkDao();
    if (bookMarkDao.count() == 0) {
  %>
  <tr>
    <td colspan="5">데이터가 존재하지 않습니다.</td>
  </tr>
  <% } else {
    List<BookMarkDto> bookMarkDTOList = bookMarkDao.getAllBookmarks();
    for (BookMarkDto bookMarkDTO : bookMarkDTOList) {
      BookMarkGroupDao bookMarkGroupDAO = new BookMarkGroupDao();
      BookMarkGroupDto bookMarkGroupDTO = bookMarkGroupDAO.selectBookMarkGroup(bookMarkDTO.getGroupId());

      WifiDao wifiDAO = new WifiDao();
      WifiDto wifiDTO = wifiDAO.selectWifi(bookMarkDTO.getMgrNo());
  %>
  <tr>
    <td><%=bookMarkDTO.getGroupId()%></td>
    <td><%=bookMarkGroupDTO.getName()%></td>
    <td>
      <a href="detail_wifi.jsp?mgrNo=<%=wifiDTO.getXSwifiMgrNo()%>">
        <%=wifiDTO.getXSwifiMainNm()%>
      </a>
    </td>
    <td><%=bookMarkDTO.getRegister_date()%></td>
    <td><a href="bookmarkDelete.jsp?id=<%=bookMarkDTO.getId()%>"> 삭제 </a></td>
  </tr>
  <% } } %>
  </tbody>
</table>

</body>
</html>