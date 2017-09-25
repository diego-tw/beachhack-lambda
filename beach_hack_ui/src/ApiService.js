const buildDrinksListRequest = () => {
    var httpRequest = createCORSRequest('GET', 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda');
    httpRequest.withCredentials = true;
    return httpRequest;
};

const buildUpdateDrinkRequest = () => {
    var httpRequest = createCORSRequest('POST', 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda');
    httpRequest.withCredentials = true;
    return httpRequest;
};

const createCORSRequest = (method, url) => {
    var httpRequest = new XMLHttpRequest();
    if ("withCredentials" in httpRequest) {
        httpRequest.open(method, url, true);
    } else if (typeof XDomainRequest !== "undefined") {
        httpRequest = new XDomainRequest();
        httpRequest.open(method, url);
    } else {
        httpRequest = null;
        console.log("withCredentials not supported")
    }

    httpRequest = addRequestResponse(httpRequest);

    return httpRequest;
};

const addRequestResponse = httpRequest => {
    httpRequest.onloadstart = () => {
    };

    httpRequest.onload = () => {

        if (httpRequest.readyState === 4) {
            if (httpRequest.status >= 200 && httpRequest.status <= 400) {
                parseResponse(httpRequest.responseText);
            }
            else {
                console.log("Response Text: " + httpRequest.responseText + httpRequest.status);
            }
        }
    };

    httpRequest.onerror = () => {
    };

    return httpRequest;
};


const parseResponse = responseData => {
    console.log(responseData);

    return Object.keys(responseData).map(drink => {
        return {
            name: drink,
            quantity: responseData[drink],
        };
    });
};

const ApiService = {buildUpdateDrinkRequest, buildDrinksListRequest}

export default ApiService;
