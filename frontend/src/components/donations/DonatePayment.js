import React, { Component } from 'react';
import { Button } from 'react-bootstrap';
import Cards from 'react-credit-cards';

import 'react-credit-cards/es/styles-compiled.css';

import './DonatePopup.css';

import * as DonationService from "../../services/axios/donations"

export default class DonatePayment extends Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.donation_id,
            donation_type: this.props.donation_type,
            amount: '5',
            other_amount: '',
            cvc: '',
            expiry: '',
            focus: '',
            name: '',
            number: '',
            error: ''
        }
    }

    submitDonation = async () => {
        if (this.state.cvc.length !== 3 || this.state.expiry === '' || this.state.name == '' || this.state.number.length !== 16) {
            this.setState({error: "Error! Please Check Details"})
            return;
        }
        var donationAmount = parseFloat(this.state.amount);
        if (this.state.amount === '-1') {
            donationAmount = parseFloat(this.state.other_amount);
        }
        try {
            if (this.state.donation_type === '0') {
                await DonationService.donateToUser(this.state.id, {amount: donationAmount});
            } else {
                await DonationService.donateToContent(this.state.id, {amount: donationAmount});
            }
            this.props.handler('1');
        } catch (error) {
            console.log(error)
            this.props.handler('2');
        }

    }

    handleInputFocus = (e) => {
        this.setState({ focus: e.target.name });
    }

    handleInputChange = (e) => {
        const { name, value } = e.target;
        if (name === 'other_amount') {
            this.setState({'amount': '-1'});
            this.setState({'other_amount': value});
        } else if (name === 'expiry') {
            var temp = value.split("-");
            this.setState({'expiry': temp[1] + temp[0]});
        } else if (name === 'cvc') {
            if (value.length <= 3) {
                this.setState({'cvc': value});
            }
        } else if (name === 'number') {
            if (value.length <= 16) {
                this.setState({'number': value});
            }
        } else {
            this.setState({ [name]: value });
        }
    }

    render() {
        return(
            <form>
                <div className="donate_form">
                    <h4>Message</h4>
                    <input className="donate_input"
                        type="text"
                        name="message"
                        placeholder="Message to Creator"
                    />

                    <h4>Amount</h4>
                    <div className="flex-parent">
                        <input className="flex-child" type="radio" name="amount" value="5" checked={this.state.amount === '5'} onChange={this.handleInputChange.bind(this)} /> $5&emsp;
                        <input className="flex-child" type="radio" name="amount" value="10" checked={this.state.amount  === '10'} onChange={this.handleInputChange.bind(this)} /> $10&emsp;
                        <input className="flex-child" type="radio" name="amount" value="20" checked={this.state.amount  === '20'}  onChange={this.handleInputChange.bind(this)} /> $20&emsp;
                        <input className="flex-child" type="radio" name="amount" value="-1" checked={this.state.amount  === '-1'}  onChange={this.handleInputChange.bind(this)} /> Other
                        <input className="flex-child, donate_input" type="number" placeholder="$" name="other_amount" value={this.state.other_amount} onChange={this.handleInputChange.bind(this)} min="0"/>
                    </div>

                    <div>
                        <Cards
                            cvc={this.state.cvc}
                            expiry={this.state.expiry}
                            focused={this.state.focus}
                            name={this.state.name}
                            number={this.state.number}
                        />
                        <div>
                            <input className="donate_input"
                                type="number"
                                name="number"
                                value={this.state.number}
                                placeholder="Card Number"
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                            <br></br>
                            <input className="donate_input"
                                type="text"
                                name="name"
                                placeholder="Name"
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                            <br></br>
                            <input className="donate_input"
                                type="month"
                                name="expiry"
                                placeholder="Expiry Date"
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                            <br></br>
                            <input className="donate_input"
                                type="number"
                                name="cvc"
                                value={this.state.cvc}
                                placeholder="CVC"
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                        </div>
                    </div>
                    <p className="error">{this.state.error}</p>
                    <Button id="donation_submit" onClick={() => this.submitDonation()} value="Donate">Donate</Button>
                </div>
            </form>
        )
    }
}
