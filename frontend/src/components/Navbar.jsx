import React, { useState } from 'react';
import logo from '../assets/img/snipsnok-logo.png';
import { connect } from 'react-redux';

import { useHistory } from "react-router-dom";

import { Navbar, Nav, Form, Button, FormControl } from 'react-bootstrap';
import AddContentModal from "./contents/AddContentModal"

import '../assets/css/navbar.css';

const NavbarTop = (props) => {
    const [query, setQuery] = useState("");

    let history = useHistory();

    const handleSubmit = (event) => {
        console.log(props)
        event.preventDefault()
        history.push(`/search/${query}`)
        console.log(props)
    }

    const handleChange = (event) => {
        setQuery(event.target.value)
    }


    return (
        <Navbar bg="light" variant="light" className="navbar-top justify-content-center">
            <img className="nav-img" src={logo} width="100" height="100" />
            <Nav className="mr-auto">
                <Nav.Link href="/">Home</Nav.Link>
                {!props.authToken && <Nav.Link className="nav-link" href="/register">Register</Nav.Link>}
                {!props.authToken && <Nav.Link className="nav-link" href="/login">Login</Nav.Link>}
                {props.authToken && <Nav.Link className="nav-link" href="/logout">Logout</Nav.Link>}
                {props.authToken && <Nav.Link className="nav-link" href="/contents">Contents</Nav.Link>}
                {props.authToken && <Nav.Link className="nav-link" href="/myprofile">My Profile</Nav.Link>}
                {props.authToken && <>
                    <Form inline onSubmit={handleSubmit}>
                        <FormControl onChange={handleChange} type="text" placeholder="Search" className="mr-sm-2" />
                    </Form>
                    <Button onClick={handleSubmit} variant="outline-info">Search</Button>
                </>}
                {props.authToken && <AddContentModal />}
            </Nav>
        </Navbar>
    );
}

const mapStateToProps = (state) => ({
    authToken: state.auth.authToken
})

export default connect(mapStateToProps)(NavbarTop);