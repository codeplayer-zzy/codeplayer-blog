/**
 * 添加一级评论
 */
function post() {
    let articleId = $("#article_id").val();
    let content = $("#comment_content").val();
    commentAjaxMethod(articleId,1,content);
}

function commentAjaxMethod(targetId, type, content) {
    if (!content) {
        alert("内容不能为空~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code === 200) {
                window.location.reload();
            }else{
                if (response.code === 2003){
                    let isAccepted = confirm(response.message);//message弹窗
                    if (isAccepted){
                        window.open("/login");
                        window.localStorage.setItem("closable", true);
                    }
                }else {
                    alert(response.message);
                }

            }
            console.log(response);
        },
        dataType: "json"
    });
}

/**
 * 添加二级评论
 */
function comment2(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    commentAjaxMethod(commentId, 2, content);
}

/**
 * 打开折叠二级评论
 */
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    //标记判断二级评论打开与否
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        let comment2Container = $("#comment-" + id);
        if (comment2Container.children().length != 1) {
            //打开二级评论
            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/" + id, function(data) {
                $.each(data.data.reverse(), function (index, comment) {
                    let mediaLeftElement = $("<div/>", {
                        "class" : "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded img-circle comment-img",
                        "src": comment.user.avatarUrl
                    }));

                    let mediaBodyElement = $("<div/>",{
                        "class": "media-body"
                    }).append($("<h5/>",{
                        "class": "media-heading comment-name",
                        "html": comment.user.name
                    })).append($("<div/>",{
                        "class": "comment-content",
                        "html": comment.content
                    })).append($("<span/>",{
                        "class": "pull-right glyphicon glyphicon-dashboard comment-gmtCreate",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:MM')
                    }));

                    let mediaElement = $("<div/>",{
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    let commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                    }).append(mediaElement);

                    comment2Container.prepend(commentElement);
                });
                //打开二级评论
                comments.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}