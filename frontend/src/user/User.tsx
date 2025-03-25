import { useEffect, useState } from 'react';
import { getUsers } from '../api/userService';

import './User.scss';

type User = {
    id: number;
    username: string;
    balance: number;
};

const Users = () => {
    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            const data = await getUsers();
            setUsers(data);
        };
        fetchData();
    }, []);

    return (
        <div className='User'>
            <h2>User List</h2>
            <p>These are available users and their balance</p>
            <ul>
                {users.map((user) => (
                    <li key={user.id}>
                        {`Användare: ${user.username}`}
                        <br />
                        {`Balance: ${user.balance}`}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Users;
