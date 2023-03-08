<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 목록</title>
		<link rel="stylesheet" href="../resources/css/notice.css">
	</head>
	<body>
		<h1>공지사항 목록</h1>
		<table>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성날짜</th>
					<th>첨부파일</th>
				</tr>	
			</thead>
			<tbody>
				<c:forEach items="${nList }" var="notice" varStatus="i">
				<tr>
					<td>${i.count }</td>
					<td><a href="/notice/detail.kh?noticeNo=${notice.noticeNo }">${notice.noticeTitle }</a></td>
					<td>${notice.noticeWriter }</td>
					<td>${notice.nCreateDate }</td>
					<td>
						<c:if test="${!empty notice.noticeFilename }">O</c:if>
						<c:if test="${empty notice.noticeFilename }">X</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr align="center">
					<td colspan="5">
						<c:forEach begin="${pi.startNavi }" end="${pi.endNavi }" var="p">
							<c:url var="pageUrl" value="/notice/list.kh">
								<c:param name="page" value="${p }"></c:param>
							</c:url>
							<a href="${pageUrl }">${p }</a>&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<form action="/notice/search.kh" method="get">
							<select name="searchCondition">
								<option value="all">전체</option>
								<option value="writer">작성자</option>
								<option value="title">제목</option>
								<option value="content">내용</option>
							</select>
							<input type="text" name="searchValue" placeholder="검색어를 입력해주세요">
							<input type="submit" value="검색">
						</form>
					</td>
					<td>
						<button onclick="location.href='/notice/writeView.kh'">글쓰기</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</body>
</html>