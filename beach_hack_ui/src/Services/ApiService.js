import 'whatwg-fetch';

const URL = 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda';

const getDrinksList = (that) => {

    let myInit = {
        method: 'GET',
        mode: 'cors',
    };

    constructFetch(that, myInit);

};

const updateDrinksList = (that, body) => {

    let myInit = {
        method: 'POST',
        mode: 'cors',
        body: body,
    };
    constructFetch(that, myInit);
};

const addNewDrink = (that, body) => {

    let myInit = {
        method: 'POST',
        mode: 'cors',
        body: body,
    };
    constructFetch(that, myInit);
};

const constructFetch = (that, init) => {
    fetch(URL, init)
        .then((response) => {
            return response.json();
        })
        .then((drinksListJSON) => {
            return parseResponse(drinksListJSON);
        })
        .then((drinksList) => {
            that.setState({drinksList: drinksList});
            if(that.state.hasSavedDrink !== undefined) that.setState({hasSavedDrink: true});
        })
        .catch((err) => {

        });
};

const parseResponse = responseData => {
    var responseJSON = responseData;
    return Object.keys(responseJSON).map(drink => {
        return {
            name: drink,
            quantity: responseJSON[drink],
        };
    });
};

const ApiService = {updateDrinksList, getDrinksList, addNewDrink};

export default ApiService;
