import React from 'react';
import axios from 'axios';

const Logout = () => {
  const handleLogout = async () => {
    try {
      // Call the logout endpoint on the backend
      await axios.post('http://localhost:8080/api/v1/logout');

      // Remove cookies from the browser
      document.cookie = 'accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      document.cookie = 'refreshToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';

      // Redirect to login page or any other appropriate page
      window.location.href = '/login'; // Replace '/login' with your login page URL
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <div>
      <h1>Logout Page</h1>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default Logout;
