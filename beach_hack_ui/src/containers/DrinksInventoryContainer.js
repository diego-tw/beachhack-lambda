import React from 'react';

import DrinksInventory from '../components/DrinksInventory';

import ApiService from "../Services/ApiService";


class DrinksInventoryContainer extends React.Component {

    constructor(props) {
        super(props);
        var drinksList = undefined;
        this.state = {
            drinksList: drinksList,
            updatingQuantity: false,
            loadingDrinks: true
        };
    }

    componentWillMount() {

    }

    componentDidMount() {
        let fetch = ApiService.getDrinksList(this);
        fetch
            .then((drinksList) => {
                this.setState({drinksList: drinksList});
                this.setState({loadingDrinks: false});

            })
            .catch((error) => {
                console.log(error);
            });
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
                    />
                </div>
            </div>
        );
    }

    adjustDrinkQuantity(drink, amount) {
        let postInfo = JSON.stringify({drinkName: drink.name, quantity: amount});
        this.setState({updatingQuantity: true});
        let fetch = ApiService.updateDrinksList(this, postInfo);
        fetch.then((drinksList) => {
            this.setState({drinksList: drinksList})
            this.setState({updatingQuantity: false})
        })
    }

}

export default DrinksInventoryContainer;
