import { authAxios } from './authAxios';

const BASE_URL = "/api/contents"

export async function like(contentId) {
    return (await authAxios.post(BASE_URL + "/" + contentId + "/like", {})).data;
}

export async function dislike(contentId) {
    return (await authAxios.post(BASE_URL + "/" + contentId + "/dislike", {})).data;
}

export async function check(contentId) {
    return (await authAxios.get(BASE_URL + "/" + contentId + "/checklike", {})).data;
}