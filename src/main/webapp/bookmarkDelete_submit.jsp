<%@ page import="com.org.seoulpublicwifi.dao.BookMarkDao" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");

    BookMarkDao bookMarkDAO = new BookMarkDao();
    int affected = bookMarkDAO.deleteBookMark(Integer.parseInt(id));
%>
</body>
<script>
    <%
        String text = affected > 0 ? "북마크 데이터를 삭제하였습니다." : "북마크 데이터를 삭제하지 못하였습니다.";
    %>
    alert("<%=text%>");
    location.href = "bookmarkList.jsp";
</script>
</html>