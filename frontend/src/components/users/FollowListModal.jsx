import React, { useState } from 'react';

import {
    Modal,
    Button,
    Form,
    InputGroup,
    Col
} from 'react-bootstrap'

import * as FollowServices from "../../services/users/follow"

import FollowUserCardList from "./FollowUserCardList.jsx";


export const FollowListModal = (props) => {
  const isForFollowing = props.isForFollowing;
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

   let header;
          if (props.isForFollowing === "true") {
              header = "List of users followed by ";
          }
          else if (props.isForFollowing === "false") {
              header = "List of followers for ";
          }

     let buttonText;
          if (props.isForFollowing === "true") {
              buttonText = "List of following";
          }
          else if (props.isForFollowing === "false") {
              buttonText = "List of followers";
          }


  return (
    <>
      <button class = "follow_modal_button" onClick={handleShow}>
        {buttonText}
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>{header} {props.username}</Modal.Title>
        </Modal.Header>
        <Modal.Body><FollowUserCardList username = {props.username} isForFollowing = {props.isForFollowing} /></Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
export default FollowListModal;
