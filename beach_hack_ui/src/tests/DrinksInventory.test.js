import React from 'react';
import DrinksInventoryContainer from '../containers/DrinksInventoryContainer';
import DrinksInventory from '../components/DrinksInventory';

import Enzyme, {mount, shallow, render} from 'enzyme';
import {expect} from 'chai';


import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({adapter: new Adapter()});

import {MemoryRouter} from 'react-router-dom';

describe('DrinksInventory Contianer', () => {
    it('should dispay list of drinks', function () {
        let drinksList = [
            {name: "coke", quantity: 1}, {name: "pepsi", quantity: 1}
        ];
        let wrapper = shallow(<DrinksInventory
                                drinksList = {drinksList}/>);
        // expect(wrapper.find('drink-name')).to.have.length(2);
        // expect(wrapper.props().drinksList).to.exist;
    });
});