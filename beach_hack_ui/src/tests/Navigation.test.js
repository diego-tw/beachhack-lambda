import React from 'react';
import Navigation from '../components/Navigation';

const ReactDOMServer = require('react-dom/server');

import Enzyme,{ mount, shallow, render } from 'enzyme';

import DrinksInventory from "../components/DrinksInventory";

import Adapter from 'enzyme-adapter-react-15';

Enzyme.configure({ adapter: new Adapter() });


import { MemoryRouter } from 'react-router-dom';

describe('Navigation Component', () => {

    it('renders without crashing', () => {
        let wrapper = render(<Navigation />);
        expect(wrapper.find('div').length).not.toBeLessThan(1);
    });


    it('should load drinks inventory page by default', function () {
        let wrapper = mount(<Navigation />);
        expect(wrapper.text()).toContain('Loading drinks');
    });

});


