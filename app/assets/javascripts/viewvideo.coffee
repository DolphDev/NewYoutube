$ ->
  $(".like-btn").click ->
    $.ajax({
      url: "/likevideo/" + $(".video-id").html(),
      type: "GET"
      success: ->
        $(".dislike-btn").removeClass("active")
        $(".like-btn").addClass("active")
    })
  $(".dislike-btn").click ->
    $.ajax({
      url: "/dislikevideo/" + $(".video-id").html(),
      type: "GET"
      success: ->
        $(".like-btn").removeClass("active")
        $(".dislike-btn").addClass("active")
    })