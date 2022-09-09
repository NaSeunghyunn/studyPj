$(document).ready(function () {
    spaceModal.init();
    categoryModal.init();
});

let index = {
    init: function () {
        api.search();
    }
};

let api = {
    search: function () {
        commonMethod.clearError();
        url = "/item";

        let body = {
            itemName: $("#itemName").val(),
            itemDescription: $("#itemDescription").val(),
            spaceName: $("#spaceName").val(),
            categoryName: $("#categoryName").val()
        };
        commonMethod.fetch(url, "POST", body)
            .then(data => this.searchCallback(data))
            .catch(data => commonMethod.drawError(data));
    },

    searchCallback: function (data) {
        if (!data) return;

        let $tbody = $("#tbodyItems");
        $tbody.empty();

        data.forEach(item => $tbody.append(genRow(item)));
    }

};

function nullToBlank(str) {
    if (str == null) return "";
    return str;
};

function genRow(data) {
    let row = "<tr>";
    row += "<td>" + nullToBlank(data.name) + "</td>";
    row += "<td>" + nullToBlank(data.spaceName) + "</td>";
    if (data.categoryNames == null) {
        row += "<td></td>";
    } else {
        row += "<td>" + createAccordion(data.id, data.categoryNames) + "</td>";
    }
    row += "<td>" + nullToBlank(data.itemDescription) + "</td>";
    row += "<td class='text-center'><a href='/item/" + data.id + "' class='btn btn-primary'>상세</a></td>";
    row += "</tr>";
    return row;
}

function searchEnter(e) {
    if (e.keyCode === 13) {
        api.search();
    }
}

function createAccordion(id, list) {
    var accordion = "<div class='accordion accordion-flush'><div class='accordion-item'>";
    if (list.length > 1) {
        accordion += "<h2 class='accordion-header' id='heading" + id + "'>";
        accordion += "<button class='accordion-button collapsed' type='button' data-bs-toggle='collapse' data-bs-target='#collapse" + id + "' aria-expanded='false' aria-controls='collapse" + id + "'>" + list[0] + "</button>";
        accordion += "</h2>";
        accordion += "<div id='collapse" + id + "' class='accordion-collapse collapse' aria-labelledby='heading" + id + "' data-bs-parent='#accordionExample'>";
        accordion += "<div class='accordion-body'>";
        $(list).each(function (index, item) {
            if (index > 0) accordion += "<p>" + nullToBlank(item) + "</p>";
        });
        accordion += "</div></div>";
    } else {
        accordion += "<h2 class='accordion-header' id='heading" + id + "'>";
        accordion += "<button class='accordion-disable collapsed' type='button'>" + list[0] + "</button>";
        accordion += "</h2>";
    }
    accordion += "</div></div>";
    return accordion;
}

index.init();