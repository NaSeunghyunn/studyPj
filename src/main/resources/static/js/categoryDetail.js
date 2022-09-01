$(document).ready(function () {
    categoryModal.init();
});

let index = {
  init: function () {
    if ($("#id").val() > 0) {
      var url = this.genUrl($("#id").val());
      commonMethod.fetchGet(url).then((data) => this.initCallback(data))
      .catch(error => commonMethod.drawError(error));
      $(".status-mod").show();
      $(".status-reg").hide();
    } else {
      $(".status-mod").hide();
      $(".status-reg").show();
    }

    $(".btn-mod").click(function () {
      index.save();
    });

    $(".btn-del").click(function () {
      index.del();
    });

    $(".btn-reg").click(function () {
      index.save();
    });

    $(".btn-back").click(function () {
      location.href = "/category";
    });
  },

  save: function () {
    let url = this.genUrl();
    let id = $("#id").val();
    let categoryName = $("#categoryName").val();
    let parentName = $("#parentCategoryName").val();
    let body = {
      id: id,
      categoryName: categoryName,
      parentName: parentName
    };
    commonMethod.fetch(url, "POST", body).then((data) => location.href = "/category/" + data)
    .catch(error => commonMethod.drawError(error));
  },

  del: function () {
    let url = this.genUrl();
    let id = $("#id").val();
    let body = {
      id: id,
    };
    commonMethod.fetch(url, "DELETE", body).then(() => location.href = "/category")
    .catch(error => commonMethod.drawError(error));
  },

  initCallback: function (result) {
    result.parents.forEach(data => index.genLink(data.id, data.name));

    $("#categoryName").val(result.name);
    if (result.parents.length > 1) {
      $("#parentCategoryName").val(result.parents[result.parents.length - 2].name);
    } else {
      $(".btn-modal-category").attr("disabled", true);
      $(".btn-modal-category").hide();
      $("#parentCategoryName").attr("readonly", true);
      $("#parentCategoryName").attr("disabled", true);
    }
  },

  genUrl: function (id) {
    id = id ? id : "";
    return "/category/detail/" + id;
  },

  genLink: function (id, name) {
    var a = document.createElement("a");
    a.setAttribute("href", "/category/" + id);
    var button = document.createElement("button");
    button.setAttribute("type", "button");
    button.setAttribute("class", "btn btn-outline-primary me-2 mb-1");
    button.innerText = name;
    $(".parents").append($(a).append(button));
    if ($("#id").val() != id) {
      var p = document.createElement("p");
      p.setAttribute("class", "text-primary d-inline-block me-2 mb-1");
      p.innerText = ">";
      $(".parents").append(p);
    }
  }
}

index.init();