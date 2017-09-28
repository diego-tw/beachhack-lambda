import React, {Component} from 'react';

import ApiService from "../Services/ApiService";

import {
    Redirect
} from 'react-router-dom'


class AddDrinkForm extends Component {

    constructor(props) {
        super(props);
        let drink = {drinkName: null, quantity: null};
        this.state = ({
            drink: drink,
            hasSavedDrink: false
        });
    }

    createDrink = (event, drink) => {
        event.preventDefault();
        let postInfo = JSON.stringify({drinkName: drink.name, quantity: drink.quantity});
        ApiService.addNewDrink(this, postInfo);
    };

    render() {

        let drink = {
            name: "",
            quantity: 0,
        };

        const {hasSavedDrink} = this.state;

        return (
            <div>
                {hasSavedDrink ? <Redirect to=""/> :
                    <form className="newDrink" onSubmit={(event) => this.createDrink(event, drink)}>
                        <input type="text" placeholder="Drink Name" onChange={event => {
                            drink.name = event.target.value
                        }}/>
                        <input type="number" placeholder="Drink Count" onChange={event => {
                            drink.quantity = event.target.value
                        }}/>
                        <button type="submit"> Add Drink</button>
                    </form>
                }
            </div>
        );
    }
}

export default AddDrinkForm;
