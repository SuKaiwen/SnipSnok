import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Route, Switch, BrowserRouter } from 'react-router-dom';
import Favicon from 'react-favicon';
import { configure } from 'axios-hooks'
import LRU from 'lru-cache'

import { FollowPage } from './users/FollowPage';
import { ContentList } from './contents/ContentList';
import { Home } from './Home';
import { CurrentUserProfile } from './CurrentUserProfile';
import Login from './Login';
import Logout from './Logout';
import { Register } from './Register';
import { UserProfile } from './UserProfile';
import NavbarTop from './Navbar';
import { authAxios, setToken } from '../services/axios/authAxios';
import faviconLogo from '../assets/img/snipsnok-logo-no-text-medium.png';
import Cookies from 'js-cookie';

import '../assets/css/main.css';
import SearchResults from './search/SearchResults';

const App = (props) => {

    // use a cache of size 10
    const cache = new LRU({ max: 10 })
    configure({ authAxios, cache })

    // set authentication details if they exist
    const token = Cookies.get('authToken');
    setToken(token);  // use the token in axios requests
    props.dispatch({ type: 'SET_TOKEN', payload: token });  // store the token in redux

    // runs once on intial load to set page title
    useEffect(() => {
        document.title = "SnipSnok"
    }, []);

    return (
        <>
            <Favicon url={faviconLogo} />
            <BrowserRouter>
                <NavbarTop />
                <div className="main-wrapper">
                    <Switch>
                        <Route path='/' exact component={Home} />
                        <Route path='/register' exact component={Register} />
                        <Route path='/contents' exact component={ContentList} />
                        <Route path='/login' exact component={Login} />
                        <Route path='/logout' exact component={Logout} />
                        <Route path='/user/:usernames' exact component={UserProfile} />
                        <Route path='/myprofile' exact component={CurrentUserProfile} />
                        <Route path='/search/:query' exact component={SearchResults} />
                        <Route path='/follow' exact component={FollowPage} />
                    </Switch>
                </div>
            </BrowserRouter>
        </>
    )
}

const mapStateToProps = (state) => ({
    authToken: state.auth.authToken
})

export default connect(mapStateToProps)(App);