const express = require('express')
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const morgan = require('morgan')
const methodOverride = require('method-override')

require('dotenv').config()

const app = express()
app.use(morgan('dev'))
app.use(bodyParser.json())
app.use(methodOverride())

mongoose.Promise = global.Promise

mongoose
  .connect(process.env.DB_URL, {
    useNewUrlParser: true,
    useFindAndModify: false
  })
  .then(() => {
    console.log('Successfully connected to the database with mongoose')
  })
  .catch(err => {
    console.log('Could not connect to the database. Exiting now...', err)
    process.exit()
  })

const routes = require('../app/routes')
routes(app)

module.exports = app
