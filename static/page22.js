var dom = document.getElementById("echa22");
var myChart = echarts.init(dom);
var app = {};

speeddata=[18203, 23489, 29034, 104970, 131744, 630230];
dicdata=['巴西','印尼','美国','印度','中国'];
app.title = '世界人口总量 - 条形图';

option = {
    title: {
        text: '关于速度',
        textStyle: {
            color: '#ccc'
        }
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    // legend: {
    //     data: ['2011年']
    // },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01],
        textStyle: {
            color: '#ccc'
        }
    },
    yAxis: {
        type: 'category',
        data: dicdata,
        textStyle: {
            color: '#ccc'
        }
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: speeddata
        }
    ]
};

myChart.setOption(option);