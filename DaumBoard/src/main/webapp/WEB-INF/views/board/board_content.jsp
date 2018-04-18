<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardContentView</title>

<script type="text/javascript">
	// 목록보기
	function showBoardList(){
		location.href="/board/boardList";
	}
</script>

</head>
<body>
	<center>
		<b>게시판 글 읽기</b> <br> <br> <br>
		
		<form action="../boardModify" method="POST">
			<table width="600" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>

				<tr height="30">
					<td width="100" bgcolor="#f2f2f2" align="right">작성자 &nbsp;</td>
					<td width="500">&nbsp;${content.contentWriter}</td>
				</tr>

				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>

				<tr height="30">
					<td width="100" bgcolor="#f2f2f2" align="right">제목 &nbsp;</td>
					<td width="500">&nbsp;<input name="title" type="text" size="80" value="${content.contentTitle}"></td>
				</tr>

				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>

				<tr height="30">
					<td width="100" bgcolor="#f2f2f2" align="right">글내용 &nbsp;</td>
					<td width="500">&nbsp; <textarea name="content" cols="80" rows="20">${content.contentDetail}</textarea></td>
				</tr>

				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>
				
				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>
			</table>
			<br>
			
			<input type="hidden" name="contentNum" value="${content.contentNum}">
			<input type="submit" value="수정">
		</form>
			
			<form action="../boardDelete" method="GET">
				<input type="hidden" name="contentNum" value="${content.contentNum}">
				<input type="submit" value="삭제">
			</form>
			<input type="button" value="목록보기" onclick="showBoardList()">
	</center>
</body>
</html>