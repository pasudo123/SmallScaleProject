<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
	<head>
		<title>Main</title>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
		<STYLE>
			a{
				text-decoration:none;
			}
		
			div.keywordWrapper{
				width:100%;
				margin:0 auto;
				height:auto;
				text-align:center;
			}

			canvas#dailyChart{
				border:1px solid black;
				width:350px;
				height:350px;
				display:block;
			}
			canvas#weeklyChart{
				border:1px solid black;
				width:350px;
				height:350px;
			}
			canvas#monthlyChart{
				border:1px solid black;
				width:350px;
				height:350px;
			}
		</STYLE>
	</head>
	
	<body>
	
		<p><a href="/keyword">일간 / 주간 / 월간 상위 키워드</a></p>
		<div class="keywordWrapper">
		<canvas id="dailyChart"></canvas>
		<canvas id="weeklyChart"></canvas>
		<canvas id="monthlyChart"></canvas>
		</div>
		
		<script>
			var daily = ${'daily'};
			console.log(">>>");
			console.log(daily);
			
			var ctx = document.getElementById('dailyChart').getContext('2d');
			var chart = new Chart(ctx,{
				type : 'doughnut',
				data : {
					labels : ["red", 
							  "blue",
							  "green"],
					datasets : [{
						label : "일간 상위 키워드",
						data : [10,10,10],
						backgroundColor: ["rgb(255, 99, 132)","rgb(54, 162, 235)","rgb(255, 205, 86)"]
					}]
				}
			});
			
			document.getElementById('dailyChart').style.width="350px";
			document.getElementById('dailyChart').style.height="350px";
			document.getElementById('dailyChart').style.display="block";
		</script>
	</body>
</html>