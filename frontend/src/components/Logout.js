import React from 'react';
import { removeToken } from '../services/axios/authAxios';
import Cookies from 'js-cookie';
import { connect } from 'react-redux';

const Logout = (props) => {

    removeToken();
    props.dispatch({ type: 'RESET_TOKEN' });
    Cookies.remove('authToken');
    console.log(props)
    setTimeout(() => {
        props.history.push('/');
    }, 1500)

    return (
        <>
            <p>Logging you out...</p>
        </>

    )
}

const mapStateToProps = (state) => ({
})

export default connect(mapStateToProps)(Logout);