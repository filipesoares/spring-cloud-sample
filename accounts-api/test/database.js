const mongoose = require("mongoose");

mongoose.Promise = global.Promise;

require('dotenv').config();

mongoose
  .connect(process.env.DB_URL, {
    useNewUrlParser: true,
    useFindAndModify: false
  });

before((done) => {
  mongoose.connection
    .on('error', (error) => console.error('Error : ', error))
    .once('open', () => { 
      console.log('Connected! Starting tests....');
      done(); 
    });
});  

after((done) => {
        mongoose.connection.db.dropDatabase(() => {
          mongoose.connection.close(done);
    }); 
});