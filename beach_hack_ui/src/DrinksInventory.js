import React from 'react';
import PropTypes from 'prop-types';

class DrinksInventory extends React.Component{

  constructor(props) {
    super(props);
    const drinksList = props.getDrinksList();
    console.log("Printing drink list:\n" + drinksList);
    this.state = {
      drinksList: drinksList === undefined ? [] : drinksList
    };
  }

  displayDrinks = drink => {
    return (
      <tr key={drink.name}>
        <td>
          <table>
            <tbody>
              <tr>
                <td>
                  <img src={drink.imageSrc} alt="logo"/>
                </td>
              </tr>
              <tr>
                <td>
                  <button onClick={event => this.setState(this.props.updateDrinkQuantity(event, drink))} value="1">+</button>
                </td>
              </tr>
              <tr>
                <td>
                  {drink.name}: {drink.quantity}
                </td>
              </tr>
              <tr>
                <td>
                  <button onClick={event => this.setState(this.props.updateDrinkQuantity(event, drink))} value="-1">-</button>
                </td>
              </tr>
            </tbody>
          </table>
        </td>
      </tr>
    );
  }

  render() {
    return(
      <div>
        <h2>Inventory</h2>
        <table>
          <tbody>
            {this.state.drinksList.forEach(drink => this.displayDrinks(drink))}
          </tbody>
        </table>
      </div>
    );
  }
}

DrinksInventory.propTypes = {
  getDrinksList: PropTypes.func.isRequired,
  updateDrinkQuantity: PropTypes.func.isRequired,
  drinksList: PropTypes.arrayOf(PropTypes.object),
}

DrinksInventory.defaultProps = {
  drinkList: [],
}

export default DrinksInventory;
