import React, { useState, useEffect } from 'react'

import * as ContentService from "../../services/axios/content"
import {
    Card,
    Badge
} from 'react-bootstrap';
import { BsBoxArrowUp } from 'react-icons/bs';

import { Container, Row, Col, Nav } from 'react-bootstrap'

export default function MediaCard(props) {
    const p = {
        margin: "0em"
    };

    const border = {
        width: "100%",
        height: "100%",
        margin: "0.5rem",
        backgroundColor: "#eee",
        display: "inline-block",
    }

    const [thumbnailUrl, setThumbnailUrl] = useState("");
    console.log(props)
    useEffect(() => {
        getEmbeded(props.content);
    }, [props.data])

    const getEmbeded = async (data) => {
        const res = await ContentService.getEmbededContent(data.mediaURL);
        setThumbnailUrl(res.thumbnail_url)
        console.log(res)
    }

    return (
        <div>
            <Container style={border}>
                <Row>
                    <Col>
                        <p style={p}>{props.content.name}</p>
                        <p style={p}>{props.content.creator.firstName} {props.content.creator.lastName} </p>
                        <p style={p}>{props.content.postType}</p>
                        <p style={p}>{props.content.mediaType}</p>
                    </Col>
                    <Col>
                        <Card.Img variant="top" src={thumbnailUrl} />
                        {props.content.mediaType === 'art' && <Card.Link href={props.content.mediaURL}><BsBoxArrowUp /> Click here to view the Art!  </Card.Link>}
                        {props.content.mediaType === 'music' && <Card.Link href={props.content.mediaURL}><BsBoxArrowUp /> Click here to view the music!  </Card.Link>}
                    </Col>
                    <Col>
                        <p>Likes: {props.content.totalLikes ? props.content.totalLikes : 0}</p>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}
