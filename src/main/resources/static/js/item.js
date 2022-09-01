$(document).ready(function () {
    spaceModal.init();
    categoryModal.init();
});

let index = {
    init: function(){
        api.search();
    }
};

let api = {
    search: function () {
        commonMethod.clearError();
        url = "/item";

        let body = {
            itemName : $("#itemName").val(),
            itemDescription : $("#itemDescription").val(),
            spaceName : $("#spaceName").val()
        };
        commonMethod.fetch(url, "POST", body)
        .then(data => this.searchCallback(data))
        .catch(data => commonMethod.drawError(data));
    },

    searchCallback: function (data) {
        if(!data) return;

        let $tbody = $("#tbodyItems");
        $tbody.empty();

        data.forEach(item => $tbody.append(genRow(item)));
    }

};

function nullToBlank(str){
        if(str == null) return "";
        return str;
    };

function genRow(data){
        let row = "<tr>";
        row += "<td>"+ nullToBlank(data.name) +"</td>";
        row += "<td>"+ nullToBlank(data.spaceName) +"</td>";
        row += "<td>"+ nullToBlank(data.itemDescription) +"</td>";
        row += "<td class='text-center'><a href='/item/"+data.id+"' class='btn btn-primary'>상세</a></td>";
        row += "</tr>";
        return row;
    }

function searchEnter(e){
    if (e.keyCode === 13) {
        api.search();
    }
}

index.init();