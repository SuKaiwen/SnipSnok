import React, { Component } from 'react';

export default class DonateError extends Component{

    render(){
        return(
            <div className="donate_form">
                <h4>There was an error processing your donation</h4>

                <p>Please try again</p>
            </div>
        )
    }
}