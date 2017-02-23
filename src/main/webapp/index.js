
$(document).ready(function() {
    $.ajax({
        url: "/gps/distopfive.do",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {

            for(var i=0;i < data.length;i++){
               var srcpath="imgs/n"+(i+1)+".png";
                $("#listfive").append('<div class="item">' +
                   '<img class="ui avatar image" src="'+ srcpath +'"> ' +
                   '<div class="content listbutton" data-name="'+data[i].CODE+'">' +
                   '<p>'+data[i].CODE+"      :     "+data[i].DIS+"米"+'</p>' +
                   '</div>'+
                   '</div>');
            }
        }
    });
});

$(function(){
    $(document).on('click','.listbutton',function () {
        var temp = $(this).data('name');
        $.ajax({
            url: "/gps/getdetail.do",
            type: "GET",
            dataType: "json",
            data: {
                "code": temp
            },
            success: function (data) {
                $("#detailcontent").empty();
                $("#detailcontent").append('<div class="content">' +
                    '<div class="center">' +
                    '<h3 class="ui inverted icon header">' +
                    '<div class="ui list">' +
                    '<div class="item"> ' +
                    '<div class="content">' +
                    '车牌号 :'+ data.CODE+
                    '</div>' +
                    '</div>' +
                    '<div class="item">' +
                    '<div class="content">' +
                    '速度 :' + data.VEO +
                    '</div>' +
                    '</div>' +
                    '<div class="item">' +
                    '<div class="content">' +
                    '方向 :' + data.DIR +
                    '</div>' +
                    '</div>' +
                    '<div class="item">' +
                    '<div class="content">' +
                    '油耗 :' + data.OIL +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</h3>' +
                    '<button class="ui icon button inverted rebutton">' +
                    '<i class="reply icon"></i>' +
                    '</button>' +
                    '</div>' +
                    '</div>');
                $('.intro').dimmer('show');
            }
        });
    })
    $(document).on('click','.rebutton',function () {
        // temp=$(this).data('name');
        $('.intro').dimmer('hide');
    })
})