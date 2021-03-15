import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { login } from '../services/axios/identity';
import { setToken } from '../services/axios/authAxios';
import { connect } from 'react-redux';
import Cookies from 'js-cookie';

const Login = (props) => {

    const [result, setResult] = useState();
    const [attempts, setAttempts] = useState(5);  // start with a default of 5 attempts

    const handleLogin = (event) => {
        event.preventDefault();
        if (attempts === -1) return;

        login({
            username: event.target.elements.formUsername.value,
            password: event.target.elements.formPassword.value
        })
            .then(res => {
                setResult(
                    <>
                        <hr />
                        <p style={{ color: 'green' }}>Successfully logged in. Redirecting...</p>
                    </>
                );
                const token = res.headers.authorization;
                console.log(`Received auth token ${token}`)
                Cookies.set('authToken', token);
                setToken(token);
                props.dispatch({ type: 'SET_TOKEN', payload: token });

                setTimeout(() => {
                    console.log(props)
                    props.history.push('/');
                }, 1500)
            })
            .catch(err => {
                console.log(err);
                setResult(
                    <>
                        <hr />
                        <p style={{ color: 'red' }}>Incorrect login details. {attempts} attempt(s) remaining. {(attempts === 0) ? 'Redirecting...' : ' '}</p>
                    </>
                );
                // redirect to the home page before allowing to try again
                if (attempts > 0) setAttempts(attempts - 1);
                else if (attempts === 0) {
                    setTimeout(() => {
                        props.history.push('/');
                    }, 1000);
                    setAttempts(-1);
                    return;
                }
            });
    }

    return (
        <>
            <Form onSubmit={(e) => handleLogin(e)}>

                <Form.Group controlId="formUsername">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="username" placeholder="Username" />
                </Form.Group>

                <Form.Group controlId="formPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" />
                </Form.Group>

                <Button variant="primary" type="submit">
                    Login
                </Button>
            </Form >
            {result}
        </>

    )
}

const mapStateToProps = (state) => ({

})


export default connect(mapStateToProps)(Login);