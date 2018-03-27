function parseData(data){
	if(data == null){
		alert('주소를 정확하게 입력하세요.');
	}
	else{
		for(var i = 0; i < data.length; i++){
			var coordList = getCoordinate(data[i].type, data[i].coordinateList);
			var name = data[i].name;
			var desc = data[i].desc;
			
			var totalTime;
			var totalDistance;;
			var time;
			var dist;
			
			if(i == 0){
				totalTime = data[0].totalTime;
				totalDistance = data[0].totalDistance;
			}
			else{
				time = data[i].time;
				dist = data[i].distance;
			}
			
			initTMap(startPoint[0].split("=")[1], startPoint[1].split("=")[1], endPoint[0].split("=")[1], endPoint[1].split("=")[1]);
		}
	}
}