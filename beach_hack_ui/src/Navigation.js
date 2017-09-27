import React from 'react';
import PropTypes from 'prop-types';
import AddDrinkForm from './AddDrinkForm';
import DrinksInventory from './DrinksInventory';

import {
    BrowserRouter as Router,
    Route
} from 'react-router-dom'

class Navigation extends React.Component {

    constructor(props) {
        super(props);
        this.state = ({title: "Inventory"});
    }

    render() {

        const {title} = this.state;

        return (
            <div>
                <nav>
                    <ul>
                        <li><h3>{title}</h3></li>
                        <li>
                            <button onClick={() => {
                                this.showAddDrink();
                            }}>Add Drink
                            </button>
                        </li>
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

    showAddDrink = () => {
        this.setState({title: "Add Drink"});
        window.location.href = "/addDrink";
    }
}

Navigation.propTypes = {
    title: PropTypes.string,
};

Navigation.defaultProps = {
    title: "Title",
};

export default Navigation;
