import React, {Component} from 'react';
import './App.css';
import Inventory from './DrinksInventory';
import ApiService from "./ApiService";

class App extends Component {
    constructor() {
        super();

        // this.drinkListReq = ApiService.buildDrinksListRequest();
        // this.updateDrinkReq = ApiService.buildUpdateDrinkRequest();
    }

    render() {
        return (
            <div className="App">
                <Inventory
                    // getDrinksList = {() => {
                    //     return ApiService.sendRequest(state);
                    // }}
                    updateDrinkQuantity={
                        (that, event, drink, updateAmount) => {
                            event.preventDefault();
                            var postInfo = JSON.stringify({drinkName: drink.name , quantity: updateAmount});
                            this.updateDrinkReq = ApiService.buildUpdateDrinkRequest(that);
                            this.updateDrinkReq.send(postInfo);
                        }
                    }
                />
            </div>
        );
    }
}

export default App;
