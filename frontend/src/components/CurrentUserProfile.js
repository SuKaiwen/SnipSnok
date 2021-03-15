import React, {Component} from 'react';
import { getCurrentUser } from '../services/axios/users';
import { getTotalCreatorDonations } from '../services/axios/donations';
import { getTotalContentDonations } from '../services/axios/donations';
import { ContentList } from './contents/ContentList';
import DonatePopup from './donations/DonatePopup';
import FollowButton from './users/FollowButton';
import FollowCard from './users/FollowCard';
import FollowUserCardList from './users/FollowUserCardList.jsx';
import FollowListModal from './users/FollowListModal';
import EditUser from './users/EditUser.jsx';
import { Spinner } from "react-bootstrap";
import cat from '../assets/img/cat.jpg';
import painting from '../assets/img/painting.jpg';
import { Card, Row, Col, Container } from 'react-bootstrap';

function Loader() {
    return (
        <div>
            <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
            </Spinner>
        </div>
    );
}


export class CurrentUserProfile extends Component{
    state = {
        lastName: '',
        firstName: '',
        username: '',
        top3Cr: '',
        bio: '',
        userID: '',
        currentUserUsername: '',
        isOwnProfile: false,
        found: 1,
        didLoad: false,
    }

    componentDidMount() {
        getCurrentUser().then((response) => {
                     this.setState({
                        lastName: response.lastName,
                        firstName: response.firstName,
                        username: response.username,
                        top3Cr: response.top3Creators,
                        bio: response.description,
                        userID: response.id,
                        isOwnProfile: true,
                        totalDonationCreator: 0,
                        totalDonationContent: 0,
                        didLoad: true,
                     });
                     getTotalCreatorDonations(response.id).then((response2) => {
                                   this.setState({
                                      totalDonationCreator: response2,
                                   });
                                   console.log(response2);
                                 });
                      getTotalContentDonations(response.id).then((response3) => {
                                    this.setState({
                                       totalDonationContent: response3,
                                    });
                                    console.log(response3);
                                  });
                   });
      }

    render(){
        return(
            <div>
              <Container fluid>
                {!this.state.didLoad &&
                    <Row style = {{justifyContent: 'center'}}>
                        <Loader />
                    </Row>
                }
                {this.state.didLoad &&
                    <div>
                    {this.state.username !== '' &&
                        <Row>
                          <Col style = {{margin: "1em"}}>
                            <Card style = {{background: '#d4ebf2'}}>
                              <Card.Body>
                                <Row style = {{justifyContent: 'center'}}>
                                    <img src={painting} style={{width: "100%", height: "80%", marginBottom: -285}} />
                                </Row>
                                <Row style = {{justifyContent: 'center'}}>
                                    <img src={cat} style={{width: 200, height: 200, borderRadius: 200/ 2, marginBottom: 20}} />
                                </Row>
                                <Row style = {{justifyContent: 'center'}}>
                                    <h3>{this.state.username}</h3>
                                </Row>
                                <Row style = {{justifyContent: 'center'}}>
                                    <h6>Name: {this.state.firstName} {this.state.lastName}</h6>
                                </Row>
                                <Row style = {{justifyContent: 'center'}}>
                                    <h6>____________________________________________________</h6>
                                </Row>
                                <Row style = {{justifyContent: 'center'}}>
                                    <FollowCard username = {this.state.username} />
                                </Row>
                                <h6/>
                                 {this.state.isOwnProfile &&
                                    <Row style = {{justifyContent: 'center'}}>
                                        <FollowListModal username = {this.state.username} isForFollowing = "false"/>
                                        <FollowListModal username = {this.state.username} isForFollowing = "true"/>
                                    </Row>
                                }
                                <Row>
                                    <Col>
                                        <Card.Text>
                                          <h6 style={{textAlign: 'left'}}>Bio: {this.state.bio}</h6>
                                        </Card.Text>
                                        <Card.Text>
                                          <h6 style={{textAlign: 'left'}}>Favourite Creators: {this.state.top3Cr}</h6>
                                        </Card.Text>
                                        <Card.Text>
                                          <h6 style={{textAlign: 'left'}}>Total Donations: ${this.state.totalDonationCreator + this.state.totalDonationContent}</h6>
                                        </Card.Text>
                                    </Col>
                                    <Col>
                                        {this.state.isOwnProfile &&
                                            <Row style = {{margin:"10px"}}>
                                                <EditUser content={this.state} />
                                            </Row>
                                        }
                                    </Col>
                                </Row>
                              </Card.Body>
                            </Card>
                          </Col>
                          <ContentList contentType="user" username={this.state.username} current = {this.state.isOwnProfile}></ContentList>
                        </Row>
                    }
                    {this.state.username === '' &&
                        <div style = {{minHeight: "500px", textAlign: 'center'}}>
                            <h1> Woops! User not found! </h1>
                        </div>
                    }
                    </div>
                }
              </Container>
            </div>
        )
    }
}

