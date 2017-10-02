import React from 'react';
import PropTypes from 'prop-types';

import DrinkItem from './DrinkItem';
import './styles/DrinksInventory.css';
import ApiService from "../Services/ApiService";

import  Spinner from 'react-spinkit';


class DrinksInventory extends React.Component {

    constructor(props) {
        super(props);
        var drinksList = undefined;
        this.state = {
            drinksList: drinksList,
            updatingQuantity: false
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
            display = (
                <div>
                    <h3>Loading drinks....</h3>
                    <Spinner id="spinner" name="wandering-cubes" color="blue" fadeIn="none"/>
                </div>
            );
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
                               this.setState({updatingQuantity: true});
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
            <div className="main-body">
                <div
                    id="drinks-table">
                    {this.state.updatingQuantity ?
                        <Spinner
                            id="spinner"
                            name="pacman"
                            color="blue"
                            fadeIn="none"
                        /> : null
                    }
                    {display}


                </div>
            </div>
        );
    }

}


DrinksInventory.propTypes = {};

DrinksInventory.defaultProps = {
    drinkList: {},
};

export default DrinksInventory;
