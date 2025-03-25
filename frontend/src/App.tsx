import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './navbar/Navbar';
import Footer from './footer/Footer';
import User from './user/User';
import Transactions from './transactions/Transactions';

const App = () => {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path='/' element={<h1>Home Page</h1>} />
                <Route path='/users' element={<User />} />
                <Route path='/transactions' element={<Transactions />} />
            </Routes>
            <Footer />
        </Router>
    );
};

export default App;
