import React, { Component } from 'react';

class AddDrinkForm extends Component {

  createDrink = (event, drink) => {
    console.log(drink);
    event.preventDefault();
    this.props.addDrink(drink);
  }

  render() {

    let drink = {
      name: "",
      count: 0,
    }

    return(
      <form className="newDrink" onSubmit={(event) => this.createDrink(event, drink)}>
        <input type="text" placeholder="Drink Name" onChange={event => {drink.name = event.target.value}}/>
        <input type="number" placeholder="Drink Count" onChange={event => {drink.count = event.target.value}}/>
        <button type="submit"> Add Drink</button>
      </form>
    );
  }
}

export default AddDrinkForm;
