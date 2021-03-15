export const loginRedux = (token) => dispatch => {
    console.log('setting token:', token)
    dispatch({ type: 'SET_TOKEN', payload: token });
}

export const logoutRedux = () => dispatch => {
    dispatch({ type: 'RESET_TOKEN' });
}
