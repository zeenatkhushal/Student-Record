/**
 * Created by VenkatNag on 3/13/2018.
 */
var https = require('https'),
    oauthJsonFile = require('fs').createWriteStream('oauth.json');
var gettingAccess = {
    consumerKey: 'bjnYTbDvOd61oShbWWyaD2leP',
    consumerSecretKey: 'mCy4iONQfevhJKmfqK1Kt0GjHb1uG0z2f3VUrhN3upmPZMXgsp'
};

var request = https.request({
    method: 'POST',
    host: 'api.twitter.com',
    path: '/oauth2/token',
    headers: {
        'User-Agent': 'Coding Defined',
        Authorization: 'Basic ' + Buffer((encodeURIComponent(gettingAccess.consumerKey) + ':' + encodeURIComponent(gettingAccess.consumerSecretKey))).toString('base64'),
        'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
        'Content-length': 29
    }
});

request.end('grant_type=client_credentials');

request.on('response', function(response) {
    if(response.statusCode !== 200) {
        return console.log('Error ' + response.statusCode);
    }
    response.pipe(oauthJsonFile);
});
