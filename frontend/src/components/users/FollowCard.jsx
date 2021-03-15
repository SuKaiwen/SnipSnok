//import React, { Component } from "react"
import {
    Card, CardGroup
} from 'react-bootstrap';

import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'
import { Form, FormGroup, FormControl, Button } from 'react-bootstrap';
//import useAxios from 'axios-hooks';

import FollowButton from "./FollowButton.jsx";
import * as FollowServices from "../../services/users/follow"

export const FollowCard = (props) => {

    const [numberFollowers, setNumberFollowers] = useState();
    const [numberFollowing, setNumberFollowing] = useState();
    const [username, setUsername] = useState(props.username);


   const numberOfFollowers = async (username) => {
      var result = null;
      result = await FollowServices.numberAccountsFollowingUser(username);
      setNumberFollowers(result);

   };

   const numberOfFollowing = async (username) => {
       var result = null;
       result = await FollowServices.numberAccountsUserIsFollowing(username);
       setNumberFollowing(result);
   };

    useEffect(() => {
        numberOfFollowers(username);
        numberOfFollowing(username);
        //setNumberFollowers(110);
        //setNumberFollowing(77);

     }, [username])

      return (

       <CardGroup style={{ width: '13.5rem' }}>
         <Card style = {{background: '#d4ebf2'}}>
          <Card.Header>
               <Card.Title class="follow_card_text" > Followers</Card.Title>
               </Card.Header>
           <Card.Body>
         {/*   <Card.Title class="follow_card_text"> {this.state.numberFollowers}</Card.Title> */}
           <Card.Title class="follow_card_text"> {numberFollowers}</Card.Title>
           </Card.Body>
         </Card>

         <Card style = {{background: '#d4ebf2'}}>
         <Card.Header>
              <Card.Title class="follow_card_text">Following</Card.Title>
               </Card.Header>
           <Card.Body>
           {/* <Card.Title class="follow_card_text">{this.state.numberFollowing}</Card.Title> */}
            <Card.Title class="follow_card_text">{numberFollowing}</Card.Title>
           </Card.Body>
         </Card>
       </CardGroup>
   );


  }
  export default FollowCard;



