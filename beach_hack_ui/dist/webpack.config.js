
var webpack = require('webpack');
var path = require('path');

var config = {
    entry: ['./src/components/app.js'],
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js'
    },
    module: {
        entry: ["babel-polyfill", "./app/js"],
        loaders: [

            { test: /\.js$/, loaders: ['babel'], exclude: /node_modules/ }
        ]
    }
}

module.exports = config;