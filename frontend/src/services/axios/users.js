import { authAxios } from './authAxios';

const BASE_URL = '/api'

export async function getUser(username) {
    return (await authAxios.get(BASE_URL + '/users/' + username)).data;
}

export async function editUser(userData) {
    return (await authAxios.post(BASE_URL + '/users/edit', userData)).data;
}

export async function getCurrentUser() {
    return (await authAxios.get(BASE_URL + '/users/current')).data;
}
