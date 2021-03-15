import { authAxios } from './authAxios';

const BASE_URL = '/api/search'

export async function searchArtists(query) {
    return await authAxios.get(`${BASE_URL}/users?username=${query}`).then(res=>{return res.data});
}

export async function searchMedia(query) {
    return await authAxios.get(`${BASE_URL}/content?name=${query}`).then(res=>{return res.data});
}