import { authAxios } from './authAxios';

const BASE_URL = "/api/donate"

export async function donateToContent(contentId, contentData) {
    return (await authAxios.post(BASE_URL + "/content/" + contentId, contentData)).data;
}

export async function getTotalContentDonations(userId) {
    return (await authAxios.get(BASE_URL + "/content/totals/" + userId)).data;
}

export async function donateToUser(creatorId, contentData) {
    return (await authAxios.post(BASE_URL + "/creator/" + creatorId, contentData)).data;
}

export async function getTotalCreatorDonations(userId) {
    return (await authAxios.get(BASE_URL + "/creator/totals/" + userId)).data;
}
