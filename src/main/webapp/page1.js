var dom = document.getElementById("echa1");
var myChart = echarts.init(dom);
var app = {};
option = null;
myChart.showLoading();
weiboData = [];
myChart.hideLoading();
$(document).ready(function(){
         $.ajax({
             url:"/gps/gpscount.do",
             type:"GET",
             dataType:"json",
             async:false,
             success: function(data){

                 weiboData = data.gpsdata;
                 console.log(weiboData);
                 myChart.setOption(option = {
                     tooltip: {},
                     geo: {
                         name: '强',
                         type: 'scatter',
                         map: 'china',
                         label: {
                             emphasis: {
                                 show: false
                             }
                         },
                         itemStyle: {
                             normal: {
                                 areaColor: '#323c48',
                                 borderColor: '#111'
                             },
                             emphasis: {
                                 areaColor: '#2a333d'
                             }
                         }
                     },
                     series: [{
                         name: '弱',
                         type: 'scatter',
                         coordinateSystem: 'geo',
                         symbolSize: 1,
                         large: true,
                         itemStyle: {
                             normal: {
                                 shadowBlur: 2,
                                 shadowColor: 'rgba(37, 140, 249, 0.8)',
                                 color: 'rgba(37, 140, 249, 0.8)'
                             }
                         },
                         data: weiboData[0]
                     }, {
                         name: '中',
                         type: 'scatter',
                         coordinateSystem: 'geo',
                         symbolSize: 1,
                         large: true,
                         itemStyle: {
                             normal: {
                                 shadowBlur: 2,
                                 shadowColor: 'rgba(14, 241, 242, 0.8)',
                                 color: 'rgba(14, 241, 242, 0.8)'
                             }
                         },
                         data: weiboData[1]
                     }, {
                         name: '强',
                         type: 'scatter',
                         coordinateSystem: 'geo',
                         symbolSize: 1,
                         large: true,
                         itemStyle: {
                             normal: {
                                 shadowBlur: 2,
                                 shadowColor: 'rgba(255, 255, 255, 0.8)',
                                 color: 'rgba(255, 255, 255, 0.8)'
                             }
                         },
                         data: weiboData[2]
                     }]
                 });




             }
            });
        });


if (option && typeof option === "object") {
    myChart.setOption(option, true);
}

