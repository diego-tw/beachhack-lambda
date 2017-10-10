import React from 'react';

import DrinksInventory from '../components/DrinksInventory';

import ApiService from "../Services/ApiService";
import _ from 'lodash';


class DrinksInventoryContainer extends React.Component {

    constructor(props) {
        super(props);
        var drinksList = undefined;
        this.state = {
            drinksList: drinksList,
            updatingQuantity: false,
            loadingDrinks: true,
            sortingType: "name"
        };
    }

    componentWillMount() {

    }

    componentDidMount() {
        let fetch = ApiService.getDrinksList(this);
        fetch
            .then((drinksList) => {
                let sortedDrinkList = this.sortDrinkList(drinksList);

                this.setState({drinksList: sortedDrinkList});
                this.setState({loadingDrinks: false});

            })
            .catch((error) => {
                console.log(error);
            });
    }

    orderByQuantity() {
        let {drinksList} = this.state;
        let sortedDrinksList = _.sortBy(drinksList, (drink) => {
            return drink.quantity
        });
        this.setState({drinksList: sortedDrinksList});
        this.setState({sortingType: "quantity"});
    }

    orderByName() {
        let {drinksList} = this.state;
        let sortedDrinksList = _.sortBy(drinksList, (drink) => {
            return drink.name
        });
        this.setState({drinksList: sortedDrinksList});
        this.setState({sortingType: "name"});

    }

    sortDrinkList(drinkList) {
        if (this.state.sortingType === "name") {
            return _.sortBy(drinkList, (drink) => {
                return drink.name;
            });
        } else {
            return _.sortBy(drinkList, (drink) => {
                return drink.quantity;
            });
        }
    }

    render() {
        return (
            <div className="main-body">
                <div id="drinks-table">
                    <DrinksInventory
                        loadingDrinks={this.state.loadingDrinks}
                        drinksList={this.state.drinksList}
                        updatingQuantity={this.state.updatingQuantity}
                        adjustDrinkQuantity={
                            (drink, amount) => {
                                this.adjustDrinkQuantity(drink, amount)
                            }
                        }
                        orderByQuantity={
                            () => this.orderByQuantity()
                        }
                        orderByName={
                            () => this.orderByName()
                        }
                    />
                </div>
            </div>
        );
    }

    adjustDrinkQuantity(drink, amount) {
        let postInfo = JSON.stringify({drinkName: drink.name, quantity: amount});
        this.setState({updatingQuantity: true});

        let updatedAmount = drink.quantity + amount;
        let {drinksList} = this.state;
        // drinksList.get(drink.name).quantity = updatedAmount;
        let updatedDrinksList = drinksList.map(drinkL => {
            if (drinkL.name === drink.name) {
                drinkL.quantity = updatedAmount;
                return drinkL;
            }
            return drinkL;
        });
        let sortedDrinksList = this.sortDrinkList(updatedDrinksList);


        this.setState({drinksList: sortedDrinksList});

        let fetch = ApiService.updateDrinksList(this, postInfo);
        fetch.then((drinksList) => {
            // let sortedDrinksList = this.sortDrinkList(drinksList);

            // this.setState({drinksList: sortedDrinksList})
            this.setState({updatingQuantity: false})
        })
            .catch((err) => {
                alert("ERROR: Cannot connect please check connection" );
            })
    }

}

export default DrinksInventoryContainer;
