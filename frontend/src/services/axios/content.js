import { authAxios } from './authAxios';
import axios from 'axios';

const BASE_URL = '/api/contents'

export async function addContent(contentData) {
    return (await authAxios.post(BASE_URL, contentData)).data;
}

export async function getContent(contentId) {
    return (await authAxios.get(BASE_URL + '/' + contentId)).data;
}

export async function modifyContent(contentId, contentData) {
    return (await authAxios.post(BASE_URL + '/' + contentId, contentData)).data;
}

export async function deleteContent(contentId) {
    return (await authAxios.delete(BASE_URL + '/' + contentId));
}

export async function getContentTotalLikes(contentId) {
    return (await authAxios.get(BASE_URL + '/' + contentId + '/totallikes')).data;
}

// paginations endpoints 

export async function getRecentContent(page, size) {
    return (await authAxios.get(BASE_URL + '/recent', {
        params: {
            page: page,
            size: size
        }
    })).data;
}

export async function getRecommendedContent(page, size) {
    return (await authAxios.get(BASE_URL + '/recommended', {
        params: {
            page: page,
            size: size
        }
    })).data;
}

export async function getContentFromUser(username, page, size) {
    return (await authAxios.get('/api/' + username + '/contents', {
        params: {
            page: page,
            size: size
        }
    })).data;
}

// iframely external api calls
const apiKey = '9e03596b8f440d292e96ac'

export async function getEmbededContent(url) {
    return (await axios.get('https://iframe.ly/api/oembed',
        {
            params: {
                url: url,
                api_key: apiKey,
            }
        })).data;
}