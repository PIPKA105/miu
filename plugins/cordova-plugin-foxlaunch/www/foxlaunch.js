var exec = require('cordova/exec');

module.exports = {
    isInstalled: function (pkg, ok, fail) { exec(ok, fail, 'FoxLaunch', 'isInstalled', [pkg]); },
    launch:      function (pkg, ok, fail) { exec(ok, fail, 'FoxLaunch', 'launch', [pkg]); },
    openStore:   function (pkg, ok, fail) { exec(ok, fail, 'FoxLaunch', 'openStore', [pkg]); }
};
