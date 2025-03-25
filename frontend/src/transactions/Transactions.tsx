import { useEffect, useState } from 'react';
import { getAllTransactions, createTransaction, getPendingTransactions } from '../api/transactionService';
import { startMining } from '../api/blockService';
import './Transactions.scss';

export type Transaction = {
    sender: string;
    receiver: string;
    amount: number;
};

const Transactions = () => {
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const [pendingTransactions, setPendingTransactions] = useState<Transaction[]>([]);

    const fetchTransactions = async () => {
        const data = await getAllTransactions();
        setTransactions(data);
    };

    const fetchPendingTransactions = async () => {
        const data = await getPendingTransactions();
        setPendingTransactions(data);
    };

    const startTransaction = async (e) => {
        e.preventDefault();

        const transaction: Transaction = {
            sender: e.target.sender.value,
            receiver: e.target.receiver.value,
            amount: e.target.amount.value
        };

        const data = await createTransaction(transaction);
        if (data) {
            setTransactions([...transactions, data]);
            await fetchTransactions();
            await fetchPendingTransactions();
        } else {
            console.error('Error creating transaction');
        }

        e.target.reset();
    };

    const mine = async () => {
        await startMining();
        await fetchTransactions();
        await fetchPendingTransactions();
    };

    useEffect(() => {
        fetchTransactions();
        fetchPendingTransactions();
    }, []);

    return (
        <div className='Transactions'>
            <div className='new-transaction'>
                <h2>Transactions</h2>
                <p>Transactions</p>
                <form onSubmit={startTransaction}>
                    <label>
                        From:
                        <br />
                        <input name='sender' type='text' />
                    </label>
                    <label>
                        To:
                        <br />
                        <input name='receiver' type='text' />
                    </label>
                    <label>
                        Amount:
                        <br />
                        <input name='amount' type='number' />
                    </label>
                    <button type='submit'>Submit</button>
                </form>
            </div>
            <div className='pending'>
                <h2>Pending transactions</h2>
                <ul>
                    {pendingTransactions.map((transaction, index) => (
                        <li key={index}>
                            {index + 1}. Sender: {transaction.sender}
                            <br />
                            Receiver: {transaction.receiver}
                            <br />
                            Amount: {transaction.amount}
                        </li>
                    ))}
                </ul>
                {pendingTransactions.length > 0 ? (
                    <button className='mineButton' onClick={mine}>
                        Start mining...
                    </button>
                ) : null}
            </div>
            <div className='history'>
                <h2>Transaction history</h2>
                <ul>
                    {transactions.map((transaction, index) => (
                        <li key={index}>
                            {index + 1}. Sender: {transaction.sender}
                            <br />
                            Receiver: {transaction.receiver}
                            <br />
                            Amount: {transaction.amount}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default Transactions;
