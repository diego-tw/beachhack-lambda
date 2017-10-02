import React from 'react';

import DrinkItem from './DrinkItem';
import './styles/DrinksInventory.css';

import Spinner from 'react-spinkit';


const DrinksInventory = (props) =>
    props.loadingDrinks ?
        <div>
            <h3>Loading drinks....</h3>
            <Spinner id="spinner" name="wandering-cubes" color="blue" fadeIn="none"/>
        </div>

        :
        <div>
            {props.updatingQuantity ?
                <Spinner
                    id="spinner"
                    name="pacman"
                    color="blue"
                    fadeIn="none"
                /> : null}
            <table>
                <tbody>
                <tr>
                    <th></th>
                    <th>Drink</th>
                    <th>Quantity</th>
                    <th></th>
                </tr>
                {props.drinksList.map((drink) => displayDrinks(drink, props))}
                </tbody>
            </table>
        </div>


const displayDrinks = (drink, props) =>
    <DrinkItem key={drink.name + drink.quantity}
               drink={drink}
               adjustDrinkQuantity={
                   (drink, amount) => {
                       props.adjustDrinkQuantity(drink, amount)
                   }
               }
    />


export default DrinksInventory;
