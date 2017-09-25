import React from 'react';
import PropTypes from 'prop-types';
import ApiService from "./ApiService";

class DrinksInventory extends React.Component {

    constructor(props) {
        super(props);
        var drinksList = undefined;
        this.state = {
            drinksList: drinksList,
        };
        ApiService.sendRequest(this);
    }

    displayView = () => {
        let display = null;
        if (this.state.drinksList !== undefined) {
            display = (
                <table>
                    <tbody>
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
            <tr key={drink.name}>
                <td>
                    <img src={drink.imageSrc} alt="logo"/>
                </td>

                <td>
                    <button
                        // onClick={event => this.setState(this.props.updateDrinkQuantity(event, drink))}
                        value="1"> +
                    </button>
                </td>

                <td>
                    {drink.name}: {drink.quantity}
                </td>

                <td>
                    <button
                        // onClick={event => this.setState(this.props.updateDrinkQuantity(event, drink))}
                        value="-1"> -
                    </button>
                </td>
            </tr>

        );
    };

    componentDidUpdate(prevProps, prevState) {
    }

    render() {
        let display = this.displayView();
        return (
            <div>
                <h2>Inventory</h2>
                <div id="drinks-table">{display}</div>
            </div>
        );
    }
}

DrinksInventory
    .propTypes = {
    // getDrinksList: PropTypes.func.isRequired,
    updateDrinkQuantity: PropTypes.func.isRequired,
    drinksList: PropTypes.arrayOf(PropTypes.object),
}

DrinksInventory
    .defaultProps = {
    drinkList: {},
}

export default DrinksInventory;
