import axios from 'axios';
import { Transaction } from '../transactions/Transactions';

const API_URL = 'http://localhost:8080/transactions';

export const getAllTransactions = async () => {
    try {
        const response = await axios.get(`${API_URL}/all`);
        return response.data;
    } catch (error) {
        console.error('Error fetching transactions:', error);
        return [];
    }
};

export const createTransaction = async (transaction: Transaction) => {
    try {
        const response = await axios.post(`${API_URL}/add`, transaction);
        return response.data;
    } catch (error) {
        console.error('Error creating transaction:', error);
        return null;
    }
};

export const getPendingTransactions = async () => {
    try {
        const response = await axios.get(`${API_URL}/pending`);
        return response.data;
    } catch (error) {
        console.error('Error fetching pending transactions:', error);
        return [];
    }
};
