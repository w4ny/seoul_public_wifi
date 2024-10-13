<%@ page import="com.org.seoulpublicwifi.dao.BookMarkDao" %>
<%@ page import="com.org.seoulpublicwifi.dto.BookMarkDto" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>
<body>
<h1>북마크 그룹 추가</h1>
<%
    request.setCharacterEncoding("UTF-8");

    String mgrNo = request.getParameter("mgrNo");
    String gId = request.getParameter("gId");

    // 값이 none일 경우 이전 페이지로 이동
    if (gId.equals("none")) {
        response.sendRedirect(request.getHeader("Referer"));
        return;
    }

    BookMarkDto bookMarkDTO = new BookMarkDto();
    bookMarkDTO.setMgrNo(mgrNo);
    bookMarkDTO.setGroupId(Integer.parseInt(gId));

    BookMarkDao bookMarkDAO = new BookMarkDao();
    int affected = bookMarkDAO.insertBookMark(bookMarkDTO);
%>
</body>
<script>
    <%
        String text = affected > 0 ? "북마크 데이터 추가하였습니다." : "북마크 데이터 추가 실패하였습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmarkList.jsp";
</script>
</html>