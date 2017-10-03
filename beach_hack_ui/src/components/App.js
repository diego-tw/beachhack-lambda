import 'babel-polyfill';

import React from 'react';
import './styles/App.css';

import NavigationContainer from '../containers/NavigationContainer';

import {
    BrowserRouter as Router,
    Route
} from 'react-router-dom'

class App extends React.Component {

    render() {
        return (
            <div className="App">
                <Router>
                    <div>
                        <Route exact path="" component={NavigationContainer}/>
                    </div>
                </Router>
            </div>
        );
    }
}
export default App;
