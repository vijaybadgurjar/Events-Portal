import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { AppBar, Toolbar, IconButton, Button, Menu, MenuItem, Tooltip } from '@mui/material';
import { LoginOutlined, LogoutOutlined, AccountBoxOutlined } from '@mui/icons-material';
import AuthService from '../../services/AuthService';
import './Header.css';
import { AccountCircle } from '@mui/icons-material';

const Header: React.FC = () => {
        const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const navigate = useNavigate();
    const location = useLocation();
    const isLoggedIn = !!localStorage.getItem('jwtToken');


    const handleLogin = () => {
        navigate('/login');
        setDrawerOpen(false);
    };

    const handleLogout = () => {
        AuthService.logout();
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
                                                <AccountCircle />
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
                                                <Tooltip title="Account">
                                                <MenuItem onClick={() => navigate('/profile')}>
                                                    <AccountBoxOutlined />
                                                </MenuItem>
                                                </Tooltip>
                                                <Tooltip title="Logout">
                                                <MenuItem onClick={handleLogout}>
                                                    <LogoutOutlined />
                                                </MenuItem>
                                                </Tooltip>
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

