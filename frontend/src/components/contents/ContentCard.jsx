import React, { Component } from "react"
import {
    Card,
    Badge
} from 'react-bootstrap';
import { BsBoxArrowUp } from 'react-icons/bs';
import { Link } from 'react-router-dom'

import LikesButton from "../likes/likesButton";
import DonatePopup from "../donations/DonatePopup";

import * as ContentService from "../../services/axios/content"

import styles from '../../assets/css/content.module.css'

export default class ContentCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            thumbnailUrl: '',
            totalLikes: 0
        }
    }

    async componentDidMount() {
        const _this = this;
        this.getEmbeded(_this.props.data);
        this.setState({
            totalLikes: this.props.data.totalLikes
        });
    }

    getEmbeded = async (data) => {
        const res = await ContentService.getEmbededContent(data.mediaURL);
        this.setState({
            thumbnailUrl: res.thumbnail_url
        })
    }

    updateTotalLikes = (like) => {
        console.log(like)
        this.setState({
            totalLikes: like ? this.state.totalLikes + 1 : this.state.totalLikes - 1
        });
    }

    render() {
        return (
            <Card border="primary" className={styles.card}>
                <Card.Img variant="top" src={this.state.thumbnailUrl} />
                <Card.Body>
                    <Card.Title>
                        {this.props.data.name}
                    </Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">
                        Creator: <Link to={"/user/" + this.props.data.creator.username}>{this.props.data.creator.username}</Link>
                    </Card.Subtitle>
                    <Badge variant="primary">Price: ${this.props.data.price}</Badge>{' '}
                    <Card.Text>
                        {this.props.data.description}
                    </Card.Text>
                    {this.props.data.mediaType === 'music' && <Card.Link href={this.props.data.mediaURL}><BsBoxArrowUp /> Click here to view the music!  </Card.Link>}
                    <Card.Footer>
                        {this.props.data.price > 0 && <DonatePopup donation_id={this.props.data.id} donation_type='1' creator_name={this.props.data.creator.username} />}
                        <LikesButton contentId={this.props.data.id} change={this.updateTotalLikes} />
                        <small className="text-muted">Total likes: {this.state.totalLikes}</small>
                    </Card.Footer>
                </Card.Body>
            </Card>
        );
    }
}

