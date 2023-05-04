const { defineConfig } = require("cypress");
async function setupNodeEvents(on, config) {
  // implement node event listeners here
  return config;
}


module.exports = defineConfig({
  reporter: 'cypress-mochawesome-reporter',
  e2e: {
    setupNodeEvents(on, config) {
      require('cypress-mochawesome-reporter/plugin')(on);
    },
      // implement node event listeners here
      specPattern: 'cypress/integration/*.js'
  },
  chromeWebSecurity:false
});
