import React from 'react';
import { ContentList } from "./contents/ContentList"
import { HomeCarousel } from "./HomeCarousel"
import Cookies from 'js-cookie';


export const Home = () => {

    var loggedIn = false;

    const token = Cookies.get('authToken');
    if (token !== undefined) {
        loggedIn = true;
    }

    if (loggedIn) {
        return <ContentList contentType="recommended"></ContentList>
    } else {
        return <HomeCarousel></HomeCarousel>
    }
}
