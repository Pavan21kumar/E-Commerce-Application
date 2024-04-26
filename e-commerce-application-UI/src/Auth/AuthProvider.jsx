import React, { createContext, useContext, useState } from "react";

// Context Holding The Auth User Details
export const authContext = createContext({});

// Component That Returns The Authentication By Enclosing Its Child Components Within The Context
const AuthProvider = ({ children }) => {
  const [user, setUser] = useState({
    userId: "",
    name: "Simanta Sen",
    role: "CUSTOMER",
    authenticated: false,
    accessExpiration: 0,
    refreshExpiration: 0,
  });

  // Function to update user data with response from the server
  const updateUser = (responseData) => {
    setUser({
      userId: responseData.statusData.userId,
      name: responseData.statusData.userName,
      role: responseData.statusData.userRole,
      authenticated: true,
      accessExpiration: responseData.statusData.accessExpiration,
      refreshExpiration: responseData.statusData.refreshExpiration,
    });
  };

  return (
    // Returning The Authentication With Values "user" and "setUser"
    // by enclosing the child components within it
    <authContext.Provider value={{ user, setUser, updateUser }}>
      {children}
    </authContext.Provider>
  );
};

export default AuthProvider;

// Custom Hook That Returns The Context Values
export const useAuth = () => useContext(authContext);
