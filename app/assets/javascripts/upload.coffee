$ ->
  $(".statusText").hide()

  $(".uploadButton").click ->
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
        $(".statusText").html("Oh snap! Something went wrong")
        $("#uploadProgress").removeClass("progress-bar-info")
        $("#uploadProgress").addClass("progress-bar-error")
        $("#uploadProgress").css("width", "100%")
      success: (data, status, xhr) ->
        $("#uploadProgress").removeClass("progress-bar-info")
        $("#uploadProgress").addClass("progress-bar-success")
        $(".statusText").html("Video successfully uploaded at <a href=\"/watch/" + data + "\">" + "/watch/" + data + "</a>")
      contentType: false,
      processData: false
    })