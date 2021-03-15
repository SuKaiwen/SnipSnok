import React, { useState } from 'react';

import {
    Modal,
    Button,
    Form,
    InputGroup,
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

export default function AddContentModal() {
    const [open, setOpen] = useState(false);
    const [validated, setValidated] = useState(false);
    const [values, setValues] = useState({
        name: '',
        mediaType: 'art',
        postType: 'free',
        price: 0,
        description: '',
        mediaURL: '',
    });

    const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
    };

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const addContent = React.useCallback(() => {
        ContentService.addContent(values);
        setValues({
            name: '',
            mediaType: 'art',
            postType: 'free',
            price: 0,
            description: '',
            mediaURL: '',
        })
    }, [values]);

    const handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            addContent();
            handleClose();
        }
        setValidated(true);
    };

    const fetchDataExternalAPI = React.useCallback(() => {
        ContentService.getEmbededContent(values.mediaURL).then(x => {
            setValues({
                name: x.title,
                mediaType: 'art',
                postType: 'free',
                price: 0,
                description: x.description,
                mediaURL: x.url,
            })
        });
    }, [values]);

    const handleClick = (event) => {
        fetchDataExternalAPI();
    }

    const body = (
        <div>
            <h2 className={styles.modalHeader}>Add a new content</h2>
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

                <Button disabled={values.mediaURL === ''} onClick={handleClick}>Fetch metadata from URL</Button>

                <Form.Group>
                    <Form.Check
                        required
                        label="Agree to terms and conditions"
                        feedback="You must agree before submitting."
                    />
                </Form.Group>
                <Button type="submit" disabled={values.name === ''}>Submit</Button>
            </Form>
        </div>
    );
    return (
        <div>
            <Button className={styles.modalAdd} variant="success" onClick={handleOpen}>
                Add content
            </Button>
            <Modal className={styles.modal} show={open} onHide={handleClose} >
                {body}
            </Modal>
        </div>
    );
}
