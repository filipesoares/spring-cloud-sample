const baseRoutes = require('./base-routes')
const accountRoutes = require('./account-routes')
const contextRoot = '/financer/_api/v1'

module.exports = app => {
  baseRoutes(app, contextRoot)
  accountRoutes(app, contextRoot)
}
