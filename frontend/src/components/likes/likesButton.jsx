import React, { Component } from "react"

import { Button } from "react-bootstrap";

import * as LikesServices from "../../services/axios/likes"

import styles from '../../assets/css/content.module.css'

export default class LikesButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            like: null
        }
    }

    componentDidMount = async () => {
        const res = await LikesServices.check(this.props.contentId);
        this.setState({
            like: res
        })
    }

    like = async (contentId) => {
        await LikesServices.like(contentId);
        this.setState({
            like: false
        })
        this.props.change(true);
    }

    dislike = async (contentId) => {
        await LikesServices.dislike(contentId);
        this.setState({
            like: true
        })
        this.props.change(false);
    }

    render() {
        if (this.state.like) {
            return (
                <Button variant="success" className={styles.likesButton} onClick={() => this.like(this.props.contentId)}>
                    Like
                </Button>
            )
        }
        else {
            return (
                <Button variant="danger" className={styles.likesButton} onClick={() => this.dislike(this.props.contentId)}>
                    Dislike
                </Button>
            )
        }

    }
}
