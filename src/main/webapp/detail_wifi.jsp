<%@ page import="java.util.List" %>
<%@ page import="com.org.seoulpublicwifi.dto.WifiDto" %>
<%@ page import="com.org.seoulpublicwifi.dao.WifiDao" %>
<%@ page import="com.org.seoulpublicwifi.dto.BookMarkGroupDto" %>
<%@ page import="com.org.seoulpublicwifi.dao.BookMarkGroupDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
  <script>
    function validateForm() {
      const selectElement = document.querySelector('select[name="gId"]');
      if (selectElement.value === "none") {
        alert("북마크 그룹이 선택되지 않았습니다.");
        return false; // 폼 전송을 중단
      }
      return true; // 폼을 전송
    }
  </script>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<%@include file="head.jsp"%>
<body>
<%
  WifiDao wifiDAO = new WifiDao();
  String distance = request.getParameter("distance");
  String mgrNo = request.getParameter("mgrNo");

  System.out.println("Distance: " + distance);
  System.out.println("Manager Number: " + mgrNo);

  if (distance == null || mgrNo == null) {
    out.println("<script>alert('거리 또는 관리번호가 누락되었습니다.'); location.href='previous_page.jsp';</script>");
    return; // 페이지 실행 중지
  }

  List<WifiDto> wifiDTOList = wifiDAO.selectWifiList(mgrNo, Double.parseDouble(distance));

  BookMarkGroupDao bookMarkGroupDAO = new BookMarkGroupDao();
  List<BookMarkGroupDto> bookMarkGroupDTOS = bookMarkGroupDAO.getAllBookMarkGroups();
  request.setAttribute("bookmarkList", bookMarkGroupDTOS);
%>

<form method="post" action="bookmarkAdd.jsp" id="bookmarkList" onsubmit="return validateForm();">
  <select name="gId">
    <option value="none" selected>북마크 그룹 이름 선택</option>
    <% for (BookMarkGroupDto bookMarkGroupDTO : bookMarkGroupDTOS) {%>
    <option value="<%=bookMarkGroupDTO.getId()%>">
      <%=bookMarkGroupDTO.getName()%>
    </option>
    <% } %>
  </select>
  <input type="submit" value="즐겨찾기 추가하기">
  <input type="hidden" name="mgrNo" value="<%=mgrNo%>">
</form>

<table>
  <% for (WifiDto wifiDTO : wifiDTOList) {%>
  <tr>
    <th>거리(km)</th>
    <td><%=wifiDTO.getDistance()%></td>
  </tr>
  <tr>
    <th>관리번호</th>
    <td><%=wifiDTO.getXSwifiMgrNo()%></td>
  </tr>

  <tr>
    <th>자치구</th>
    <td><%=wifiDTO.getXSwifiWrdofc()%></td>
  </tr>
  <tr>
    <th>와이파이명</th>
    <td><%=wifiDTO.getXSwifiMainNm()%></td>
  </tr>

  <tr>
    <th>도로명 주소</th>
    <td><%=wifiDTO.getXSwifiAdres1()%></td>
  </tr>
  <tr>
    <th>상세 주소</th>
    <td><%=wifiDTO.getXSwifiAdres2()%></td>
  </tr>
  <tr>
    <th>설치 위치(층)</th>
    <td><%=wifiDTO.getXSwifiInstlFloor()%></td>
  </tr>
  <tr>
    <th>설치 기관</th>
    <td><%=wifiDTO.getXSwifiInstlMby()%></td>
  </tr>
  <tr>
    <th>설치 유형</th>
    <td><%=wifiDTO.getXSwifiInstlTy()%></td>
  </tr>
  <tr>
    <th>서비스 구분</th>
    <td><%=wifiDTO.getXSwifiSvcSe()%></td>
  </tr>
  <tr>
    <th>망 종류</th>
    <td><%=wifiDTO.getXSwifiCmcwr()%></td>
  </tr>
  <tr>
    <th>설치 년도</th>
    <td><%=wifiDTO.getXSwifiCnstcYear()%></td>
  </tr>
  <tr>
    <th>실내 외 구분</th>
    <td><%=wifiDTO.getXSwifiInoutDoor()%></td>
  </tr>
  <tr>
    <th>WIFI 접속 환경</th>
    <td><%=wifiDTO.getXSwifiRemars3()%></td>
  </tr>
  <tr>
    <th>x좌표</th>
    <td><%=wifiDTO.getLat()%></td>
  </tr>
  <tr>
    <th>y좌표</th>
    <td><%=wifiDTO.getLnt()%></td>
  </tr>
  <tr>
    <th>작업일자</th>
    <td><%=wifiDTO.getWorkDttm()%></td>
  </tr>
  <% } %>
</table>
</body>
</html>
