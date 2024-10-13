<%@ page import="com.org.seoulpublicwifi.dto.BookMarkGroupDto" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.org.seoulpublicwifi.dao.BookMarkGroupDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>
<body>
<h1>북마크 그룹 추가</h1>
<%@ include file="head.jsp"%>
<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String orderNo = request.getParameter("order_no");

    BookMarkGroupDto bookMarkGroupDTO = new BookMarkGroupDto();
    bookMarkGroupDTO.setName(name);
    bookMarkGroupDTO.setOrder(Integer.parseInt(orderNo));
    bookMarkGroupDTO.setRegister_date(String.valueOf(new Timestamp(System.currentTimeMillis())));

    BookMarkGroupDao bookMarkGroupDAO = new BookMarkGroupDao();
    int affected = bookMarkGroupDAO.saveBookMarkGroup(bookMarkGroupDTO);
%>
</body>
<script>
    <%
        String text = affected > 0 ? "북마크 그룹 데이터를 추가하였습니다." : "북마크 그룹 데이터를 추가하지 못했습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmarkGroup.jsp";
</script>
</html>