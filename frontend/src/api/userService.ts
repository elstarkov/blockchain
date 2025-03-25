import axios from 'axios';

const API_URL = 'http://localhost:8080/users';

export const getUsers = async () => {
    try {
        const response = await axios.get(`${API_URL}/get`);
        return response.data;
    } catch (error) {
        console.error('Error fetching users:', error);
        return [];
    }
};
