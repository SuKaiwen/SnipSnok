import React, { Component, useState } from 'react';
import { Modal, Button } from "react-bootstrap";

import DonatePayment from './DonatePayment';
import DonateSuccess from './DonateSuccess';
import DonateError from './DonateError';

import './DonatePopup.css';
import 'bootstrap/dist/css/bootstrap.min.css';

export default class DonatePopup extends Component {

    constructor (props) {
        super(props);
        this.handler = this.handler.bind(this)
        this.state = {
            stage: '0',
            showPopup: false
        }
    }

    handler = (value) => {
        this.setState({
            stage: value
        })
      }

    PopupModal = () => {
        const handleClose = () => {
            this.setState({showPopup: false});

        }
        
        const handleShow = () => {
            this.setState({stage: '0'});
            this.setState({showPopup: true});
        }

        return (
            <>
                <Button variant="primary" onClick={handleShow}>
                    Donate
                </Button>

                <Modal
                    show={this.state.showPopup}
                    onHide={handleClose}
                    backdrop="static"
                    keyboard={false}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>Donate to {this.props.donation_title}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        { this.state.stage === '0' ? <DonatePayment donation_id={this.props.donation_id} donation_type={this.props.donation_type} handler={this.handler}/> : null }
                        { this.state.stage === '1' ? <DonateSuccess creator_name={this.props.creator_name} /> : null }
                        { this.state.stage === '2' ? <DonateError /> : null }
                    </Modal.Body>
                </Modal>
            </>
        );
    }

    render() {
        return (
            <this.PopupModal />
        )
    }
}
