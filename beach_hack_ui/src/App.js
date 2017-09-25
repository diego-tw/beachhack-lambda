import React, {Component} from 'react';
import './App.css';
import Inventory from './DrinksInventory';
import ApiService from "./ApiService";

class App extends Component {
    constructor() {
        super();

        // this.drinkListReq = ApiService.buildDrinksListRequest();
        this.updateDrinkReq = ApiService.buildUpdateDrinkRequest();
    }

    render() {
        return (
            <div className="App">
                <Inventory
                    // getDrinksList = {() => {
                    //     return ApiService.sendRequest(state);
                    // }}
                    updateDrinkQuantity={
                        (event, drink) => {
                            event.preventDefault();
                            this.updateDrinkReq.send({"drinkName": drink.name, "quantity": drink.quantity});
                        }
                    }
                />
            </div>
        );
    }
}

export default App;
