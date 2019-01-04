google.charts.load('current', {packages: ['corechart', 'bar']});
$("#btn_medicine").click(function () {
     $("#btn_medicine").hide();
    $.ajax({
        url: "MedicineVsAvailableMedicinebarchart",
        type: "POST",
        data: "{}",
        success: function (msg) {
            createDataTable(msg)            
        },
        error: function(){
            console.log("error occurred while making ajax call;")
        }
    });    
});

function createDataTable(jsonData) {
    var parsedData = $.parseJSON(jsonData);
    var data = new Array();
    var productNameArr = new Array();
    var cityArr = new Array();
    for(var i=0; i<parsedData.length; i++) {
        var productName = parsedData[i]["productName"];
        var city = parsedData[i]["productName"];
        if(!productNameArr.includes(productName)) {
            productNameArr.push(productName);
        }
        if(!cityArr.includes(city)) {
            cityArr.push(city);
        }
     }
     var headingArray = new Array(productNameArr.length+1);
     headingArray[0] = "ProductName";
     var j=0;

     for(var i=1; i<=productNameArr.length; i++) {
        headingArray[i] = productNameArr[j]; 
        j++;
     }

     data[0] = headingArray;
     var m =1;
    for(var i=0; i<cityArr.length; i++) {
        var dataArr = new Array(headingArray.length);
        dataArr[0] = cityArr[i];
        for(var j=0; j<productNameArr.length; j++) {
            for(k=0; k<parsedData.length; k++) {
                if(parsedData[k]["productName"] === cityArr[i] && parsedData[k]["productName"] === productNameArr[j])
				{
                    dataArr[j+1] = parseInt(parsedData[k]["availableProductQuantity"]);                   
                }                 
            }

        }
        for(var n=1; n<headingArray.length; n++) {
            if(!(dataArr[n] > 0)) {
                dataArr[n] = 0;
            }
        }
        data[m] = (dataArr);
        m++;
        
     }
	 
     drawChart(data, productNameArr);
}

function drawChart(data, productNameArr) {
    var productNames = "";
    for(var i=0; i<productNameArr.length; i++) {
        productNames += productNameArr[i] + ",";
    }
     var chartData = google.visualization.arrayToDataTable(data);

     var options = {
		title: 'Bar Chart for Number of medicines available in the store',
		fontsize:10,
        'width':930,
        'height':2500,
		legend: { position: 'top', title:'Products #',textStyle: {fontSize: 16},colors: ['blue']} ,
        chart: {
            title: 'Trending Medicines Chart',
            subtitle: productNames,
          },
        bars: 'horizontal',
		
        hAxis: {
               title :'Number of medicines available in the store',
			   textStyle : {
							fontSize: 12
						},
			   viewWindow: {
							min: 0,
							max: 300
							},
				ticks: [0, 50,100,150,200,250,300]
				},
		vAxis: {
               title :'Medicine Names',
			   textStyle : {
							fontSize: 10
						}
              }			  
        };
    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
    chart.draw(chartData, options);
}