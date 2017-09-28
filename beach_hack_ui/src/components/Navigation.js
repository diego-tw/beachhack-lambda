import React from 'react';
import PropTypes from 'prop-types';
import AddDrinkForm from './AddDrinkForm';
import DrinksInventory from './DrinksInventory';

import {
    BrowserRouter as Router,
    Route
} from 'react-router-dom'

import './styles/Navigation.css'

class Navigation extends React.Component {

    constructor(props) {
        super(props);
        this.state = ({title: "Inventory"});
    }

    render() {

        const {title} = this.state;

        return (
            <div>
                <nav className="navigationBar">
                    <ul>
                        <li id="title">{title}</li>
                        <li><a href="/">Current Inventory</a> </li>
                        <li><button
                            className="addDrinkButton"
                            id="add-drink-button"
                            onClick={()=>{
                            window.location.href = "/addDrink"
                        }}>Add A Drink</button> </li>
                    </ul>
                </nav>
                <Router>
                    <div>
                        <Route exact path="/" component={DrinksInventory} />
                        <Route path="/addDrink" component={AddDrinkForm} />
                    </div>
                </Router>
            </div>
        )
    }

}

Navigation.propTypes = {
    title: PropTypes.string,
};

Navigation.defaultProps = {
    title: "Title",
};

export default Navigation;
