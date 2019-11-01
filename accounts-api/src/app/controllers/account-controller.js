const Account = require('../model/Account')

exports.list = (req, res) => {
  return Account.find()
    .then(accounts => res.send(accounts))
    .catch(err => console.log(err))
}

exports.fetch = (req, res) => {
  return Account.findById(req.params.id)
    .then(account => res.send(account))
    .catch(err => console.log(err))
}

exports.create = (req, res) => {
  let account = new Account({
    name: req.body.name,
    agency: req.body.agency,
    number: req.body.number,
    balance: req.body.balance,
    user: req.body.user
  })

  account
    .save()
    .then(data => {
      res
        .location(
          `${req.protocol}://${req.get('host')}${req.originalUrl}/${data._id}`
        )
        .status(201)
        .send(data)
    })
    .catch(err => console.log(err))
}

exports.update = (req, res) => {
  Account.findOneAndUpdate(
    { _id: req.params.id },
    {
      name: req.body.name,
      agency: req.body.agency,
      number: req.body.number,
      balance: req.body.balance,
      user: req.body.user
    },
    { new: true },
    (err, account) => {
      if (err) {
        if (err.kind === 'ObjectId') {
          return res.status(404).send({
            message: 'Account not found with id ' + req.params.id
          })
        }
        return res.status(500).send({
          message: 'Error updating account with id ' + req.params.id
        })
      }

      if (!account) {
        res.status(404).send({
          message: 'Account not Found'
        })
      }

      return res.send(account)
    }
  )
}

exports.delete = (req, res) => {
  Account.findByIdAndRemove(req.params.id)
    .then(account => {
      if (!account) {
        res.status(404).send({
          message: 'Account not Found'
        })
      }

      res.status(204).end()
    })
    .catch(err => {
      if (err.kind === 'ObjectId' || err.name === 'NotFound') {
        return res.status(404).send({
          message: 'Account not found with id ' + req.params.id
        })
      }
      return res.status(500).send({
        message: 'Error removing account with id ' + req.params.id
      })
    })
}
