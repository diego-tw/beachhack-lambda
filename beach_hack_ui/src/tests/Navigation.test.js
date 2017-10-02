import React from 'react';
import Navigation from '../components/Navigation';

const ReactDOMServer = require('react-dom/server');

import Enzyme, {mount, shallow, render} from 'enzyme';

import DrinksInventory from "../components/DrinksInventory";

import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({adapter: new Adapter()});

import {MemoryRouter} from 'react-router-dom';

describe('Navigation Component', () => {

    it('renders without crashing', () => {
        let wrapper = shallow(<Navigation> <div /> </Navigation>);
        const html = wrapper.html();
        expect(html.indexOf('<div>') !== -1).toBeTruthy();
    });

    it('should load drinks inventory page by default', function () {
        let wrapper = mount(<Navigation/>);
        expect(wrapper.text()).toContain('Loading drinks');
    });

    it('should load add drinks when requested', function () {
        let wrapper
            = mount(<MemoryRouter>
                        <Navigation/>
                     </MemoryRouter>);
        const addButton = wrapper.find('.addDrinkButton');
        addButton.simulate('submit');
        // expect(wrapper.find('.newDrink')).not.toBeTruthy();
    });

});


