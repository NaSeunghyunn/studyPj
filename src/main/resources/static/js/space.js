$(document).ready(function () {
    commonMethod.clearError();
    search.search();
});

function insertSpace() {
    let body = {
        spaceName: $("#saveSpaceName").val(),
        parentName: $("#saveParentName").val(),
    };

    commonMethod.fetch("/space/save", "POST", body).then(data => insertSpaceCallback(data))
        .catch(error => commonMethod.drawError(error));
}

function insertSpaceCallback(result) {
    search.search();
}

var search = {
    enterSearch: function (e) {
        if (e.keyCode === 13) {
            this.search();
        }
    },

    clickSearch: function (e) {
        this.search();
    },

    search: function (e) {
        let data = {
            name: $("#search").val()
        }
        commonMethod.get("/space/searchList", data, this.searchCallback, this.failCallback);
    },

    searchCallback: function (result) {
        commonMethod.clearError();
        $("#contents").empty();

        $(result).each(function (index, item) {
            $("#contents").append(createRow(item));
        });
    },

    failCallback: function (result) {
        commonMethod.drawError(error);
    }
}

function updateSpace(target) {
    $(target).attr("disabled", true);
    commonMethod.clearError();
    $row = $(target).parents("li");

    let id = $row.find(".spaceId").val();
    let name = $row.find(".spaceName").val();

    let body = {
        id: id,
        name: name
    };

    commonMethod.fetch("/space/update", "POST", body)
        .then(() => updateRow(id, name))
        .catch(error => commonMethod.drawError(error))
        .finally(() => $(target).attr("disabled", false));
}

function updateRow(id, name){
    let targets = getTargets(id);
        $(targets).each(function(index, item){
            $(item).find(".spaceName").val(name);
        });
}

function deleteSpace(target) {
    $row = $(target).parents("li");
    let id = $row.find(".spaceId").val();
    let body = {
        id: id
    };
    commonMethod.fetch("/space", "DELETE", body)
        .then(() => removeRow(id))
        .catch(error => commonMethod.drawError(error));
}

function removeRow(id){
    let targets = getTargets(id);
    $(targets).each(function(index, item){
        $(item).remove();
    });
}

function getTargets(id){
    let targets = [];
    $("li").each(function(index, item){
        if($(item).find(".spaceId").val() == id){
            targets.push($(item));
        }
    });
    return targets;
}

function searchChildren(target) {
    $(target).text("닫기");
    $(target).removeAttr("onclick");
    $(target).attr("onclick", "removeChildRow(this)");

    $row = $(target).parents("li");
    var id = $row.find(".spaceId").val();

    postSearchChildren(id);
}

function postSearchChildren(id) {
    var url = "/space/search/children/" + id;
    commonMethod.fetchGet(url).then(data => addChildRow($row, data)).catch(error => commonMethod.drawError(error));
}


function addChildRow(parentRow, data) {
    let parentId = $(parentRow).find(".spaceId").val();
    $(parentRow).after(createChildRow(data));
}

function removeChildRow(target) {
    $(target).text("열기");
    $(target).removeAttr("onclick");
    $(target).attr("onclick", "searchChildren(this)");


    $row = $(target).parents("li");
    $row.next(".accordion").remove();
}

function getParentClassName(parentId) {
    if (parentId == null) return "";
    return "parent-" + parentId;
}

function createRow(item) {
    var row = " <li class='list-group-item flex justify-content-between align-items-center " + getParentClassName(item.parentId) + "'>";
    row += "        <input type='text' class='spaceName' style='width:40%;' placeholder='변경할 이름을 입력하세요.' value='" + item.name + "'/>";
    row += "        <input type='hidden' class='spaceId' value='" + item.id + "'/>";
    row += "        <input type='hidden' class='spaceParentId' value='" + item.parentId + "'/>";
    row += "        <div>";
    row += "            <button class='btn btn-primary' onclick='updateSpace(this)'>변경</button>";
    row += "            <button class='btn btn-primary' onclick='deleteSpace(this)'>삭제</button>";
    row += "            <button class='btn btn-primary' onclick='searchChildren(this)'" + disableSearchChildBtn(item.existChild) + ">열기</button>";
    row += "        </div>";
    row += "    </li>";
    return row;
}

function disableSearchChildBtn(existChild) {
    return existChild ? "" : " disabled "
}

function createChildRow(data) {
    var row = "<ul class='accordion' style='background-color: rgba(0,0,0,.125);'>";
    $(data).each(function (index, item) {
        row += createRow(item);
    });
    row += "</ul>";
    return row;
}