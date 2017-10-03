import React from 'react';
import NavigationContainer from '../containers/NavigationContainer';


import Enzyme, {mount, shallow, render} from 'enzyme';


import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({adapter: new Adapter()});

import {MemoryRouter} from 'react-router-dom';

describe('Navigation Component', () => {

    it('renders without crashing', () => {
        let wrapper = shallow(<NavigationContainer> <div /> </NavigationContainer>);
        expect(1).to.equal();
    });

    it('should load drinks inventory page by default', function () {
        let wrapper = mount(<NavigationContainer/>);
        expect(wrapper.text()).toContain('Loading drinks');
    });

    it('should load add drinks when requested', function () {
        let wrapper
            = mount(<MemoryRouter>
                        <NavigationContainer/>
                     </MemoryRouter>);
        const addButton = wrapper.find('.addDrinkButton');
        addButton.simulate('submit');
        // expect(wrapper.find('.newDrink')).not.toBeTruthy();
    });

});


