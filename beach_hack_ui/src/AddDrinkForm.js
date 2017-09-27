import React, {Component} from 'react';

import ApiService from "./Services/ApiService";


class AddDrinkForm extends Component {

    constructor(props) {
        super(props);
        let drink = {drinkName: null, quantity: null};
        this.state = ({drink: drink});
    }

    createDrink = (event, drink) => {
        console.log(drink);
        event.preventDefault();
        let postInfo = JSON.stringify({drinkName: drink.name, quantity: drink.quantity});

        ApiService.updateDrinksList(this, postInfo);
        window.location.href = '/';
    };

    render() {

        let drink = {
            name: "",
            count: 0,
        };

        return (
            <form className="newDrink" onSubmit={(event) => this.createDrink(event, drink)}>
                <input type="text" placeholder="Drink Name" onChange={event => {
                    drink.name = event.target.value
                }}/>
                <input type="number" placeholder="Drink Count" onChange={event => {
                    drink.count = event.target.value
                }}/>
                <button type="submit"> Add Drink</button>
            </form>
        );
    }
}

export default AddDrinkForm;
