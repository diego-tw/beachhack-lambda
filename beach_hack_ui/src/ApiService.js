const buildDrinksListRequest = (state) => {
    var httpRequest = createCORSRequest('GET', 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda', state);
    httpRequest.withCredentials = true;
    return httpRequest;
};

const buildUpdateDrinkRequest = () => {
    var httpRequest = createCORSRequest('POST', 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda');
    httpRequest.withCredentials = true;
    return httpRequest;
};

const createCORSRequest = (method, url, state) => {
    var httpRequest = new XMLHttpRequest();

    console.log(state);
    httpRequest = addRequestResponse(httpRequest, state);

    if ("withCredentials" in httpRequest) {
        httpRequest.open(method, url, true);
    } else if (typeof XDomainRequest !== "undefined") {
        httpRequest = new XDomainRequest();
        httpRequest.open(method, url);
    } else {
        httpRequest = null;
        console.log("withCredentials not supported")
    }


    return httpRequest;
};

const addRequestResponse = (httpRequest, state) => {
    httpRequest.onloadstart = () => {
    };

    httpRequest.onload = () => {

        if (httpRequest.readyState === 4) {
            if (httpRequest.status >= 200 || httpRequest.status <= 400) {
                let drinkList = parseResponse(httpRequest.responseText);
                state.setState({drinksList: drinkList});
            }
            else {
                console.log("Response Text: " + httpRequest.responseText + httpRequest.status);
            }
        }
    };

    httpRequest.onerror = () => {
        console.log("Error with API call");
    };

    return httpRequest;
};


const parseResponse = responseData => {
    var responseJSON = JSON.parse(responseData);
    return Object.keys(responseJSON).map(drink => {
        return {
            name: drink,
            quantity: responseJSON[drink],
        };
    });
};

const sendRequest = state => {
    console.log(state);
    var httpRequest = buildDrinksListRequest(state);
    httpRequest.send();

};

const ApiService = {buildUpdateDrinkRequest, buildDrinksListRequest, sendRequest}

export default ApiService;
