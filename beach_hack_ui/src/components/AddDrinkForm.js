import React, {Component} from 'react';

import ApiService from "../Services/ApiService";

import {
    Redirect
} from 'react-router-dom'

import './styles/AddDrinkForm.css'


class AddDrinkForm extends Component {

    constructor(props) {
        super(props);
        this.state = ({
            drinkName: '',
            drinkQuantity: 0,
            hasSavedDrink: false,
            displayDrinkNameError: 'hidden',
            displayDrinkQuantityError: 'hidden'
        });
    }

    createDrink = (event) => {
        event.preventDefault();
        this.cleanErrors();

        if (this.state.drinkName === 0) {
            this.setState({displayDrinkNameError: 'display'});
            return;
        }
        if (this.state.drinkQuantity <= 0) {
            this.setState({displayDrinkQuantityError: 'display'});
            return;
        }
        let postInfo = JSON.stringify({drinkName: this.state.drinkName, quantity: this.state.drinkQuantity});
        let fetch = ApiService.addNewDrink(this, postInfo);
        fetch.then( (drinksList) => {
            this.setState({hasSavedDrink: true});
        });
    };

    cleanErrors() {
        this.setState({
            displayDrinkNameError: 'hidden',
            displayDrinkQuantityError: 'hidden'
        })
    }

    render() {
        const {hasSavedDrink} = this.state;

        return (
            <div id="add-drink-body">
                {hasSavedDrink ? <Redirect to=""/> :
                    <form className="newDrink" onSubmit={(event) => this.createDrink(event)}>
                        <input required id="drink-name-input" type="text" placeholder="Drink Name" onChange={event => {
                            this.setState({drinkName: event.target.value})
                        }}/>
                        <text className={this.state.displayDrinkNameError} id="drink-name-error-field">Error: Please
                            enter name for drink e.g Coke
                        </text>
                        <input required id="drink-quantity-input" type="number" placeholder="Drink Quantity"
                               onChange={event => {
                                   this.setState({drinkQuantity: event.target.value})
                               }}/>
                        <text className={this.state.displayDrinkQuantityError} id="drink-quantity-error-field">Error:
                            Please enter valid quantity for drink e.g 2
                        </text>
                        <button id="save-drink-button" type="submit"> Add Drink</button>
                    </form>
                }
            </div>
        );
    }
}

export default AddDrinkForm;
