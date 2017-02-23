$(document).ready(function() {
    $.ajax({
        url: "/gps/gettopprovice.do",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            data[0].value = data[0].value / 10;
            console.log(data);
             gettopprovice(data);
        }
    });
});

function gettopprovice(data){
var dom = document.getElementById("echa2");
var myChart = echarts.init(dom);
var app = {};
/*countdata=[
                {value:335, name:'直接访问'},
                {value:310, name:'邮件营销'},
                {value:274, name:'联盟广告'},
                {value:235, name:'视频广告'},
                {value:400, name:'搜索引擎'}
            ].sort(function (a, b) { return a.value - b.value});*/
    countdata=data.sort(function (a, b) { return a.value - b.value});
    console.log(countdata)
    option = {
        // backgroundColor: '#2c343c',

        title: {
            text: '地区统计',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#ccc'
            }
        },

        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0, 1]
            }
        },
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:countdata,
                roseType: 'angle',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}