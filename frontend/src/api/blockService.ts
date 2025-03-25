import axios from 'axios';

const API_URL = 'http://localhost:8080/blockchain';

export const startMining = async () => {
    try {
        const response = await axios.post(`${API_URL}/mine`);
        return response.data;
    } catch (error) {
        console.error('Error mining block:', error);
        return [];
    }
};
