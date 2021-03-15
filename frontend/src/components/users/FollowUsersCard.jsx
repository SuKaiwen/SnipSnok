import React, { Component } from "react"
import {
    Card, CardGroup, ListGroup
} from 'react-bootstrap';

import FollowButton from "./FollowButton.jsx";

export const FollowUsersCard = (props) => {

        return (

            <CardGroup style={{ width: '20.5rem' }}>

                 <Card border="white">
                   <Card.Body class = "less_padding">
                     <Card.Text class="follow_card_text_left" > {props.username} </Card.Text>
                   </Card.Body>
                 </Card>

                 <Card border="white">
                   <Card.Body class = "less_padding">
                     <Card.Text class="centre" > <FollowButton username={props.username} /></Card.Text>
                   </Card.Body>
                 </Card>
           </CardGroup>


        );

}
export default FollowUsersCard;

