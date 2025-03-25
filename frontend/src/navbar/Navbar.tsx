import { Link } from 'react-router-dom';

import './Navbar.scss';

const Navbar = () => {
    return (
        <header>
            <nav className='Navbar'>
                <ul>
                    <li>
                        <Link to='/'>Home</Link>
                    </li>
                    <li>
                        <Link to='/users'>Users</Link>
                    </li>
                    <li>
                        <Link to='/transactions'>Transactions</Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
};

export default Navbar;
