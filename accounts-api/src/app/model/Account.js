const mongoose = require('mongoose')
var Schema = mongoose.Schema

const Account = new Schema(
  {
    id: mongoose.Schema.Types.ObjectId,
    name: String,
    agency: Number,
    number: Number,
    username: String,
    balance: { type: Number, get: getBalance, set: setBalance },
    user: String
  },
  {
    timestamps: true,
    toJSON: {
      getters: true
    }
  }
)

function getBalance (num) {
  return (num / 100).toFixed(2)
}

function setBalance (num) {
  return num * 100
}

module.exports = mongoose.model('Account', Account)
