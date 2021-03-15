//import * as AuthAxios from "../authaxios/authAxios";
import { authAxios } from "../axios/authAxios";

const BASE_URL = "/api"


export async function addNewFollower(username) {
    return (await authAxios.post(BASE_URL + "/follow/" + username)).data;
}


export async function deleteExistingFollower(username) {
    return (await authAxios.delete(BASE_URL + "/follow/" + username)).data;
}

//export async function getFollower(username) {
//    return (await authAxios.get(BASE_URL + "/follow/" + username)).data;
//}

export async function getFollowerExists(username) {
    return (await authAxios.get(BASE_URL + "/follow/" + username)).data;
}


export async function accountsUserIsFollowing(username) {
    return (await authAxios.get(BASE_URL + "/" + username + "/following")).data;
}

export async function numberAccountsUserIsFollowing(username) {
    return (await authAxios.get(BASE_URL + "/" + username + "/following/total")).data;
}

export async function accountsFollowingUser(username) {
    return (await authAxios.get(BASE_URL + "/" + username + "/followers")).data;
}

export async function numberAccountsFollowingUser(username) {
    return (await authAxios.get(BASE_URL + "/" + username + "/followers/total")).data;
}