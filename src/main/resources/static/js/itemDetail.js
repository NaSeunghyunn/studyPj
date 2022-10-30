$(document).ready(function () {
    $('#purchaseDate').datepicker({
        format: "yyyy-mm-dd",
        startDate: '-1y',
        language: "en",
        autoclose: true,
    });

    $('#expirationDate').datepicker({
        format: "yyyy-mm-dd",
        startDate: '-1y',
        language: "en",
        autoclose: true,
    });

    spaceModal.init();
    categoryModal.init();
});

let index = {
    init: function () {
    if ($("#id").val() > 0) {
        api.getItem();
        $(".status-mod").show();
        $(".status-reg").hide();
        }else{
        $(".status-mod").hide();
                $(".status-reg").show();
        }
    },

    genCategory: function (id, name) {
        var a = document.createElement("a");
        a.setAttribute("href", "/category/" + id);
        a.setAttribute("class", "btn btn-outline-primary ms-1 me-1 mb-1");
        a.innerText = name;
        $(".categories").append($(a));

        categoryModal.addCategoryItem(id,name);
    },

    genSpace: function (id, name) {
        var button = document.createElement("button");
        button.setAttribute("type", "button");
        button.setAttribute("class", "btn btn-outline-primary ms-1 me-1 mb-1 cursor-default");
        button.innerText = name;
        $(".locations").append(button);
        var p = document.createElement("p");
        p.setAttribute("class", "text-primary m-1");
        p.innerText = ">";
        $(".locations").append(p);
    },

    switchDispose: function () {
        var isChecked = document.getElementById('disposeSwitch').checked;
        if (isChecked) {
            $("#disposeLabel").removeClass("check-off");
            $("#disposeLabel").addClass("check-on");
        } else {
            $("#disposeLabel").removeClass("check-on");
            $("#disposeLabel").addClass("check-off");
        }
    },

    switchDisappear: function () {
        var isChecked = document.getElementById('disappearSwitch').checked;
        if (isChecked) {
            $("#disappearLabel").removeClass("check-off");
            $("#disappearLabel").addClass("check-on");
        } else {
            $("#disappearLabel").removeClass("check-on");
            $("#disappearLabel").addClass("check-off");
        }
    }


};

let api = {

    getItem: function () {
        url = "/item/detail/" + $("#id").val();
        commonMethod.fetchGet(url).then(data => this.getItemCallback(data))
        .catch(error => commonMethod.drawError(error));
    },

    getItemCallback: function (data) {
    if(!data) return;
        data.categories.forEach(category => index.genCategory(category.id, category.name));
        data.spaces.forEach(space => index.genSpace(space.id, space.name));
        $(".locations").children(":last").hide();
        if (data.spaces && data.spaces.length > 0) {
            $("#spaceName").val(data.spaces[data.spaces.length - 1].name);
            $("#spaceId").val(data.spaces[data.spaces.length - 1].id);
        }
        $("#itemName").val(data.name);
        $("#itemDescription").text(data.description);
        $("#purchaseDate").datepicker('update', data.purchaseDate);
        $("#expirationDate").datepicker('update', data.expirationDate);

        if (data.disposeFlg == "1") {
            $("#disposeSwitch").prop("checked", true);
            $("#disposeLabel").addClass("check-on");
        } else {
            $("#disposeSwitch").prop("checked", false);
            $("#disposeLabel").addClass("check-off");
        }

        if (data.disappearFlg == "1") {
            $("#disappearSwitch").prop("checked", true);
            $("#disappearLabel").addClass("check-on");
        } else {
            $("#disappearSwitch").prop("checked", false);
            $("#disappearLabel").addClass("check-off");
        }

    },

    save: function () {

        let id = $("#id").val();
        let itemName = $("#itemName").val();
        let itemDescription = $("#itemDescription").val();
        let spaceName = $("#spaceName").val();
        let purchaseDate = $("#purchaseDate").val();
        let expirationDate = $("#expirationDate").val();
        let disposeFlg = document.getElementById('disposeSwitch').checked ? "1" : "0";
        let disappearFlg = document.getElementById('disappearSwitch').checked ? "1" : "0";

        let url = "/item/detail";

        var categoryIds = [];
        $(".addCategory").each(function(index, item){
            categoryIds.push($(item).val());
        });
        let body = {
            id: id,
            name: itemName,
            spaceName: spaceName,
            purchaseDate: purchaseDate,
            expirationDate: expirationDate,
            disposeFlg: disposeFlg,
            disappearFlg: disappearFlg,
            description: itemDescription,
            categoryIds: categoryIds
        };

        commonMethod.fetch(url, "POST", body).then(data => location.href = "/item/" + data + "?referer="+$("#referer").val())
        .catch(error => commonMethod.drawError(error));
    },

    del: function () {

        let url = "/item/detail";
        let body = {
            id: $("#id").val()
        };

        commonMethod.fetch(url, "DELETE", body).then(() => location.href = $("#referer").val())
        .catch(error => commonMethod.drawError(error));
    }

};

index.init();