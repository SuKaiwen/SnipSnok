import React, { Component, useState, useEffect } from "react"
import {
    Card, CardGroup, ListGroup, ListGroupItem
} from 'react-bootstrap';
import { For } from "react-for-loop";

import FollowButton from "./FollowButton.jsx";
import FollowUsersCard from "./FollowUsersCard.jsx";
import * as FollowServices from "../../services/users/follow"


export const FollowUserCardList = (props) => {
  const isForFollowing = props.isForFollowing;
  const username = props.username;
  const [data, setData] = useState([]);
  const [noUsers, setNoUsers] = useState("false");

   const listOfFollowers = async () => {
      var result = [];
     result = await FollowServices.accountsFollowingUser(props.username);

      if (result.length == 0){
        setNoUsers("true");
      }
      setData(result);
   }

   const listOfFollowing = async () => {
       var result = [];

       result = await FollowServices.accountsUserIsFollowing(props.username);

        if (result.length == 0){
               setNoUsers("true");
         }
       setData(result);
   }

   useEffect(() => {
        if (isForFollowing == "true"){
            listOfFollowing(props.username);
        }
        else if (isForFollowing == "false") {
            listOfFollowers(props.username);
        }
    }, [isForFollowing, props.username])

    let header;

        if (props.isForFollowing == "true" && noUsers == "true"){
            header = "This user is not following anyone";
        }
        else if (props.isForFollowing == "false" && noUsers == "true"){
            header = "This user has no followers";
        }
        else {
            header = null;
        }


    return (
    <div>
    <p> {header} </p>
    <ListGroup variant="flush">
       <For data={data} itemRenderer={(data, idx) => (
          <ListGroupItem key={idx} >
            <FollowUsersCard username={data.username} />
          </ListGroupItem>
      )}
      ></For>

    </ListGroup>
    </div>

    )


}
export default FollowUserCardList;

