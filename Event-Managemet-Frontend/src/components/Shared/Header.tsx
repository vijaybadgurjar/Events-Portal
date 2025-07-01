import {useSelector, useDispatch} from 'react-redux'Add commentMore actions
import { selectEmail } from '../../features/user/userSelectors';

import { AppBar, Toolbar, IconButton, Button, Menu, MenuItem, Box } from '@mui/material';
import { AppBar, Toolbar, IconButton, Button, Menu, MenuItem, Tooltip } from '@mui/material';
import { LoginOutlined, LogoutOutlined, AccountBoxOutlined } from '@mui/icons-material';
import AuthService from '../../services/AuthService';
import './Header.css';
import { AccountCircle } from '@mui/icons-material';

const Header: React.FC = () => {
    const email = useSelector(selectEmail);Add commentMore actions
        const dispatch = useDispatch();
        const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const navigate = useNavigate();
    const location = useLocation();
    const isLoggedIn = !!localStorage.getItem('jwtToken');


    const handleLogin = () => {
        navigate('/login');
        setDrawerOpen(false);
    };

    const handleLogout = () => {
        AuthService.logout(dispatch);
        window.location.reload();
    };

       const handleMenu = (event: React.MouseEvent<HTMLElement>) => {Add commentMore actions
            setAnchorEl(event.currentTarget);
        };

      const handleClose = () => {Add commentMore actions
            setAnchorEl(null);
        };

    return (
        <AppBar position="sticky" className="header-appbar">
            <Toolbar className="header-toolbar">
                <IconButton edge="start" color="inherit" aria-label="home" className="header-logo-button" onClick={() => navigate('/')}>
                    <img src="/evs-logoName.png" alt="EventBazaar Logo" className="header-logo" />
                 <div className='header-buttons'>Add commentMore actions
                                    {isLoggedIn ? (
                                        <div>
                                            <IconButton
                                                aria-controls='manu-appbar'
                                                onClick={handleMenu}
                                                color="inherit">
                                                {email}<AccountCircle />
                                            </IconButton>
                                            <Menu
                                                id="menu-appbar"
                                                anchorEl={anchorEl}
                                                anchorOrigin={{
                                                    vertical: 'top',
                                                    horizontal: 'right',
                                                }}
                                                keepMounted
                                                transformOrigin={{
                                                    vertical: 'top',
                                                    horizontal: 'right',
                                                }}
                                                open={Boolean(anchorEl)}
                                                onClose={handleClose}
                                            >

                                                <MenuItem onClick={() => navigate('/profile')}>
                                                   <Box>Add commentMore actions
                                                                                     <AccountBoxOutlined />  Account
                                                                                     </Box>
                                                                                 </MenuItem>
                                                                                 <MenuItem onClick={() => navigate('/users')}>
                                                                                     <Box>
                                                                                     <AccountBoxOutlined />  All Users
                                                                                     </Box>

                                                <MenuItem onClick={handleLogout}>
                                                    <Box>Add commentMore actions
                                                              <LogoutOutlined />  Logout
                                                      </Box>
                                                </MenuItem>

                                            </Menu>
                        </div>
                                                    ) : (Add commentMore actions
                                                         location.pathname !== '/login' && (
                                                             <Button color="inherit" className="header-button" onClick={handleLogin}>
                                                                 <LoginOutlined />
                            </Button>
                )
            )}
        </div>
            </Toolbar>
        </AppBar>
    );
};

export default Header;

