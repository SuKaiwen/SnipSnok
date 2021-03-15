import React, { useState } from 'react';
import {
    Modal,
    Button,
    Form,
    InputGroup,
    Row,
    Col
} from 'react-bootstrap'

import * as ContentService from "../../services/axios/content"

import * as UserService from "../../services/axios/users"

import styles from '../../assets/css/content.module.css'

export default function EditUser(props) {
    const [open, setOpen] = useState(false);
    const [validated, setValidated] = useState(false);
    const [values, setValues] = useState(props);

    const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
    };

    const handleOpen = () => {
        setValues({ ...values, ...props.content });
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const editUser = React.useCallback(() => {
        UserService.editUser(values);
    }, [values]);

    const handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            editUser();
            handleClose();
        }
        setValidated(true);
    };

    const body = (
        <div>
            <h2 className={styles.modalHeader} >Edit User Details</h2>
            <Form className={styles.modalForm} noValidate validated={validated} onSubmit={handleSubmit}>
                <Form.Group controlId="firstname">
                    <Form.Label>First name</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        placeholder="First name"
                        value={values.firstName}
                        onChange={handleChange('firstName')} />
                    <Form.Control.Feedback type="invalid">
                        Please choose a first name.
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group controlId="lastname">
                    <Form.Label>Last name</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        placeholder="Last name"
                        value={values.lastName}
                        onChange={handleChange('lastName')} />
                    <Form.Control.Feedback type="invalid">
                        Please choose a last name.
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group controlId="description">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        placeholder="Description"
                        value={values.description}
                        onChange={handleChange('description')} />
                </Form.Group>

                <Form.Group controlId="Top3Cr">
                    <Form.Label>Top 3 Creators</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        placeholder="Top 3 Creators"
                        value={values.top3Creators}
                        onChange={handleChange('top3Creators')} />
                </Form.Group>

                <Row>
                    <Col>
                        <Button type="submit" onSubmit={handleSubmit}
                            disabled={values.name === ''}>Submit</Button>
                    </Col>
                </Row>

            </Form>
        </div>
    );
    return (
        <div>
            <Button onClick={handleOpen}>
                Edit user
            </Button>
            <Modal className={styles.modal} show={open} onHide={handleClose} >
                {body}
            </Modal>
        </div>
    );
}
