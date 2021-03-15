import React, { Component } from "react"
import {
    Button,
} from "react-bootstrap";

import * as FollowServices from "../../services/users/follow"

export default class FollowButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            follow: true

        }
    }



    follow = async (username) => {

        try {
             await FollowServices.addNewFollower(username);
             this.setState({
                    follow: false
                        })
            } catch(e) {
                console.log(e);
            }

    }

    unfollow = async (username) => {
        try {
         await FollowServices.deleteExistingFollower(username);
                this.setState({
                    follow: true
                })
        } catch(e) {
            console.log(e);
        }

    }

    ifFollow = async (username) => {
            try {
            var returnResult = null;
            returnResult = await FollowServices.getFollowerExists(username);

            if (returnResult == 0){

                     this.setState({
                        follow: true
                    })

            }
            else if (returnResult >= 1){
                     this.setState({
                         follow: false,
                    })

            }

            } catch(e) {
                console.log(e);
            }

        }

    componentDidMount() {
        this.ifFollow(this.props.username);

     }





    render() {


        if (this.state.follow) {
            return (
                    <button class="follow_button" onClick={() => this.follow(this.props.username)}>
                    Follow
                    </button>
            )
        }
        else {
            return (
                    <button class="follow_button" onClick={() => this.unfollow(this.props.username)}>
                    Unfollow
                    </button>
            )
        }

    }
}


