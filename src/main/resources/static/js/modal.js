const TABLE_CLASS_NM_CATEGORY = "categoryTable";
const TABLE_CLASS_NM_SPACE = "spaceTable";
const TYPE_SELECT = "selectModal";
const TYPE_ADD = "addModal";

let categoryModal = {
    init: function () {
        if ($("#categoryModalTable").find("tr").length == 0) {
            this.search();
        }
    },

    search: function () {
        let data = {
            name: $("#categorySearchName").val()
        }
        commonMethod.get("/category/search", data, this.searchCallback, function(){});
    },

    searchEnter: function (e) {
        if (e.keyCode === 13) {
            this.search();
        }
    },

    searchCallback: function (result) {
        var tableBody = document.getElementById("categoryModalTable");
        $(tableBody).empty();
        commonModal.setSearchTable(result, tableBody);
    },

    addCategory: function(target) {
            var tr = $(target).parents("tr");
            var id = tr.find(".searchId").val();
            var name = tr.find(".searchName").val();

            $(".modal-close").click();
            var duplicateFlg = false;
            $(".addCategory").each(function(index, item){
                if($(item).val() == id) {
                    duplicateFlg = true;
                    return;
                }
            });

            if(duplicateFlg) return;

            categoryModal.addCategoryItem(id,name);
    },

    addCategoryItem : function(id, name){
        var button = document.createElement("button");
                    button.setAttribute("type", "button");
                    button.setAttribute("class", "btn btn-outline-primary ms-1 me-1 mb-1");
                    button.setAttribute("onclick", "categoryModal.delCategoryItem(this,"+id+")");
                    button.innerText = name;

                    var input = document.createElement("input");
                    input.setAttribute("type", "hidden");
                    input.setAttribute("class", "addCategory");
                    input.value = id;

                    $(".addCategories").append(button).append(input);
    },

    delCategoryItem : function(target,id){
    console.log(id);
        $(".addCategory").each(function(index, item){
                        if($(item).val() == id) {
                            $(item).remove();
                        }
                    });
        $(target).hide(200, function() { $(this).remove(); });
    }
};

let spaceModal = {
    init: function () {
        if ($("#spaceModalTable").find("tr").length == 0) {
            this.search();
        }
    },

    search: function () {
        let data = {
            name: $("#spaceSearchName").val()
        }
        commonMethod.get("/space/search", data, this.searchCallback, function(){});
    },

    searchEnter: function (e) {
        if (e.keyCode === 13) {
            this.search();
        }
    },

    searchCallback: function (result) {
        var tableBody = document.getElementById("spaceModalTable");
        $(tableBody).empty();
        commonModal.setSearchTable(result, tableBody);
    },

    addSpace: function(target) {
                var tr = $(target).parents("tr");
                var id = tr.find(".searchId").val();
                var name = tr.find(".searchName").val();

                $(".addSpace").each(function(index, item){
                    if(item.val() == id) {
                        $(".modal-close").click();
                        return;
                    }
                });

                var button = document.createElement("button");
                button.setAttribute("type", "button");
                button.setAttribute("class", "btn btn-outline-primary ms-1 me-1 mb-1 cursor-default");
                button.innerText = name;

                var input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("class", "addSpace");
                input.value = id;

                $(".addSpaces").append(button).append(input);
                $(".modal-close").click();
            },
};

let commonModal = {
    setSearchTable: function (result, tableBody) {
        result.forEach(data => {
            var tr = tableBody.insertRow();
            var tdName = tr.insertCell();
            tdName.setAttribute("class", "col-7");
            tdName.innerText = data.name;
            var searchId = document.createElement("input");
            searchId.setAttribute("type", "hidden");
            searchId.setAttribute("class", "searchId");
            searchId.value = data.id;
            var searchName = document.createElement("input");
            searchName.setAttribute("type", "hidden");
            searchName.setAttribute("class", "searchName");
            searchName.value = data.name;
            $(tdName).append(searchId).append(searchName);
            var tdBtn = tr.insertCell();
            tdBtn.setAttribute("class", "col-1");
            var button = document.createElement("button");
            button.setAttribute("type", "button");
            button.setAttribute("class", "btn btn-primary");
            button.setAttribute("onClick", "commonModal.onClickItem(this);");
            button.innerText = "선택";
            tdBtn.append(button);
        });
    },

    onClickItem: function(target) {
        var $tbody = $(target).parents("tbody");
        if ($tbody.hasClass(TYPE_ADD)) {
            commonModal.addModalItem(target);
        } else {
            commonModal.selectModalItem(target);
        }
    },

    addModalItem: function(target) {
    var $tbody = $(target).parents("tbody");
        if ($tbody.hasClass(TABLE_CLASS_NM_CATEGORY)) {
                    categoryModal.addCategory(target);
                } else if ($tbody.hasClass(TABLE_CLASS_NM_SPACE)) {
                    spaceModal.addCategory(target);
                }
    },

    selectModalItem: function (target) {
        var tr = $(target).parents("tr");
        var id = tr.find(".searchId");
        var name = tr.find(".searchName")

        var $tbody = $(target).parents("tbody");
        if ($tbody.hasClass(TABLE_CLASS_NM_CATEGORY)) {
            $(".modal-category-id").val(id.val());
            $(".modal-category-name").val(name.val());
            $(".modal-close").click();
        } else if ($tbody.hasClass(TABLE_CLASS_NM_SPACE)) {
            $(".modal-space-id").val(id.val());
            $(".modal-space-name").val(name.val());
            $(".modal-close").click();
        }
    }
}
