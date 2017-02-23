$(function(){
     $(document).on('click','.listbutton',function () {
      $('.intro').dimmer('show');
    })
      $(document).on('click','.rebutton',function () {
       // temp=$(this).data('name');
       $('.intro').dimmer('hide');
     })
})