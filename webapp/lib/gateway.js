var gateway = new function () {
    this.backendUri = "http://localhost:8889/api";
    this.backendSecurePrefix = "Bearer ";

    this.nodesPath = "/nodes";
    this.filesPath = "/files";

    this.authentication = (username, password)=> {
        var data = {username: username, password: password};
        var token;
        $.ajax({
            type: "POST",
            url : this.backendUri + "/authentication",
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            success: function(res) {
                token = res;
            },
            error: function(req, err) {
                console.log(req);
                throw err;

            }
        });
        return token;
    }
    
    this.getAllNodes = ()=> {
        var nodes;
        $.ajax({
            url : this.backendUri + this.nodesPath,
            type: "GET",
            async: false,
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                nodes = res;
                console.log("getAllNodes(data):" + textStatus);
                console.log(res);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("getAllNodes(errorThrown):" + errorThrown);
            }
        });
        return nodes;
    }

    this.addNewNode = (parentId)=> {
        var data = {parentId: parentId};
        var node;
        $.ajax({
            type: "POST",
            url : this.backendUri + this.nodesPath,
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                node = JSON.parse(res);
                console.log("addNewNodes(data):" + textStatus);
                console.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("addNewNodes(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

    this.removeNode = (nodeId)=> {
        var data = {nodeId: nodeId};
        var node;
        $.ajax({
            type: "DELETE",
            url : this.backendUri + this.nodesPath,
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                node = JSON.parse(res);
                console.log("addNewNodes(data):" + textStatus);
                console.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("addNewNodes(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

    this.sendFile = (file)=> {
        var formData = new FormData();
        formData.append('file', file[0]);

        var fileId;
        $.ajax({
            type: "POST",
            url : this.backendUri + this.filesPath,
            async: false,
            data: formData,
            cache: false,
            timeout: 1000,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                console.log(res);
                fileId = JSON.parse(res);
                console.log("sendFile(data):" + textStatus);
                console.log(fileId);
            },
            error: function(req, err) {
                console.log(req);
                throw err;
            }
        });
        return fileId;
    }

    this.getFilePathById = (fileId)=> {
        return this.backendUri + this.filesPath + "/" + fileId;
    }

}