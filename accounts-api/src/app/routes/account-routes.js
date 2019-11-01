const controller = require('../controllers/account-controller')

module.exports = (app, contextRoot) => {
  const resource = `${contextRoot}/accounts`

  app
    .route(`${resource}`)
    .get(controller.list)
    .post(controller.create)

  app
    .route(`${resource}/:id`)
    .get(controller.fetch)
    .put(controller.update)
    .delete(controller.delete)
}
