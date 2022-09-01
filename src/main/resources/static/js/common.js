let commonMethod = {
    ajax: function (method, url, data, fn_doneCallback, fn_failCallback) {
        $.ajax({
            type: method,
            url: getApiUrl(url),
            data: data, // body데이터
            contentType: "application/json; charset=utf-8", // body타입 MIME
            dataType: "json",
        }).done(function (xhr) {
            fn_doneCallback(xhr);
        }).fail(function (xhr) {
            fn_failCallback(xhr);
        });
    },

    get: function (url, data, fn_doneCallback, fn_failCallback) {
        $.get(getApiUrl(url), data).done(function (xhr) {
            if (fn_doneCallback == null) {
                console.log(xhr);
                return xhr;
            }
            fn_doneCallback(xhr);
        }).fail(function (xhr) {
            fn_failCallback(xhr);
        });
    },

    post: function (url, data, fn_doneCallback, fn_failCallback) {
        $.post(getApiUrl(url), data).done(function (xhr) {
            fn_doneCallback(xhr);
        }).fail(function (xhr) {
            fn_failCallback(xhr);
        });
    },

    fetchGet: async function (url) {
        const res = await fetch(getApiUrl(url));
        const data = await res.json();
        if (res.ok) {
            return data;
        } else {
            apiError(data);
        }
    },

    fetchPost: async function (url, body, fn_doneCallback) {
        let option = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }
        const res = await fetch(getApiUrl(url), option);
        const data = await res.json();
        if (res.ok) {
            fn_doneCallback(data);
        } else {
            apiError(data);
        }
    },

    fetch: async function (url, method, body, fn_doneCallback) {
        let option = {
            method: method,
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }
        const res = await fetch(getApiUrl(url), option);
        const data = await res.json();
        if (res.ok) {
            return data;
        } else {
            apiError(data);
        }
    },

    backBtn: function () {
    },

    clearError: function (){
        $(".err").empty();
    },

    drawError: function (error){
         $(".err").text(error.message);
    }
}

function getApiUrl(url){
    return "../api" + url;
}

function apiError(data){
    throw new Error(data.error);
}