$("learn-save").click(function() {
  var all_elements = $("#learnModal input, #learnModal button")
  all_elements.attr('disabled', true)
  $.ajax({
    url: "api/learn", // TODO: url base
    data: {
      text: $("#learnModal .learn_text").val()
    }
  })
})