const buildDrinksListRequest = () => {
    let httpRequest = buildRequest();
    httpRequest.open('GET', 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda');
    httpRequest.withCredentials = true;
    return httpRequest;
};

const buildUpdateDrinkRequest = () => {
    let httpRequest = buildRequest();
    httpRequest.open('POST', 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda');
    httpRequest.withCredentials = true;
    return httpRequest;
}

const parseResponse = responseData => {
    return Object.keys(responseData).map(drink => {
        return {
            name: drink,
            quantity: responseData[drink],
        };
    });
}

const buildRequest = () => {
    var httpRequest = new XMLHttpRequest();

    httpRequest.onreadystatechange = () => {
    	if (httpRequest.readyState === 4)
    	{
    		if (httpRequest.status >= 200 && httpRequest.status <= 400)
    		{
    			parseResponse(httpRequest.responseText);
    		}
            else
            {
                console.log(httpRequest.responseText);
            }
    	}
    };

    return httpRequest;
}

const ApiService = {buildUpdateDrinkRequest, buildDrinksListRequest}

export default ApiService;
