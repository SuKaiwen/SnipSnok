import React, { Component } from 'react'
import {Link} from 'react-router-dom'
import PropTypes from 'prop-types';
//import clsx from 'clsx';
import './FollowButton.css';
import FollowButton from './FollowButton.jsx';
//import FollowCard from './FollowCard.jsx';
import FollowCard from './FollowCard.jsx';
import FollowUsersCard from './FollowUsersCard.jsx';
import FollowUserCardList from './FollowUserCardList.jsx';

//import ContentList from '../contents/ContentList.jsx';
//import ContentCard from '../contents/ContentCard.jsx';


//export default class FollowPage extends Component {
export const FollowPage = (props) => {



   // render() {
        return (

           <div>
            <FollowButton username = "aPerson" />
            <FollowCard username = "someone"/>
            <p></p>

            <p> List of users that will appear when Following User </p>
            <FollowUsersCard username="Sophia"/>
            <FollowUsersCard username="Someone"/>
            <FollowUserCardList username="thisUsername" isForFollowing ="true"/>
            </div>


        )
    //}

}


