$(".uploader").click ->
  console.log("hi!")
  $.ajax({
    url: "/upload",
    type: 'PUT',
    data: $("uploader").file.getData
    success: ->
      print("hi")
  })