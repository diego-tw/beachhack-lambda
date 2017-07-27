import React, { Component } from 'react';
import './App.css';
import Inventory from './DrinksInventory';

class App extends Component {
  constructor(){
    super();

    this.addDrink = this.addDrink.bind(this);
    this.updateDrink = this.updateDrink.bind(this);

    this.state={
      drinksInventory: {}
    };
  }
  addDrink(drink) {
    const drinks = {...this.state.drinksInventory};

    // var drinkName = drink.getName();
    // currentStocklevel + count if count < 0 return 0

    const timestamp = Date.now();
    drinks[`drink-${timestamp}`] = drink;

    this.setState({drinksInventory: drinks});
  }
  updateDrink(key, updatedDrink) {
    const drinks = {...this.state.drinksInventory};
    drinks[key] =  updatedDrink;
    console.log(key);
    this.setState({drinks});
}
  render() {
    return (
      <div className="App">
        <Inventory
          addDrink={this.addDrink}
          updateDrink={this.updateDrink}
          drinks={this.drinks}/>
      </div>
    );
  }
}

export default App;