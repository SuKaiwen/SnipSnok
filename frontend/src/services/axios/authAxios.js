import axios from 'axios';

export const authAxios = axios.create({
    baseURL: 'http://localhost:8080',
});

export const setToken = (accessToken) => {
    authAxios.defaults.headers.common['Authorization'] = accessToken;
}

export const removeToken = () => {
    delete authAxios.defaults.headers.common['Authorization'];
}

export function checkHeader() {
    return authAxios.defaults.headers.common;
}