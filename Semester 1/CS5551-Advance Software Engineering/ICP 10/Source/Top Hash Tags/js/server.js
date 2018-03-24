/**
 * Created by VenkatNag on 3/13/2018.
 */
var https = require('https');
var assert = require('assert');
var bodyParser = require("body-parser");
var express = require('express');
var cors = require('cors');
var app = express();
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
var string="";
app.get('/trends', function (req, res,next) {
    var headers = {
        'User-Agent': 'Coding Defined',
        Authorization: 'Bearer ' + require('./oauth.json').access_token
    };

    function callTwitter(options, callback) {
        https.get(options, function (response) {
            jsonHandler(response, callback);
        }).on('error', function (e) {
            console.log('Error : ' + e.message);
        })
    }

    var trendOptions = {
        host: 'api.twitter.com',
        path: '/1.1/trends/place.json?id=1',
        headers: headers
    }

    function jsonHandler(response, callback) {
        var json = '';
        response.setEncoding('utf8');
        if (response.statusCode === 200) {
            response.on('data', function (chunk) {
                json += chunk;
            }).on('end', function () {
                callback(JSON.parse(json));
            });
        } else {
            console.log('Error : ' + reseponse.statusCode);
        }
    }

    callTwitter(trendOptions, function (trendsArray) {
        var count = 0;
        trendsArray[0].trends.forEach(function (trend) {
            count++;
            string = string + " " + trend.name
        })
        console.log(string.toString())

    });
    res.json({words:string})
});
var server = app.listen(8081, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log("Example app listening at http://%s:%s", host, port)
});

