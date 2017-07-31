import React from 'react';
import AddDrinkForm from './AddDrinkForm';

class DrinksInventory extends React.Component{

  constructor() {
    super();

    this.state = {
      drinkList :
      [
        {
          name: "coke",
          quantity: 0,
          imgSrc: "",
        }
      ]
    }
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
                  <button>+</button>
                </td>
              </tr>
              <tr>
                <td>
                  <input type="number" className="counter" value={drink.quantity} onChange={event => this.updateDrink(event, drink)}/>
                </td>
              </tr>
              <tr>
                <td>
                  {drink.name}
                </td>
              </tr>
              <tr>
                <td>
                  <button>-</button>
                </td>
              </tr>
            </tbody>
          </table>
        </td>
      </tr>
    );
  }

  updateDrink = (event, drink) => {
    event.preventDefault();

    this.setState({
      drinkList: [
        {
          name: drink.name,
          quantity: event.target.value,
          imgSrc: drink.imgSrc,
        }
      ]
    });
  }

  render() {
    return(
      <div>
        <h2>Inventory</h2>
        <table>
          <tbody>
            {this.state.drinkList.map(drink => this.displayDrinks(drink))}
          </tbody>
        </table>
      </div>
    );
  }
}

DrinksInventory.defaultProps = {
  drinkList: {},
}

export default DrinksInventory;
