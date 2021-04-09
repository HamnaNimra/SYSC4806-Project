function addToCart(isbn) {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function() {
      if(this.readyState === 4) {
        var data = JSON.parse(this.responseText);
        console.log(data);

        if(!('error' in data)) {
            showCart(data.items);
        }
      }
    });

    xhr.open("GET", window.location.origin + "/addBook/" + isbn);

    xhr.send();
}

function removeFromCart(isbn) {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function() {
      if(this.readyState === 4) {
        var data = JSON.parse(this.responseText);
        console.log(data);

        if(!('error' in data)) {
            showCart(data.items);
        }
      }
    });

    xhr.open("GET", window.location.origin + "/removeBook/" + isbn);

    xhr.send();
}

function showCart(data) {
    if(document.getElementById("cart") != null) {
        document.getElementById("cart").remove();
    }

    if(window.location.href.includes("checkout")) {
        location.reload();
        return;
    }

    var container = document.createElement('div');
    container.id = "cart";

    var items = '';
    for(var i = 0; i < data.length; i++) {
        items += '<li class="list-group-item"><a href="/viewBook/'+ data[i].isbn + '">' + data[i].title + '<br />by ' + data[i].author + '</a><a data-isbn="'+ data[i].isbn +'" href="#" onclick="removeFromCart(this.getAttribute(' + "'data-isbn'" + '));">Remove</a></li>';
    }

    var template = '<div class="card" style="width: 18rem; position:fixed; top:0; right:0;">' +
          '<div class="card-body">' +
            '<h5 class="card-title">Your Cart</h5>' +
          '</div>' +
          '<ul class="list-group list-group-flush">' +
          items +
          '</ul>' +
          '<div class="card-body">' +
            '<a class="btn btn-dark" href="/checkout" class="card-link">Checkout</a>'
          '</div>' +
          '</div>';

    container.innerHTML = template;
    document.body.prepend(container);
}