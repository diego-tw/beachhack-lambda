import React from 'react';
import NavigationContainer from '../containers/NavigationContainer';

import sinon from 'sinon';
import Enzyme, {mount, shallow, render} from 'enzyme';
import {expect} from 'chai';


import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({adapter: new Adapter()});

import {MemoryRouter} from 'react-router-dom';

sinon.spy(NavigationContainer.prototype, 'componentDidMount');

describe('Navigation Container', () => {

    it('should mount componenet', () => {
        const wrapper = mount(<NavigationContainer />);
        expect(NavigationContainer.prototype.componentDidMount.calledOnce).to.equal(true);
    });

    it('should have link to current inventory', () => {
        let wrapper = shallow(<NavigationContainer> </NavigationContainer>);
        expect(wrapper.html()).to.contain('href="/"');
    });

    it('should have link to add a drink page', function () {
        let wrapper = shallow(<NavigationContainer />);
        expect(wrapper.html()).to.contain('Add A Drink');
    });


});


