import { authAxios } from './authAxios';

export const login = async (details) => {
    return await authAxios.post('/login', details);
}

export const register = async (details) => {
    // console.log('Posting details to /api/users:', details);
    return await authAxios.post('/api/users', details);
}
