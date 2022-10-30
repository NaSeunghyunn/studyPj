let index = {
    init: function () {
        api.search();
    }
};

let api = {
    search: function () {
        commonMethod.clearError();
        url = "/item/expire";

        let body = {
            expirationDays: $("#expirationDate").val(),
        };
        commonMethod.fetch(url, "POST", body)
            .then(data => this.searchCallback(data))
            .catch(data => commonMethod.drawError(data));
    },

    searchCallback: function (data) {
        if (!data) return;

        let $tbody = $("#tbodyExpireItems");
        $tbody.empty();

        data.forEach(item => $tbody.append(genRow(item)));
    }

};

function genRow(data) {
    let row = "<tr>";
    row += "<td>" + nullToBlank(data.name) + "</td>";
    row += "<td>" + nullToBlank(data.spaceName) + "</td>";
    row += "<td>" + nullToBlank(data.expirationDate) + "</td>";
    row += "<td>" + nullToBlank(data.itemDescription) + "</td>";
    row += "<td class='text-center'><a href='/item/" + data.id + "?referer=/item/expire' class='btn btn-primary'>상세</a></td>";
    row += "</tr>";
    return row;
}

function nullToBlank(str) {
    if (str == null) return "";
    return str;
};

function searchEnter(e) {
    if (e.keyCode === 13) {
        api.search();
    }
}
index.init();