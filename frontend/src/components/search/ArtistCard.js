import React, { useState, useEffect } from 'react'

import { Navbar, Nav } from 'react-bootstrap';

import {Container, Row, Col} from 'react-bootstrap'

import * as FollowServices from "../../services/users/follow"

export default function ArtistCard(props) {
    
    const border ={
        width: "100%",
        height: "100%",
        margin: "0.5rem",
        backgroundColor: "#eee",
        display: "inline-block",
}
    const [numberFollowers, setNumberFollowers] = useState("");
    
console.log(props)

    useEffect(() =>{
        numberOfFollowers(props.artist.username);
    },[props.artist.username])

const numberOfFollowers = async (username) => {
    var result = null;
    result = await FollowServices.numberAccountsFollowingUser(username);
    setNumberFollowers(result);
 };

    

    return (
        <div>
            <Container style ={border}>
                <Row>
                    <Col>
                        <Nav.Link href={"/user/"+props.artist.username}>{props.artist.username}</Nav.Link>
                        <p>{props.artist.firstName} {props.artist.lastName}</p>
                    </Col>
                    <Col>
                        <p>Followers: {numberFollowers ? numberFollowers : 0}</p>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}
