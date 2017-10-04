import ApiService from '../Services/ApiService';

import sinon from 'sinon';
import Enzyme, {mount, shallow, render} from 'enzyme';
import {expect} from 'chai';



import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({adapter: new Adapter()});

describe('API Service', () => {
    it('makes a GET request for todo items', function () {
        let fetch = sinon.useFakeXMLHttpRequest();
        // sinon.stub(jQuery, 'ajax');
        // ApiService.getDrinksList();

    });
});
