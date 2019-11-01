"use strict";

var chai = require('chai')
var expect = chai.expect
var mongoose = require('mongoose')
var System = require('../src/app/model/System')

describe('Systems Tests', () => {

    describe('Create System', () => {
        it('Create a system', (done) => {
            let system = new System({name: 'Test System', version: '1.0.0', context: 'test-system', tags: 'test'});
            system.save()
                .then( (result) => {
                    expect(result._id).to.not.null;
                    done();
                });
        });
    });
    
    describe('Get all systems', () => {
        it('should return all systems', (done) => {
            System.find()
                .then( (result) => {
                    expect(result).to.be.an('array').that.is.not.empty;
                    done();
                });
        })
    });
    
    describe('Retrieve a system', () => {
        it('should return one system', (done) => {
            System.findOne({name: 'Test System'})
                .then( (result) => {
                    expect(result).to.be.an('object');
                    done();
                });
        })
    });

    describe('Update a system', () => {
        it('should update a system', (done) => {
            System.findOneAndUpdate({name: 'Test System'}, {name: 'Updated Name System'}, { new: true })
                .then( (result) => {
                    expect(result).to.be.an('object').to.have.deep.property('name', 'Updated Name System');
                    done();
                });
        })
    });

    describe('Delete a system', () => {
        it('should delete one system', (done) => {
            System.findOneAndRemove({name: 'Test System'})
                .then( (result) => {
                    expect(result).to.be.an('null');
                    done();
                });
        })
    });

    after((done) => {
            mongoose.connection.collections.systems.drop(() => {
            done();
        }); 
    });

});