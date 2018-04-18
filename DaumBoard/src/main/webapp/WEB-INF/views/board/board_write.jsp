<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardWriteForm</title>

<script type="text/javascript">
	function goBack(){
		window.history.back();
	}
</script>
</head>
<body>
	<center>
		<b>게시판 글쓰기 페이지</b> <br> <br> <br>
		<form action="boardWrite" method="POST">
			<table width="600" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>

				<tr height="30">
					<td width="100" bgcolor="#f2f2f2" align="right">작성자 &nbsp;</td>
					<td width="500">&nbsp; <input name="writer" type="text" size="15"></td>
				</tr>

				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>

				<tr height="30">
					<td width="100" bgcolor="#f2f2f2" align="right">제목 &nbsp;</td>
					<td width="500">&nbsp; <input name="title" type="text" size="80"></td>
				</tr>

				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>

				<tr height="30">
					<td width="100" bgcolor="#f2f2f2" align="right">글내용 &nbsp;</td>
					<td width="500">&nbsp; <textarea name="content" cols="80" rows="20"></textarea></td>
				</tr>

				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>
				
				<tr>
					<td colspan="2" bgcolor="slategray"></td>
				</tr>
			</table>
			
			<br> 
			<input type="submit" value="작성완료">
			<input type="button" value="취소" onclick="goBack()">
		</form>
	</center>
</body>
</html>