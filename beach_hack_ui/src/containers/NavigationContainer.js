import React from 'react';
import PropTypes from 'prop-types';

import Navigation from '../components/Navigation';


class NavigationContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = ({title: "Current Inventory"});
    }

    componentDidMount(){

    }

    render() {
        return (
            <Navigation
                props={this.props}
            />
        )
    }
}

NavigationContainer.propTypes = {
    title: PropTypes.string,
};

NavigationContainer.defaultProps = {
    title: "Title",
};

export default NavigationContainer;
