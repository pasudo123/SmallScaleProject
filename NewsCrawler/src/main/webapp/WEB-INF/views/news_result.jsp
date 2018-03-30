<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
    </HEAD>
    <BODY>
    	<div class="newsContent">
        	<div>
            	<div>
					${newsGather.title}
                </div>
             </div>
       		<div>${newsGather.content}</div>
   		</div>
             
      	<div class="newsComment">
			<ul>
				<c:forEach items="${newsGather.comment}" var="news">
					<li>
						<div class="commentDate">
							${news.date}
						</div>
						<div class="comment">
	                     	${news.comment}
	                  	</div>
	                  	<hr>
	              	</li>
              	</c:forEach>
          	</ul>
      	</div>
    </BODY>
</HTML>