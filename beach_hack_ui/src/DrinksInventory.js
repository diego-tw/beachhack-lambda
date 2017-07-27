import React from 'react';
import AddDrinkForm from './AddDrinkForm';

class DrinksInventory extends React.Component{
  constructor() {
    super();

    this.handleChange = this.handleChange.bind(this);
  }
  handleChange(e, key){
    const drink = this.props.drinks[key];
    const updatedDrink = {...drink,
      [e.target.name]: e.target.value
    };
    this.props.updateDrink(key, updatedDrink)
  }
  renderInventory(key) {
    const drink = this.props.drinks[key];
    return(
      <div className="drink-edit" key={key}>
          <input
            type="text"
            name="name"
            value={drink.name}
            placeholder="Drink Name"
            onChange={(e) => {this.handleChange(e, key)}}></input>
          <input
            type="text"
            name="price"
            value={drink.price}
            placeholder="Drink Price"
            onChange={(e) => {this.handleChange(e, key)}}></input>
          <select
            name="status"
            value={drink.status}
            onChange={(e) => {this.handleChange(e, key)}}>
            <option value="available">Available!</option>
            <option value="unavailable">Sold Out!</option>
          </select>
          <textarea
            type="text"
            name="description"
            value={drink.desc}
            placeholder="Drink Description"
            onChange={(e) => {this.handleChange(e, key)}}></textarea>
          <input
            type="text"
            name="image"
            value={drink.image}
            placeholder="Drink Image"
            onChange={(e) => {this.handleChange(e, key)}}></input>
          <button onClick={() => this.props.removeDrink(key)}>Remove Drink</button>
      </div>
    );
  }
  render() {
    return(
      <div>
        <h2>Inventory</h2>
        {/* {Object.keys(this.props.drinks).map((key)=>this.renderInventory(key))} */}
        <button type="submit" updateDrink={this.props.updateDrink}>Update</button>
        <AddDrinkForm addDrink={this.props.addDrink}/>
      </div>
    );
  }
}

DrinksInventory.propTypes = {
  addDrink: React.PropTypes.func.isRequired,
  drinks: React.PropTypes.object.isRequired
};

export default DrinksInventory;
