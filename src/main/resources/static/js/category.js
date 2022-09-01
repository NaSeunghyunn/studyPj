let index = {
    init: function () {
        api.getCategory(0);
    },
};

let api = {
    getCategory: function (id, target) {
        let url = "/category/" + id;
        commonMethod.fetchGet(url).then((data) => this.getCategoryCallback(data, target))
        .catch(error => commonMethod.drawError(error));
    },

    clickSearch: function (event) {
        this.searchCategory();
    },

    enterSearch: function (event) {
        if (event.key === "Enter") {
            this.searchCategory();
        }
    },

    searchCategory: function () {
        var ul = document.getElementById("contents");
        $(ul).empty();
        if ($("#search").val()) {
            let data = {
                name: $("#search").val()
            }
            commonMethod.get("/category/search", data, this.getCategoryCallback)
        } else {
            api.getCategory(0);
        }
    },

    getCategoryCallback: function (result, target) {
        let ul = document.getElementById("contents");
        result.forEach((data, i) => {
            let li = document.createElement("li");
            li.setAttribute("class", "list-group-item flex justify-content-between align-items-center");
            if (target) {
                li.id = target.id + "-" + i;
            } else {
                li.id = i;
            }
            var level = li.id.split("-").length - 1;
            var leverStr = "";
            for (i = 0; i < level; i++) {
                leverStr = leverStr + '\u00A0' + '\u00A0' + '\u00A0';
            }
            let span = document.createElement("span");
            span.innerText = leverStr + data.name;
            span.setAttribute("class", "text-primary cursor-pointer stop-dragging");
            span.onclick = function () {
                let parent = span.parentNode;
                if ($(parent).hasClass("on")) {
                    var $lowerLevel = $("li[id^='" + parent.id + "-']");
                    $lowerLevel.hide(200);
                    if ($lowerLevel.hasClass("on")) {
                        $lowerLevel.removeClass("on");
                        $lowerLevel.addClass("off");
                    }
                    $(parent).removeClass("on");
                    $(parent).addClass("off");
                } else if ($(parent).hasClass("off")) {
                    var startId = parent.id + "-";
                    $("li[id^='" + startId + "']").each(function () {
                        var childLevel = this.id.split("-").length - 1;
                        if (childLevel == level + 1) {
                            $(this).show(200);
                        }
                    });
                    $(parent).removeClass("off");
                    $(parent).addClass("on");
                } else {
                    var id = $(parent).find(".categoryId");
                    let result = api.getCategory($(id).val(), parent);
                    $(parent).addClass("on");
                }
            };
            let detailBtn = document.createElement("button");
            detailBtn.setAttribute("class", "btn btn-primary");
            detailBtn.innerText = "상세";
            $(detailBtn).click(function () {
                $(location).attr("href", "/category/" + data.id)
            });
            let id = document.createElement("input");
            id.setAttribute("type", "hidden");
            id.setAttribute("class", "categoryId");
            id.value = data.id;
            $(li).append(span).append(id).append(detailBtn);
            if (target) {
                $(target).after(li);
                $(li).hide().show(200);
            } else {
                $(ul).prepend(li);
            }
        });
    }
};

index.init();
