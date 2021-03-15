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

import styles from '../../assets/css/content.module.css'

const mediaTypes = [
    {
        value: 'art',
        label: 'art',
    },
    {
        value: 'music',
        label: 'music',
    },
];

const postTypes = [
    {
        value: 'free',
        label: 'free',
    },
    {
        value: 'donation',
        label: 'donation',
    },
];

export default function EditContentModal(props) {
    const [open, setOpen] = useState(false);
    const [validated, setValidated] = useState(false);
    const [values, setValues] = useState(props.content);

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

    const editContent = React.useCallback(() => {
        ContentService.modifyContent(values.id, values);
    }, [values]);

    const deleteContent = React.useCallback(() => {
        ContentService.deleteContent(values.id);
        handleClose();
        setValidated(true);
    }, [values]);

    const handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            editContent();
            handleClose();
        }
        setValidated(true);
    };

    const body = (
        <div>
            <h2 className={styles.modalHeader} >Edit new content</h2>
            <Form className={styles.modalForm} noValidate validated={validated} onSubmit={handleSubmit}>
                <Form.Group controlId="name">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        placeholder="name"
                        value={values.name}
                        onChange={handleChange('name')} />
                    <Form.Control.Feedback type="invalid">
                        Please choose a name.
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Row>
                    <Form.Group as={Col} controlId="mediaType">
                        <Form.Label>Media Type</Form.Label>
                        <Form.Control
                            as="select"
                            value={values.mediaType}
                            onChange={handleChange('mediaType')} >
                            {mediaTypes.map((option) => (
                                <option key={option.value} value={option.value}>
                                    {option.label}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group as={Col} controlId="postType">
                        <Form.Label>Post Type</Form.Label>
                        <Form.Control
                            as="select"
                            value={values.postType}
                            onChange={handleChange('postType')} >
                            {postTypes.map((option) => (
                                <option key={option.value} value={option.value} label={option.label} />
                            ))}
                        </Form.Control>
                    </Form.Group>
                </Form.Row>

                <Form.Label>Price</Form.Label>
                <InputGroup>
                    <InputGroup.Prepend>
                        <InputGroup.Text id="inputGroupPrepend">$</InputGroup.Text>
                    </InputGroup.Prepend>
                    <Form.Control
                        as='input'
                        value={values.price}
                        type="number"
                        onChange={handleChange('price')}
                        disabled={values.postType === "free"}
                        aria-describedby="inputGroupPrepend"
                        required={(values.postType === 'donation')}
                        min="0"
                    />
                    <Form.Control.Feedback type="invalid">
                        Please put a price that is more than 0.
                    </Form.Control.Feedback>
                </InputGroup>

                <Form.Group controlId="description">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={5}
                        value={values.description}
                        onChange={handleChange('description')} />
                </Form.Group>

                <Form.Group controlId="mediaURL">
                    <Form.Label>Media URL</Form.Label>
                    <Form.Control
                        type="url"
                        placeholder="example.com"
                        value={values.mediaURL}
                        onChange={handleChange('mediaURL')} />
                </Form.Group>
                <Row>
                    <Col>
                        <Button type="submit" onSubmit={handleSubmit}
                            disabled={values.name === ''}>Submit</Button>
                    </Col>
                    <Col>
                        <Button type="submit"
                            variant="danger" onClick={deleteContent}>Delete</Button>
                    </Col>
                </Row>

            </Form>
        </div>
    );
    return (
        <div>
            <Button onClick={handleOpen}>
                Edit content
            </Button>
            <Modal className={styles.modal} show={open} onHide={handleClose} >
                {body}
            </Modal>
        </div>
    );
}
