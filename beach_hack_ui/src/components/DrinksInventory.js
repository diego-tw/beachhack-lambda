import React from 'react';
import PropTypes from 'prop-types';
import Icons from '../resources/icons/Icons';


import './styles/DrinksInventory.css';
import AddDrinkForm from "./AddDrinkForm";
import ApiService from "../Services/ApiService";



class DrinksInventory extends React.Component {

    constructor(props) {
        super(props);
        var drinksList = undefined;
        this.state = {
            drinksList: drinksList,
        };
    }

    displayView = () => {
        let display = null;
        if (this.state.drinksList !== undefined) {
            display = (
                <table>
                    <tbody>
                    <tr>
                        <th></th>
                        <th>Drink</th>
                        <th>Quantity</th>
                        <th></th>
                    </tr>
                    {this.state.drinksList.map((drink) => this.displayDrinks(drink))}
                    </tbody>
                </table>
            );
        } else {
            display = (<h3>Loading drinks....</h3>);
        }
        return display;
    };

    displayDrinks = drink => {
        return (
            <DrinkItem key={drink.name + drink.quantity}
                       drink={drink}
                       adjustDrinkQuantity={
                           (drink, amount) => {
                               let postInfo = JSON.stringify({drinkName: drink.name, quantity: amount});
                               ApiService.updateDrinksList(this, postInfo);
                           }
                       }
            />
        );
    };

    componentWillMount() {
        ApiService.getDrinksList(this);
    }


    render() {
        let display = this.displayView();
        return (
            <div>
                < div
                    id="drinks-table"> {display}
                </div>
            </div>
        );
    }

    loadAddDrinkModule = () => {
        return (<AddDrinkForm/>);
    }
}


class DrinkItem extends React.Component {

    constructor(props) {
        super(props);
        const {drink} = this.props;
        this.state = {drink: drink};
    }

    adjustDrinkQuantity = (drink, amount) => {
        this.props.adjustDrinkQuantity(drink, amount);
    };

    render() {
        const {drink} = this.state;
        return (

            <tr key={drink.name}>
                <td>
                    <button
                        id="deduct-button"
                        onClick={(event) => {
                            event.preventDefault();
                            this.adjustDrinkQuantity(drink, -1);
                        }}
                        value="-1"
                    >
                        <Icons icon='remove_circle'/>
                    </button>
                </td>
                <td id="drink-name">
                    {drink.name}
                </td>
                <td id="drink-quantity">
                    {drink.quantity}
                </td>
                <td>
                    <button id="plus-button"
                            onClick={(event) => {
                                event.preventDefault();
                                this.adjustDrinkQuantity(drink, 1);
                            }}
                            value="1">
                        <Icons icon='add_circle'/>

                    </button>
                </td>
            </tr>
        )
    }
}

DrinkItem.propTypes = {
    drink: PropTypes.object,
}

DrinksInventory
    .propTypes = {};

DrinksInventory
    .defaultProps = {
    drinkList: {},
};

export default DrinksInventory;
