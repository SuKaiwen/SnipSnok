import React, { Component } from 'react';

export default class DonateSuccess extends Component{
    constructor (props) {
        super(props);
    }

    render(){
        return(
            
            <div className="donate_form">
                <h4>Thank You! <span role="img" aria-label="Celebration emoji">🥳</span></h4>

                <p>You donated to {this.props.creator_name}</p>
            </div>
        )
    }
}