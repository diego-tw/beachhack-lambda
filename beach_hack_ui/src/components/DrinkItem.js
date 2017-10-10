import React from 'react';
import PropTypes from 'prop-types';

import Icons from '../resources/icons/Icons';


class DrinkItem extends React.Component {

    constructor(props) {
        super(props);
        const {drink} = this.props;
        this.state = {drink: drink};
    };

    render() {
        const {drink} = this.state;
        return (

            <tr key={drink.name}>
                <td id="drink-name">
                    {drink.name}
                </td>
                <td >
                    <button
                        id="deduct-button"
                        onClick={(event) => {
                            event.preventDefault();
                            this.props.adjustDrinkQuantity(drink, -1);
                        }}
                        value="-1"
                    >
                        <Icons size={64} icon='remove_circle'/>
                    </button>
                </td>
                <td id="drink-quantity">
                    {drink.quantity}
                </td>
                <td>
                    <button id="plus-button"
                            onClick={(event) => {
                                event.preventDefault();
                                this.props.adjustDrinkQuantity(drink, 1);
                            }}
                            value="1">
                        <Icons size={64} icon='add_circle'/>
                    </button>
                </td>
            </tr>
        )
    }
}

DrinkItem.propTypes = {
    drink: PropTypes.object,
    adjustDrinkQuantity: PropTypes.func
};


export default DrinkItem;