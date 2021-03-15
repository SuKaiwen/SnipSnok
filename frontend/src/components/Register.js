import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { register } from '../services/axios/identity';
import { removeToken } from '../services/axios/authAxios';
import Cookies from 'js-cookie';

export const Register = (props) => {

    const [result, setResult] = useState();
    const [validated, setValidated] = useState(false);

    const handleLogin = (event) => {
        event.preventDefault();

        if (event.target.elements.formPassword1.value !== event.target.elements.formPassword2.value) {
            alert('Your passwords do not match. Please try again.');
            return;
        }

        if (event.target.elements.formPassword1.value.length <= 10) {
            alert('Your password must be at least 10 characters. Try again');
            return;
        }

        const hasNumber = /\d/;
        if (!hasNumber.test(event.target.elements.formPassword1.value)) {
            alert('Your password must contain a number. Try again');
            return;
        }

        if (event.target.checkValidity() === false) {
            event.stopPropagation();
            setValidated(true);
            return;
        }

        removeToken();
        Cookies.remove('authToken');

        register({
            email: event.target.elements.formEmail.value,
            username: event.target.elements.formUsername.value,
            password: event.target.elements.formPassword1.value,
            firstName: event.target.elements.formFirstName.value,
            lastName: event.target.elements.formLastName.value,

        })
            .then(res => {
                setResult(
                    <>
                        <hr />
                        <p style={{ color: 'green' }}>Successfully registered. Please log in...</p>
                    </>
                );
                setTimeout(() => {
                    props.history.push('/login');
                }, 1500)
            })
            .catch(err => {
                setResult(
                    <>
                        <hr />
                        <p style={{ color: 'red' }}>That username is taken. Please choose another</p>
                    </>
                );
            });
    }

    return (
        <>
            <Form noValidate validated={validated} onSubmit={(e) => handleLogin(e)}>
                <Form.Group controlId="formEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control type="email" placeholder="Email" required />
                </Form.Group>

                <Form.Group controlId="formUsername">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="username" placeholder="Username" required />
                </Form.Group>

                <Form.Group controlId="formPassword1">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" required />
                    <Form.Text id="passwordHelpBlock" muted>
                        Please enter a unique password.
                    </Form.Text>
                </Form.Group>

                <Form.Group controlId="formPassword2">
                    <Form.Label>Confirm password</Form.Label>
                    <Form.Control type="password" placeholder="Password" required />
                </Form.Group>

                <Form.Group controlId="formFirstName">
                    <Form.Label>First name</Form.Label>
                    <Form.Control type="first-name" placeholder="First name" required />
                </Form.Group>

                <Form.Group controlId="formLastName">
                    <Form.Label>Last name</Form.Label>
                    <Form.Control type="last-name" placeholder="Last name" required />
                </Form.Group>

                <Button variant="primary" type="submit">
                    Register
                </Button>
            </Form >
            {result}
        </>

    )
}
