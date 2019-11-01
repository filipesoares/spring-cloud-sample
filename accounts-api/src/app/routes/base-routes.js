module.exports = (app, contextRoot) => {
  app.get(contextRoot, function (req, res) {
    res.send(
      { 
        api: 'accounts-api',
        version: '1.0.0'
      })
  })

  app.get(`${contextRoot}/health`, function (req, res) {
    res.send(`OK`)
  })
}
