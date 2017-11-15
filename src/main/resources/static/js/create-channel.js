$('#create-new-bot').on('click', function() {
  $('#create-new-bot').prop('disabled', true);
    var params = {};
    $($("#create-new-bot-form").serializeArray()).each(function(i,v){
      params[v.name] = v.value;
    });
    $.ajax({
      method : "POST",
      url : "/bot/create",
      data : JSON.stringify(params),
      contentType : "application/json",
      dataType : "JSON"
    }).done(function(data) {
      alert(data.status);
      $("form")[0].reset();
    }).fail(function(data) {
      alert(data.responseJSON.message);
  });
  $('#create-new-bot').prop('disabled', false);
});
