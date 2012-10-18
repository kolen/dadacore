$("#learn-save").click(function() {
  var all_elements = $("#learnModal input, #learnModal button")
  all_elements.attr('disabled', true)
  $.ajax({
    url: "api/learn", // TODO: url base
    type: 'POST',
    data: {
      text: $("#learnModal .learn_text").val()
    },
    success: function() {
      $("#learnModal .learn_text").val("")
      all_elements.removeAttr('disabled')
      $('#learnModal').modal('hide')
    },
    error: function() {
      all_elements.removeAttr('disabled')
      alert("Error sending learn text")
    }
  })
})