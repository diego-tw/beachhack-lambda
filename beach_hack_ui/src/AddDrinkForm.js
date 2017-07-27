import React, { Component } from 'react';

class AddDrinkForm extends Component{
  createDrink(event){
    event.preventDefault();
    const drink = {
      // image: this.image.value,
      name: this.name.value,
      count: this.count.value
    }
    this.props.addDrink(drink);
  }
  render() {
    return(
      <form className="newDrink" onSubmit={(e) => this.createDrink(e)}>
        <input type="text" placeholder="Drink Name" ref={(input) => {this.name = input}}/>
        <input type="text" placeholder="Drink Count" ref={(input) => {this.count = input}}/>
        <button type="submit"> Add Drink</button>
      </form>
    );
  }
}

export default AddDrinkForm;
