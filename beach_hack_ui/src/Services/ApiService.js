import 'whatwg-fetch';
import _ from 'lodash';

const URL = 'https://15toyx5nv6.execute-api.us-east-2.amazonaws.com/prod/DrinksInventoryLambda';

const getDrinksList = (that) => {

    let myInit = {
        method: 'GET',
        mode: 'cors',
    };

    return constructFetch(that, myInit);

};

const updateDrinksList = (that, body) => {

    let myInit = {
        method: 'POST',
        mode: 'cors',
        body: body,
    };
    return constructFetch(that, myInit);
};

const addNewDrink = (that, body) => {

    let myInit = {
        method: 'POST',
        mode: 'cors',
        body: body,
    };
    return constructFetch(that, myInit);
};

const constructFetch = (that, init) => {
    return fetch(URL, init)
        .then((response) => {
            return response.json();
        })
        .then((drinksListJSON) => {
            let response = parseResponse(drinksListJSON)
            return _.sortBy(response, (drink) => {return drink.name});
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
