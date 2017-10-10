import React from 'react';

import DrinkItem from './DrinkItem';
import './styles/DrinksInventory.css';

import Spinner from 'react-spinkit';
import Icons from '../resources/icons/Icons';


const DrinksInventory = (props) =>
    props.loadingDrinks ?
        <div>
            <h3>Loading drinks....</h3>
            <Spinner id="spinner" name="wandering-cubes" color="blue" fadeIn="none"/>
        </div>

        :
        <div>
            <table>
                <tbody>
                <tr>
                    <th id="drink-name-title">
                        <button onClick={(event) => {
                            event.preventDefault();
                            props.orderByName();
                        }}>Drink
                            <Icons color="#000000" icon='down_arrow'/>

                        </button>
                    </th>
                    <th></th>
                    <th id="drink-qty-title">
                        <button onClick={(event) => {
                            event.preventDefault();
                            props.orderByQuantity();
                        }}>Qty <Icons
                            color="#000000"
                            icon='down_arrow'/>
                        </button>
                    </th>
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
