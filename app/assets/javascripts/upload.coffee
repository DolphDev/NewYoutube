$ ->
  $(".statusText").hide()
  $(".upload-error-box").hide()

  $(".uploadButton").click ->
    if $("#titleInput").val() == ""
      $(".upload-error").html("Title field may not be blank")
      $(".upload-error-box").fadeIn()
      return
    else if $("#descArea").val() == ""
      $(".upload-error").html("Description field may not be blank")
      $(".upload-error-box").fadeIn()
      return
    else if $("#fileInput").val() == ""
      $(".upload-error").html("You must enter a file")
      $(".upload-error-box").fadeIn()
      return
    formData = new FormData($(".upload-form")[0])
    $(".statusText").fadeIn()
    $.ajax({
      url: '/upload',
      type: 'POST',
      data: formData,
      cache: false,
      xhr: ->
        xhr = new window.XMLHttpRequest();
        xhr.upload.onprogress = (evt) ->
          fraction = evt.loaded / evt.total
          $(".statusText").html("Uploading - " + Math.round(fraction*100) + "%")
          $("#uploadProgress").css("width", fraction*100 + "%")
        return xhr

      error: (xhr, status, error) ->
        $(".statusText").html("Oh snap! Something went wrong! (" + error + ")")
        $("#uploadProgress").removeClass("progress-bar-info")
        $("#uploadProgress").addClass("progress-bar-error")
        $("#uploadProgress").css("width", "100%")
      success: (data, status, xhr) ->
        if status != 200
          $(".statusText").html("Oh snap! Something went wrong! (" + data + ")")
          $("#uploadProgress").removeClass("progress-bar-info")
          $("#uploadProgress").addClass("progress-bar-error")
          $("#uploadProgress").css("width", "100%")
        $("#uploadProgress").removeClass("progress-bar-info")
        $("#uploadProgress").addClass("progress-bar-success")
        $(".statusText").html("Video successfully uploaded at <a href=\"/watch/" + data + "\">" + "/watch/" + data + "</a>")
        window.location.href = "/watch/" + data
      contentType: false,
      processData: false
    })