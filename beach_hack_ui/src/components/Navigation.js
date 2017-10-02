import React from 'react';
import AddDrinkForm from './AddDrinkForm';
import DrinksInventoryContainer from '../containers/DrinksInventoryContainer';

import {
    BrowserRouter as Router,
    Route
} from 'react-router-dom'

import './styles/Navigation.css'

const Navigation = props =>
    <div>
        <nav className="navigationBar">
            <ul>
                <li id="title"><a href="/">Current Inventory</a></li>
                <li>
                    <button
                        className="addDrinkButton"
                        id="add-drink-button"
                        onClick={() => {
                            window.location.href = "/addDrink"
                        }}>
                        Add A Drink
                    </button>
                </li>
            </ul>
        </nav>
        <div id="main-content">
            <Router>
                <div>
                    <Route exact path="/" component={DrinksInventoryContainer}/>
                    <Route path="/addDrink" component={AddDrinkForm}/>
                </div>
            </Router>
        </div>
    </div>


export default Navigation;
